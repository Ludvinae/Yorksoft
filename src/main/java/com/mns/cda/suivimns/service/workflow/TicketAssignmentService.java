package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.dao.AssignmentDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.AssignmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketAssignmentService {


    private final AssignmentDao assignmentDao;
    private final TicketStatusService ticketStatusService;
    private final StatusTransition transition;

    private void closeCurrentAssignment(Integer idTicket) {
        assignmentDao.findLatestByTicket(idTicket)
                .ifPresent(assignment -> assignment.setEndDate(LocalDateTime.now()));
    }

    @Transactional
    public Ticket assignTicket(Ticket ticket, Manager manager,
                               Technician technician, String statusReason) {

        // Verifier que le ticket puisse transitionner vers le status 'ASSIGNED'
        if (!transition.canTransition(ticket.getCurrentStatus(), StatusEnum.ASSIGNED)) {
            throw new StatusTransition.IllegalStatusTransitionException();
        }

        // Verifier que le ticket n'est pas déja attribué à ce technicien
        if (technician == ticket.getCurrentTechnician()) {
            throw new AssignmentService.AssignmentConflictException();
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

        // Mettre à jour le ticket avec le technicien assigné, et le manager ayant crée l'affectation
        ticketChanged.setCurrentTechnician(technician);
        ticketChanged.setCurrentManager(manager);

        return ticketChanged;
    }
}
