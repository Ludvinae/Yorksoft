package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.DemoUnavailableException;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.security.AppUserDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Trial accounts for the public demo: rank 0 technicians created without any personal data,
 * then purged with everything they created once their expiry date is past.
 * <p>
 * What a trial account can touch is limited upstream so that the purge is enough to keep the demo
 * dataset clean: workflow actions (closing included) need the ticket to be assigned to the technician, and a
 * technician can only self-assign the tickets it created,
 * and trial accounts can't comment nor edit articles they didn't write (see AppUserDetails#isTrialTechnician).
 */
@Service
@RequiredArgsConstructor
public class DemoAccountService {

    private static final Logger logger = LoggerFactory.getLogger(DemoAccountService.class);

    private final TechnicianDao technicianDao;
    private final PasswordEncoder passwordEncoder;
    private final Clock clock;
    private final DemoTicketGenerator ticketGenerator;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${app.demo.enabled:false}")
    private boolean enabled;

    @Value("${app.demo.ttl-hours:24}")
    private int ttlHours;

    // Caps the table size even if many IPs create accounts
    @Value("${app.demo.max-active-accounts:200}")
    private int maxActiveAccounts;

    public boolean isEnabled() {
        return enabled;
    }

    public int getTtlHours() {
        return ttlHours;
    }

    @Transactional
    public AppUserDetails createTrialTechnician() {
        if (technicianDao.countByDemoExpiresAtIsNotNull() >= maxActiveAccounts) {
            logger.warn("Trial account limit reached ({}), creation refused", maxActiveAccounts);
            throw new DemoUnavailableException();
        }

        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        Technician technician = new Technician();
        technician.setFirstName("Visiteur");
        technician.setLastName(suffix.substring(0, 4).toUpperCase(Locale.ROOT));
        technician.setEmail("essai-" + suffix + "@demo.invalid");
        // Nobody knows this password: the trial account is only reachable through the token returned on creation
        technician.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        technician.setRank((byte) 0);
        technician.setDemoExpiresAt(LocalDateTime.now(clock).plusHours(ttlHours));

        Technician saved = technicianDao.save(technician);
        int tickets = ticketGenerator.generateHistory(saved.getIdAppUser());
        logger.info("Trial account created: id={}, expires at {}, {} past tickets generated",
                saved.getIdAppUser(), saved.getDemoExpiresAt(), tickets);

        return new AppUserDetails(saved);
    }

    @Scheduled(fixedDelayString = "${app.demo.purge-interval-ms:900000}")
    @Transactional
    public void purgeExpiredAccounts() {
        List<Integer> userIds = technicianDao.findExpiredTrialAccountIds(LocalDateTime.now(clock));
        if (userIds.isEmpty()) {
            return;
        }

        // Tickets created by the trial accounts (the creator is the actor of the OPEN history entry)
        List<Integer> ticketIds = entityManager.createQuery("""
                        SELECT h.ticket.idTicket FROM History h
                        WHERE h.status.code = :open AND h.actor.idAppUser IN :userIds
                        """, Integer.class)
                .setParameter("open", StatusEnum.OPEN)
                .setParameter("userIds", userIds)
                .getResultList();

        if (!ticketIds.isEmpty()) {
            for (String entity : List.of("Comment", "Classification", "Assignment", "History")) {
                execute("DELETE FROM " + entity + " e WHERE e.ticket.idTicket IN :ids", ticketIds);
            }
            execute("DELETE FROM Ticket t WHERE t.idTicket IN :ids", ticketIds);
        }

        List<Integer> knowledgeIds = entityManager.createQuery(
                        "SELECT k.idKnowledge FROM Knowledge k WHERE k.author.idAppUser IN :userIds", Integer.class)
                .setParameter("userIds", userIds)
                .getResultList();

        if (!knowledgeIds.isEmpty()) {
            execute("UPDATE Ticket t SET t.knowledge = null WHERE t.knowledge.idKnowledge IN :ids", knowledgeIds);
            execute("DELETE FROM Procedure p WHERE p.knowledge.idKnowledge IN :ids", knowledgeIds);
            entityManager.createNativeQuery("DELETE FROM knowledge_versions WHERE id_knowledge IN (:ids)")
                    .setParameter("ids", knowledgeIds)
                    .executeUpdate();
            execute("DELETE FROM Knowledge k WHERE k.idKnowledge IN :ids", knowledgeIds);
        }

        // Whatever is left pointing to the accounts, on tickets they didn't create
        execute("DELETE FROM Procedure p WHERE p.technician.idAppUser IN :ids", userIds);
        execute("DELETE FROM Comment c WHERE c.author.idAppUser IN :ids", userIds);
        execute("UPDATE History h SET h.actor = null WHERE h.actor.idAppUser IN :ids", userIds);
        execute("UPDATE Ticket t SET t.currentTechnician = null WHERE t.currentTechnician.idAppUser IN :ids", userIds);
        execute("DELETE FROM Assignment a WHERE a.technician.idAppUser IN :ids", userIds);
        execute("DELETE FROM Activity a WHERE a.user.idAppUser IN :ids", userIds);

        // Removed through the repository so that both JOINED tables (app_user, technician) are cleaned
        technicianDao.deleteAllById(userIds);

        logger.info("Purged {} expired trial account(s) and {} ticket(s) they created", userIds.size(), ticketIds.size());
    }

    private void execute(String jpql, List<Integer> ids) {
        entityManager.createQuery(jpql).setParameter("ids", ids).executeUpdate();
    }
}
