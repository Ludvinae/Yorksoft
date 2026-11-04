package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.dao.AssignmentDao;
import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.enumerate.ActivityType;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.AssignmentConflictException;
import com.mns.cda.suivimns.exception.IllegalStatusTransitionException;
import com.mns.cda.suivimns.exception.UnauthorizedTechnicianException;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.service.entity.ActivityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TicketAssignmentService {


    private final AssignmentDao assignmentDao;
    private final HistoryDao historyDao;
    private final TicketStatusService ticketStatusService;
    private final ActivityService activityService;
    private final TicketProgressService ticketProgressService;

    private void closeCurrentAssignment(Integer idTicket) {
        assignmentDao.findLatestByTicket(idTicket)
                .ifPresent(assignment -> assignment.setEndDate(LocalDateTime.now()));
    }

    @Transactional
    public Ticket assignTicket(Ticket ticket, Manager manager, Technician technician, String statusReason) {
        // Verifier que le ticket n'est pas déja attribué à ce technicien
        Integer currentTechnicianId = (ticket.getCurrentTechnician() != null
                ? ticket.getCurrentTechnician().getIdAppUser() : null);
        if (Objects.equals(technician.getIdAppUser(), currentTechnicianId)) {
            throw new AssignmentConflictException();
        }
        // Marque l'affectation actuelle comme finie si elle existe
        closeCurrentAssignment(ticket.getIdTicket());
        // Changer l'état du ticket
        Ticket ticketChanged = ticketStatusService.changeStatus(ticket, StatusEnum.ASSIGNED, manager, statusReason);
        // Créer la nouvelle affectation
        Assignment assignment = new Assignment();
        assignment.setTicket(ticketChanged);
        assignment.setTechnician(technician);
        assignment.setManager(manager);
        assignmentDao.save(assignment);
        // Mettre à jour le ticket avec le technicien assigné, et le manager ayant créé l'affectation
        ticketChanged.setCurrentTechnician(technician);
        ticketChanged.setCurrentManager(manager);
        activityService.log(manager, "A attribué le ticket #" + ticket.getIdTicket() +
                " à " + technician.getFirstName() + " " + technician.getLastName(), ActivityType.ASSIGNMENT);
        activityService.log(technician, "A reçu l'attribution du ticket #" + ticket.getIdTicket(),
                ActivityType.ASSIGNMENT);
        return ticketChanged;
    }

    @Transactional
    public Ticket selfAssignTicket(Ticket ticket, Technician technician, String statusReason) {
        // Verifier que le ticket est ouvert et sans technicien assigné
        if (ticket.getCurrentStatus() != StatusEnum.OPEN) throw new IllegalStatusTransitionException();
        if (ticket.getCurrentTechnician() != null) throw new AssignmentConflictException();
        // Verifier que le technicien est bien le créateur du ticket (acteur de l'historique OPEN)
        AppUser creator = historyDao
                .findFirstByTicketIdTicketAndStatusCodeOrderByStartDateAsc(ticket.getIdTicket(), StatusEnum.OPEN)
                .map(History::getActor)
                .orElse(null);
        if (creator == null || !Objects.equals(technician.getIdAppUser(), creator.getIdAppUser())) {
            throw new UnauthorizedTechnicianException();
        }
        // Changer l'état du ticket
        Ticket ticketChanged = ticketStatusService.changeStatus(ticket, StatusEnum.ASSIGNED, technician, statusReason);
        // Créer la nouvelle affectation (pas de manager : auto-attribution)
        Assignment assignment = new Assignment();
        assignment.setTicket(ticketChanged);
        assignment.setTechnician(technician);
        assignmentDao.save(assignment);
        // Mettre à jour le ticket avec le technicien assigné
        ticketChanged.setCurrentTechnician(technician);
        ticketChanged.setCurrentManager(null);
        activityService.log(technician, "S'est attribué le ticket #" + ticket.getIdTicket(), ActivityType.ASSIGNMENT);
        // Passe le ticket en IN_PROGRESS
        return ticketProgressService.takeTicketInCharge(ticketChanged, technician, null);
    }
}
