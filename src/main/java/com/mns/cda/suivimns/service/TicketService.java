package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.*;
import com.mns.cda.suivimns.dto.TicketCreation;
import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.TicketResponse;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import com.mns.cda.suivimns.service.inter.iClassificationService;
import com.mns.cda.suivimns.service.inter.iHistoryService;
import com.mns.cda.suivimns.service.inter.iTicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TicketService implements iTicketService {

    protected final TicketDao ticketDao;
    private final ThemeDao themeDao;
    protected final iHistoryService iHistoryService;
    protected final ClassificationDao classificationDao;
    protected final iClassificationService iClassificationService;
    protected final ClientDao clientDao;
    protected final ImpactDao impactDao;
    protected final UrgencyDao urgencyDao;
    protected final VersionDao versionDao;

    @Override
    public List<Ticket> findAll() {
        return ticketDao.findAll();
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return ticketDao.findById(id);
    }


    @Override
    public List<TicketFullWithLatest> getAllTicketFullWithLatest() {
        return ticketDao.returnTicketFullWithLatest();
    }

    @Override
    public List<TicketFullWithLatest> getTicketFullWithLatestByTechnician(int id) {
        return ticketDao.returnTicketAttributed(id);
    }

    @Override
    public Ticket save(Ticket ticket) {
        ticket.setIdTicket(null);
        ticket.setFinalPriority(ticket.getInitialPriority());
        ticket.setOpenDate(null);
        ticket.setModificationDate(null);
        return ticketDao.save(ticket);
    }

    @Override
    public void delete(Ticket ticket) {
        ticketDao.delete(ticket);
    }

    @Override
    public Ticket update(Ticket ticketToUpdate, int id) throws TicketNotFoundException {
        Optional<Ticket> ticket = ticketDao.findById(id);

        if (ticket.isEmpty()) {
            throw new TicketNotFoundException();
        }

        ticketToUpdate.setIdTicket(ticket.get().getIdTicket());

        return ticketDao.save(ticketToUpdate);
    }


    @Transactional
    @Override
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

    @Override
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


    // Priorité

    private static final int[][] priorityMatrix =
            {{5, 4},
            {4, 3},
            {3, 2},
            {2, 1}};

    @Override
    public int computePriority(int impact, int urgence, int importance) {
        int finalImpact = Math.min(impact + importance, 4);
        return priorityMatrix[finalImpact - 1][urgence - 1];
    }


    // Mapping

    @Override
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
