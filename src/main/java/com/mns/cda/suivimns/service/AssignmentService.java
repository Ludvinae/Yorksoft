package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AssignmentDao;
import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.service.inter.iAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentService implements iAssignmentService {

    protected final AssignmentDao assignmentDao;

    @Override
    public List<Assignment> findAll() {
        return assignmentDao.findAll();
    }

    @Override
    public Optional<Assignment> findById(int id) {
        return assignmentDao.findById(id);
    }

    @Override
    public void modify(Assignment assignment, int id) {
        assignment.setIdAssignment(id);
        assignment.setAssignmentDate(assignment.getAssignmentDate());
        assignmentDao.save(assignment);
    }

    @Override
    public void firstSave(Assignment assignment) {
        assignment.setIdAssignment(null);
        assignment.setAssignmentDate(LocalDateTime.now());
        assignmentDao.save(assignment);
    }


    @Override
    public void close(Assignment assignment, int id) {
        assignment.setIdAssignment(id);
        assignment.setAssignmentDate(assignment.getAssignmentDate());
        assignment.setTicket(assignment.getTicket());
        assignment.setManager(assignment.getManager());
        assignment.setTechnician(assignment.getTechnician());
        assignment.setEndDate(LocalDateTime.now());
        assignmentDao.save(assignment);
    }

    @Override
    public void delete(Assignment assignment) {
        assignmentDao.delete(assignment);
    }

    @Override
    public void update(Assignment assignmentToUpdate, int id) throws iAssignmentService.AssignmentNotFoundException, AssignmentBadRequestException {
        Optional<Assignment> assignment = assignmentDao.findById(id);

        if (assignment.isEmpty()) {
            throw new iAssignmentService.AssignmentNotFoundException();
        }

        if (assignmentToUpdate.getTicket() == null ||
            assignmentToUpdate.getManager() == null ||
            assignmentToUpdate.getTechnician() == null) {
            throw new iAssignmentService.AssignmentBadRequestException();
        }


        assignmentToUpdate.setIdAssignment(assignment.get().getIdAssignment());

        assignmentDao.save(assignmentToUpdate);


        // Pour remplacer la ligne precedente?
        // modify(assignmentToUpdate, id);

    }
}
