package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.*;
import com.mns.cda.suivimns.dto.TicketDto;
import com.mns.cda.suivimns.dto.flat.TicketCreation;
import com.mns.cda.suivimns.dto.flat.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.flat.TicketResponse;
import com.mns.cda.suivimns.mapper.TicketMapper;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import com.mns.cda.suivimns.service.business.TicketPriorityService;
import com.mns.cda.suivimns.service.business.TicketStatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService  {

    public static class TicketNotFoundException extends Exception {
    }

    protected final TicketDao ticketDao;
    protected final TicketMapper ticketMapper;
    protected final TicketPriorityService priorityService;
    protected final TicketStatusService statusService;

    private final ThemeDao themeDao;
    protected final HistoryService iHistoryService;
    protected final ClassificationDao classificationDao;
    protected final ClassificationService iClassificationService;
    protected final ClientDao clientDao;
    protected final ImpactDao impactDao;
    protected final UrgencyDao urgencyDao;
    protected final VersionDao versionDao;

    public List<TicketDto> findAll() {
        return ticketMapper.toDtoList(ticketDao.findAll());
    }

    public TicketDto findById(int id) throws TicketNotFoundException {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        return ticketMapper.toDto(ticket);
    }


    public List<TicketFullWithLatest> getAllTicketFullWithLatest() {
        return ticketDao.returnTicketFullWithLatest();
    }

    public List<TicketFullWithLatest> getTicketFullWithLatestByTechnician(int id) {
        return ticketDao.returnTicketAttributed(id);
    }

    public TicketDto save(TicketDto dto) throws StatusService.StatusNotFoundException {

        Ticket ticket = ticketMapper.toEntity(dto);

        priorityService.initializePriority(ticket);

        ticket.setIdTicket(null);
        ticket.setOpenDate(null);
        ticket.setModificationDate(null);
        ticket.setCloseDate(null);

        Ticket ticketSaved = ticketDao.save(ticket);

        // En attendant l'authentification
        AppUser userBidon = new AppUser();
        userBidon.setIdAppUser(1);

        statusService.initializeStatus(ticketSaved, userBidon);

        return ticketMapper.toDto(ticketSaved);
    }

    public void delete(int id) throws TicketNotFoundException {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        ticketDao.delete(ticket);
    }

    public TicketDto update(int id, TicketDto ticketToUpdate) throws TicketNotFoundException {
        Ticket currentTicket = ticketDao.findById(id)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        ticketMapper.updateEntityFromDto(ticketToUpdate, currentTicket);

        return ticketMapper.toDto(ticketDao.save(currentTicket));
    }

    public Ticket forceChangePriority(int priority, int id) throws TicketNotFoundException {
        Ticket currentTicket = ticketDao.findById(id)
                .orElseThrow(TicketNotFoundException::new);

        priorityService.recalculateCurrentPriority(currentTicket);

        return ticketDao.save(currentTicket);
    }


    @Transactional
    public Ticket createTicket(TicketCreation ticketDto) throws StatusService.StatusNotFoundException {

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
        priorityService.initializePriority(ticket);

        ticket.setClient(client);
        ticket.setImpact(impact);
        ticket.setUrgency(urgency);
        ticket.setVersion(version);

        Ticket savedTicket = ticketDao.save(ticket);

        // En attendant l'authentification
        AppUser userBidon = new AppUser();
        userBidon.setIdAppUser(1);

        statusService.initializeStatus(savedTicket, userBidon);
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




}
