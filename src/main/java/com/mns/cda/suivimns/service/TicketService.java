package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.*;
import com.mns.cda.suivimns.dto.TicketCreation;
import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final ThemeDao themeDao;

    public static class TicketNotFoundException extends Exception {}

    protected final TicketDao ticketDao;

    protected final StatusTriggerService trigger;
    protected final ClassificationDao classificationDao;
    protected final ClientDao clientDao;
    protected final ImpactDao impactDao;
    protected final UrgencyDao urgencyDao;
    protected final VersionDao versionDao;

    public List<Ticket> findAll() {
        return ticketDao.findAll();
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

    public void save(Ticket ticket) {
        ticket.setIdTicket(null);
        ticket.setFinalPriority(ticket.getInitialPriority());
        ticket.setOpenDate(null);
        ticket.setModificationDate(null);
        ticketDao.save(ticket);
    }


    public void saveStatusUpdate(Ticket ticket, Integer actorId) {
        trigger.updateHistory(ticket, actorId, "Nouveau");
    }

    public void delete(Ticket ticket) {
        ticketDao.delete(ticket);
    }

    public void update(Ticket ticketToUpdate, int id) throws TicketNotFoundException {
        Optional<Ticket> ticket = ticketDao.findById(id);

        if (ticket.isEmpty()) {
            throw new TicketNotFoundException();
        }

        ticketToUpdate.setIdTicket(ticket.get().getIdTicket());

        ticketDao.save(ticketToUpdate);
    }

    public void addThemeToTicket(Ticket ticket, String designation) {

        // 1. récupérer la thématique
        Theme theme = themeDao.findByDesignation(designation.toLowerCase())
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

    @Transactional
    public Ticket createTicket(TicketCreation ticketDto) {

        // 1. construire le ticket
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDto.title());
        ticket.setDescription(ticketDto.description());
        ticket.setInitialPriority(ticketDto.initialPriority());
        ticket.setFinalPriority(ticketDto.initialPriority());

        // 2. récupérer les relations
        Client client = clientDao.findById(ticketDto.idClient())
                .orElseThrow(() -> new RuntimeException("Client introuvable"));

        Impact impact = impactDao.findById(ticketDto.idImpact())
                .orElseThrow(() -> new RuntimeException("Impact introuvable"));

        Urgency urgency = urgencyDao.findById(ticketDto.idUrgency())
                .orElseThrow(() -> new RuntimeException("Urgence introuvable"));

        Version version = versionDao.findById(ticketDto.idVersion())
                .orElseThrow(() -> new RuntimeException("Version introuvable"));

        ticket.setClient(client);
        ticket.setImpact(impact);
        ticket.setUrgency(urgency);
        ticket.setVersion(version);

        // 3. save ticket
        Ticket savedTicket = ticketDao.save(ticket);

        // 4. historique
        trigger.updateHistory(savedTicket, ticketDto.idCreator(), "Nouveau");

        // 5. thématique
        addThemeToTicket(savedTicket, ticketDto.themeDesignation());

        return savedTicket;
    }


    @Transactional
    public Ticket createTicketOld(Ticket ticket, Integer actorId, String themeDesignation) {

        // 1. init ticket
        ticket.setIdTicket(null);
        ticket.setFinalPriority(ticket.getInitialPriority());
        ticket.setOpenDate(null);
        ticket.setModificationDate(null);

        // 2. save ticket
        Ticket savedTicket = ticketDao.save(ticket);

        // 3. historique initial
        trigger.updateHistory(savedTicket, actorId, "Nouveau");

        // 4. thématique initiale
        addThemeToTicket(savedTicket, themeDesignation);

        return savedTicket;
    }
}
