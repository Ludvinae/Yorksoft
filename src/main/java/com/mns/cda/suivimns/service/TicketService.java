package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.*;
import com.mns.cda.suivimns.dto.flat.TicketCreation;
import com.mns.cda.suivimns.dto.flat.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.flat.TicketResponse;
import com.mns.cda.suivimns.dto.flat.TicketUpdatedDto;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService  {

    public static class TicketNotFoundException extends Exception {
    }

    protected final TicketDao ticketDao;
    private final ThemeDao themeDao;
    protected final HistoryService iHistoryService;
    protected final ClassificationDao classificationDao;
    protected final ClassificationService iClassificationService;
    protected final ClientDao clientDao;
    protected final ImpactDao impactDao;
    protected final UrgencyDao urgencyDao;
    protected final VersionDao versionDao;

    public List<TicketResponse> findAllDto() {
        return ticketDao.findAllDto();
    }

    public Optional<Ticket> findById(int id) {
        return ticketDao.findById(id);
    }


    public List<TicketFullWithLatest> getAllTicketFullWithLatest() {
        return ticketDao.returnTicketFullWithLatest();
    }

    public List<TicketFullWithLatest> getTicketFullWithLatestByTechnician(int id) {
        return ticketDao.returnTicketAttributed(id);
    }

    public Ticket save(Ticket ticket) {
        ticket.setIdTicket(null);
        ticket.setFinalPriority(ticket.getInitialPriority());
        ticket.setOpenDate(null);
        ticket.setModificationDate(null);
        return ticketDao.save(ticket);
    }

    public void delete(Ticket ticket) {
        ticketDao.delete(ticket);
    }

    public TicketUpdatedDto update(TicketUpdatedDto ticketToUpdate, int id) throws TicketNotFoundException {
        Ticket currentTicket = ticketDao.findById(id)
                .orElseThrow(TicketNotFoundException::new);

        currentTicket.setTitle(ticketToUpdate.title());
        currentTicket.setDescription(ticketToUpdate.description());
        currentTicket.setCallDuration(ticketToUpdate.callDuration());

        Ticket ticketSaved = ticketDao.save(currentTicket);

        return new TicketUpdatedDto(
                ticketSaved.getIdTicket(),
                ticketSaved.getTitle(),
                ticketSaved.getDescription(),
                ticketSaved.getFinalPriority(),
                ticketSaved.getCallDuration());
    }

    public Ticket forceChangePriority(int priority, int id) throws TicketNotFoundException {
        Ticket currentTicket = ticketDao.findById(id)
                .orElseThrow(TicketNotFoundException::new);

        currentTicket.setFinalPriority(priority);

        return ticketDao.save(currentTicket);
    }


    @Transactional
    public Ticket createTicket(TicketCreation ticketDto) {

        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDto.title());
        ticket.setDescription(ticketDto.description());

        // Retrieve relations
        Client client = clientDao.findById(ticketDto.idClient())
                .orElseThrow(() -> new RuntimeException("Client introuvable"));

        Impact impact = impactDao.findById(ticketDto.idImpact())
                .orElseThrow(() -> new RuntimeException("Impact introuvable"));

        Urgency urgency = urgencyDao.findById(ticketDto.idUrgency())
                .orElseThrow(() -> new RuntimeException("Urgence introuvable"));

        Version version = versionDao.findById(ticketDto.idVersion())
                .orElseThrow(() -> new RuntimeException("Version introuvable"));

        // Automatic priority calcul
        int priority = computePriority(impact.getPriorityFactor(), urgency.getPriorityFactor(), client.getImportance());
        ticket.setInitialPriority(priority);
        ticket.setFinalPriority(priority);

        ticket.setClient(client);
        ticket.setImpact(impact);
        ticket.setUrgency(urgency);
        ticket.setVersion(version);

        Ticket savedTicket = ticketDao.save(ticket);

        // En attendant l'authentification
        AppUser userBidon = new AppUser();
        userBidon.setIdAppUser(1);

        iHistoryService.updateHistory(savedTicket, userBidon.getIdAppUser(), "Nouveau");
        addThemeToTicket(savedTicket, ticketDto.themeDesignation());

        return savedTicket;
    }

    // METHODS

    public void addThemeToTicket(Ticket ticket, String designation) {

        // 1. récupérer la thématique
        Theme theme = themeDao.findByDesignation(designation)
                .orElseThrow(() -> new RuntimeException("Thématique introuvable"));

        // 2. créer la classification
        ClassificationKey key = new ClassificationKey(
                ticket.getIdTicket(),
                theme.getIdTheme()
        );

        Classification classification = new Classification(key, LocalDateTime.now(), ticket, theme);

        // 3. sauvegarder
        classificationDao.save(classification);
    }

    public String getCurrentTheme(Ticket ticket) {
        return ticket.getClassificationList().stream()
                .max(Comparator.comparing(Classification::getAffectationDate))
                .map(c -> c.getTheme().getDesignation())
                .orElse(null);
    }


    // Priorité

    private static final int[][] priorityMatrix =
            {{5, 4},
            {4, 3},
            {3, 2},
            {2, 1}};

    public int computePriority(int impact, int urgence, int importance) {
        int finalImpact = Math.min(impact + importance, 4);
        return priorityMatrix[finalImpact - 1][urgence - 1];
    }


    // Mapping

    public TicketResponse responseToDto(Ticket ticket) {
        Status status = iHistoryService.getStatus(ticket.getIdTicket());
        Theme theme = iClassificationService.getTheme(ticket.getIdTicket());

        return new TicketResponse(
                ticket.getIdTicket(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getModificationDate(),
                ticket.getFinalPriority(),
                ticket.getVersion().getVersionNumber(),
                ticket.getVersion().getVersionType().getDesignation(),
                ticket.getVersion().getSoftware().getName(),
                ticket.getClient().getFirstName(),
                ticket.getClient().getLastName(),
                status.getDesignation(),
                theme.getDesignation());
    }


}
