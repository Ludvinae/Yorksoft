package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.*;
import com.mns.cda.suivimns.dto.entity.TicketDto;
import com.mns.cda.suivimns.dto.flat.*;
import com.mns.cda.suivimns.dto.workflow.*;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.mapper.entity.TicketMapper;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.service.business.*;
import com.mns.cda.suivimns.service.workflow.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.mns.cda.suivimns.service.workflow.TicketClosingService.isNotEditable;

@Service
@RequiredArgsConstructor
public class TicketService  {

    public static class TicketNotFoundException extends RuntimeException {
    }

    protected final TicketMapper ticketMapper;
    protected final TicketPriorityService priorityService;
    protected final TicketStatusService statusService;
    protected final TicketAssignmentService assignmentService;
    protected final TicketClassificationService classificationService;
    protected final TicketClosingService closingService;
    protected final TicketProgressService progressService;
    protected final TicketSolvedService solvedService;
    protected final TicketWaitService waitingService;
    protected final ActiveTimeService activeTime;

    protected final TicketDao ticketDao;
    private final ThemeDao themeDao;
    protected final ClassificationDao classificationDao;
    protected final ManagerDao managerDao;
    protected final TechnicianDao technicianDao;
    protected final AppUserDao appUserDao;

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
        classificationService.classify(ticketSaved, dto.currentTheme());

        return ticketMapper.toDto(ticketSaved);
    }

    public void delete(int id) throws TicketNotFoundException {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        ticketDao.delete(ticket);
    }

    public TicketDto update(int id, TicketDto ticketToUpdate)
            throws TicketNotFoundException {
        Ticket currentTicket = ticketDao.findById(id)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        if (isNotEditable(currentTicket)) {
            throw new TicketClosingService.TicketNotEditableException();
        }

        ticketMapper.updateEntityFromDto(ticketToUpdate, currentTicket);

        return ticketMapper.toDto(ticketDao.save(currentTicket));
    }

    public Ticket forceChangePriority(int priority, int id) throws TicketNotFoundException {
        Ticket currentTicket = ticketDao.findById(id)
                .orElseThrow(TicketNotFoundException::new);

        priorityService.recalculateCurrentPriority(currentTicket);

        return ticketDao.save(currentTicket);
    }



    // METHODS

    public void addThemeToTicket(Ticket ticket, String designation) {

        // 1. récupérer la thématique
        Theme theme = themeDao.findByDesignation(designation)
                .orElseThrow(() -> new RuntimeException("Thématique introuvable"));

        // 2. créer la classification
        Classification classification = new Classification();
        classification.setTicket(ticket);
        classification.setTheme(theme);

        // 3. sauvegarder
        classificationDao.save(classification);
    }

    public String getCurrentTheme(Ticket ticket) {
        return ticket.getClassificationList().stream()
                .max(Comparator.comparing(Classification::getAffectationDate))
                .map(c -> c.getTheme().getDesignation())
                .orElse(null);
    }


    /**
     * Gère l'assignation d'un ticket
     * Verifie que le ticket puisse transitionner vers le statut 'ASSIGNED'
     * Met à jour le ticket avec le technicien et le manager actuel
     * @param idTicket
     * @param assignmentDto
     * @return
     */
    public TicketDto assignTicket(Integer idTicket, TicketAssignmentDto assignmentDto) {

        // Récupère le ticket par l'id
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        // Récupère le technicien et le manager
        Manager manager = managerDao.findById(assignmentDto.idManager())
                .orElseThrow(ManagerService.ManagerNotFoundException::new);
        Technician technician = technicianDao.findById(assignmentDto.idTechnician())
                .orElseThrow(TechnicianService.TechnicianNotFoundException::new);

        Ticket ticketAssigned = assignmentService.assignTicket(ticket, manager, technician, assignmentDto.statusReason());

        return ticketMapper.toDto(ticketAssigned);
    }

    public TicketDto closeTicket(Integer idTicket, TicketClosingDto dto) {
        // Récupère le ticket par l'id
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        AppUser user = appUserDao.findById(dto.idAppUser())
                .orElseThrow(AppUserService.AppUserNotFoundException::new);

        Ticket ticketClosed = closingService.closeTicket(ticket, user, dto.closingReason());

        return  ticketMapper.toDto(ticketClosed);
    }


    public TicketDto takeTicketInCharge(Integer idTicket, TicketProgressDto dto) {

        // Récupère le ticket par l'id
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        Technician technician = technicianDao.findById(dto.idAppUser())
                .orElseThrow(TechnicianService.TechnicianNotFoundException::new);

        Ticket ticketChanged = progressService.takeTicketInCharge(ticket, technician, dto.statusReason());
        return ticketMapper.toDto(ticketChanged);
    }

    public TicketDto resumeTicket(Integer idTicket, TicketProgressDto dto) {
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        AppUser user = appUserDao.findById(dto.idAppUser())
                .orElseThrow(AppUserService.AppUserNotFoundException::new);

        Ticket ticketChanged = progressService.resumeTicket(ticket, user, dto.statusReason());
        return ticketMapper.toDto(ticketChanged);
    }

    public TicketDto solveTicket(Integer idTicket, TicketSolvedDto dto) {

        // Récupère le ticket par l'id
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        Technician technician = technicianDao.findById(dto.idTechnician())
                .orElseThrow(TechnicianService.TechnicianNotFoundException::new);

        Ticket ticketChanged = solvedService.proposeSolution(ticket, technician, dto.statusReason());

        return ticketMapper.toDto(ticketChanged);
    }

    public TicketDto setWaitingStatus(Integer idTicket, TicketWaitDto dto) {

        // Récupère le ticket par l'id
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        Technician technician = technicianDao.findById(dto.idTechnician())
                .orElseThrow(TechnicianService.TechnicianNotFoundException::new);

        if (dto.waitingStatus() != StatusEnum.WAITING_CLIENT
            && dto.waitingStatus() != StatusEnum.WAITING_THIRD_PARTY) {
            throw new IllegalArgumentException("Invalid waiting status");
        }

        Ticket ticketChanged = waitingService.setWaitingStatus(ticket, technician, dto.statusReason(), dto.waitingStatus());

        return ticketMapper.toDto(ticketChanged);
    }

    public Long getActiveTimeInSeconds(Integer idTicket) {
        List<StatusEnum> statuses = List.of(
                StatusEnum.OPEN,
                StatusEnum.ASSIGNED,
                StatusEnum.IN_PROGRESS
        );

        return activeTime.getActiveTimeInSeconds(idTicket, statuses);
    }
}
