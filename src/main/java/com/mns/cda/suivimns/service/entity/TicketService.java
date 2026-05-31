package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.*;
import com.mns.cda.suivimns.dto.details.TicketDetailFullDto;
import com.mns.cda.suivimns.dto.entity.TicketDto;
import com.mns.cda.suivimns.dto.search.TicketListDto;
import com.mns.cda.suivimns.dto.search.TicketSearchCriteria;
import com.mns.cda.suivimns.dto.workflow.*;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.*;
import com.mns.cda.suivimns.mapper.entity.TicketMapper;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.business.*;
import com.mns.cda.suivimns.service.search.TicketQueryService;
import com.mns.cda.suivimns.service.workflow.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.mns.cda.suivimns.service.workflow.TicketClosingService.isNotEditable;

@Service
@RequiredArgsConstructor
public class TicketService  {

    protected final TicketMapper ticketMapper;
    protected final TicketPriorityService priorityService;
    protected final TicketStatusService statusService;
    protected final TicketAssignmentService assignmentService;
    protected final TicketClassificationService classificationService;
    protected final TicketClosingService closingService;
    protected final TicketProgressService progressService;
    protected final TicketSolvedService solvedService;
    protected final TicketWaitService waitingService;
    protected final TicketMetricsService metricsService;
    protected final TicketQueryService queryService;
    protected final TicketDetailService detailService;

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
                .orElseThrow(TicketNotFoundException::new);

        return ticketMapper.toDto(ticket);
    }


    public TicketDto save(TicketCreationDto dto, AppUserDetails principal) throws StatusNotFoundException {

        Ticket ticket = ticketMapper.creationToEntity(dto);
        ticket.setOverdue(false);

        priorityService.initializePriority(ticket);

        Ticket ticketSaved = ticketDao.save(ticket);

        AppUser creator = appUserDao.findById(principal.getId())
            .orElseThrow(AppUserNotFoundException::new);

        statusService.initializeStatus(ticketSaved, creator);

        Theme theme = themeDao.findById(dto.idTheme()).orElseThrow(ThemeNotFoundException::new);
        classificationService.classify(ticketSaved, theme.getCode());

        metricsService.refreshTicketMetrics(ticketSaved);

        return ticketMapper.toDto(ticketSaved);
    }

    public void delete(int id) throws TicketNotFoundException {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(TicketNotFoundException::new);

        ticketDao.delete(ticket);
    }

    public TicketDetailFullDto update(int id, TicketDescriptionDto ticketToUpdate, AppUserDetails principal) {

        boolean isEdited = false;

        Ticket currentTicket = ticketDao.findById(id)
                .orElseThrow(TicketNotFoundException::new);

        if (isNotEditable(currentTicket)) {
            throw new TicketNotEditableException();
        }

        if (!Objects.equals(principal.getUserRole(), "ADMIN")
            && !Objects.equals(principal.getUserRole(), "MANAGER")
            && !Objects.equals(currentTicket.getCurrentTechnician().getIdAppUser(), principal.getId())) {
            throw new UnauthorizedTechnicianException();
        }

        if (!ticketToUpdate.description().isBlank() || ticketToUpdate.description().equals(currentTicket.getDescription())) {
            currentTicket.setDescription(ticketToUpdate.description());
            isEdited = true;
        }
        if (!ticketToUpdate.solution().isBlank() || ticketToUpdate.solution().equals(currentTicket.getSolution())) {
            currentTicket.setSolution(ticketToUpdate.solution());
            isEdited = true;
        }

        if (!isEdited) {
            throw new IllegalArgumentException();
        }

        metricsService.refreshTicketMetrics(currentTicket);
        ticketDao.save(currentTicket);

        return detailService.getTicketDetails(id, principal);
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
                .orElseThrow(TicketNotFoundException::new);

        // Récupère le technicien et le manager
        Manager manager = managerDao.findById(assignmentDto.idManager())
                .orElseThrow(ManagerNotFoundException::new);
        Technician technician = technicianDao.findById(assignmentDto.idTechnician())
                .orElseThrow(TechnicianNotFoundException::new);

        Ticket ticketAssigned = assignmentService.assignTicket(ticket, manager, technician, assignmentDto.statusReason());

        metricsService.refreshTicketMetrics(ticketAssigned);

        return ticketMapper.toDto(ticketAssigned);
    }

    public TicketDto closeTicket(Integer idTicket, StateChangeJustification justification, AppUserDetails principal) {
        // Récupère le ticket par l'id
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketNotFoundException::new);

        AppUser user = appUserDao.findById(principal.getId())
                .orElseThrow(AppUserNotFoundException::new);

        if (ticket.getCurrentTechnician() != user && !Objects.equals(principal.getUserRole(), "ADMIN")) {
            throw new UnauthorizedTechnicianException();
        }

        Ticket ticketClosed = closingService.closeTicket(ticket, user, justification.reason());

        metricsService.refreshTicketMetrics(ticketClosed);

        return  ticketMapper.toDto(ticketClosed);
    }


    public TicketDto takeTicketInCharge(Integer idTicket, StateChangeJustification justification, AppUserDetails principal) {

        // Récupère le ticket par l'id
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketNotFoundException::new);

        Technician technician = technicianDao.findById(principal.getId())
                .orElseThrow(TechnicianNotFoundException::new);

        if (ticket.getCurrentTechnician() != technician && !Objects.equals(principal.getUserRole(), "ADMIN")) {
            throw new UnauthorizedTechnicianException();
        }

        Ticket ticketChanged = progressService.takeTicketInCharge(ticket, technician, justification.reason());

        metricsService.refreshTicketMetrics(ticketChanged);

        return ticketMapper.toDto(ticketChanged);
    }

    public TicketDto resumeTicket(Integer idTicket, StateChangeJustification justification, AppUserDetails principal) {
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketNotFoundException::new);

        AppUser user = appUserDao.findById(principal.getId())
                .orElseThrow(AppUserNotFoundException::new);

        if (ticket.getCurrentTechnician() != user && !Objects.equals(principal.getUserRole(), "ADMIN")) {
            throw new UnauthorizedTechnicianException();
        }

        Ticket ticketChanged = progressService.resumeTicket(ticket, user, justification.reason());

        metricsService.refreshTicketMetrics(ticketChanged);

        return ticketMapper.toDto(ticketChanged);
    }

    public TicketDto solveTicket(Integer idTicket, StateChangeJustification justification, AppUserDetails principal) {

        // Récupère le ticket par l'id
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketNotFoundException::new);

        Technician technician = technicianDao.findById(principal.getId())
                .orElseThrow(TechnicianNotFoundException::new);

        if (ticket.getCurrentTechnician() != technician && !Objects.equals(principal.getUserRole(), "ADMIN")) {
            throw new UnauthorizedTechnicianException();
        }

        Ticket ticketChanged = solvedService.proposeSolution(ticket, technician, justification.reason());

        metricsService.refreshTicketMetrics(ticketChanged);

        return ticketMapper.toDto(ticketChanged);
    }

    public TicketDto setWaitingStatus(Integer idTicket, TicketWaitDto dto) {

        // Récupère le ticket par l'id
        Ticket ticket = ticketDao.findById(idTicket)
                .orElseThrow(TicketNotFoundException::new);

        Technician technician = technicianDao.findById(dto.idTechnician())
                .orElseThrow(TechnicianNotFoundException::new);

        if (dto.waitingStatus() != StatusEnum.WAITING_CLIENT
            && dto.waitingStatus() != StatusEnum.WAITING_THIRD_PARTY) {
            throw new IllegalArgumentException("Invalid waiting status");
        }

        Ticket ticketChanged = waitingService.setWaitingStatus(ticket, technician, dto.statusReason(), dto.waitingStatus());

        metricsService.refreshTicketMetrics(ticketChanged);

        return ticketMapper.toDto(ticketChanged);
    }



    public Page<TicketListDto> getAllPageable(TicketSearchCriteria criteria, Pageable pageable, AppUserDetails principal) {
        if (principal.getUserRole() == null) {
            throw new InvalidUserRoleException();
        } else if (Objects.equals(principal.getUserRole(), "CLIENT")) {
            TicketSearchCriteria newCriteria = new TicketSearchCriteria(
                    criteria.keyword(), principal.getId(), criteria.hasVersion(), criteria.hasSoftware(),
                    criteria.statuses(), criteria.statusesExcluded(), criteria.priorityGreaterThan(),
                    criteria.priorityLessThan(), criteria.priorityEquals(), criteria.assignedTo(),
                    criteria.createdAfter(), criteria.createdBefore(), criteria.closedAfter(),
                    criteria.closedBefore(), criteria.isNotClosed(), criteria.isOverdue());
            return queryService.search(newCriteria, pageable);
        }
        return queryService.search(criteria, pageable);
    }

    // DEBUG

}
