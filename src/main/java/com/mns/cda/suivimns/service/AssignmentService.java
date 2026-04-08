package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AssignmentDao;
import com.mns.cda.suivimns.model.Assignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    public static class AssignmentNotFoundException extends Exception {}

    protected final AssignmentDao assignmentDao;

    public List<Assignment> findAll() {
        return assignmentDao.findAll();
    }

    public Optional<Assignment> findById(int id) {
        return assignmentDao.findById(id);
    }

    public void modify(Assignment assignment, int id) {
        assignment.setIdAssignment(id);
        assignment.setAssigmentDate(assignment.getAssigmentDate());
        assignmentDao.save(assignment);
    }

    public void firstSave(Assignment assignment) {
        assignment.setIdAssignment(null);
        assignment.setAssigmentDate(LocalDateTime.now());
        assignmentDao.save(assignment);
    }

    public void close(Assignment assignment, int id) {
        assignment.setIdAssignment(id);
        assignment.setAssigmentDate(assignment.getAssigmentDate());
        assignment.setTicket(assignment.getTicket());
        assignment.setManager(assignment.getManager());
        assignment.setTechnician(assignment.getTechnician());
        assignment.setEndDate(LocalDateTime.now());
        assignmentDao.save(assignment);
    }

    public void delete(Assignment assignment) {
        assignmentDao.delete(assignment);
    }

    public void update(Assignment assignmentToUpdate, int id) throws AssignmentService.AssignmentNotFoundException {
        Optional<Assignment> assignment = assignmentDao.findById(id);

        if (assignment.isEmpty()) {
            throw new AssignmentService.AssignmentNotFoundException();
        }

        assignmentToUpdate.setIdAssignment(assignment.get().getIdAssignment());

        assignmentDao.save(assignmentToUpdate);
    }
}
