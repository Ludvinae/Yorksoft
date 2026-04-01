package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dao.AssignmentDao;
import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.view.AssignmentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class AssignmentController {

    protected AssignmentDao assignmentDao;

    @Autowired
    public AssignmentController(AssignmentDao assignmentDao) {
        this.assignmentDao = assignmentDao;
    }

    @GetMapping("/assignment/list")
    @JsonView(AssignmentView.class)
    public List<Assignment> getAll() {
        return assignmentDao.findAll();
    }

    @GetMapping("/assignment/{id}")
    @JsonView(AssignmentView.class)
    public ResponseEntity<Assignment> getById(@PathVariable int id) {
        Optional<Assignment> assignment = assignmentDao.findById(id);
        if (assignment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(assignment.get(), HttpStatus.OK);
    }

    @PostMapping("/assignment")
    public ResponseEntity<Assignment> create(@RequestBody @Validated() Assignment assignment) {
        assignment.setIdAssignment(null);
        assignment.setAssigmentDate(LocalDateTime.now());
        assignmentDao.save(assignment);

        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

    @DeleteMapping("/assignment/{id}")
    public ResponseEntity<Assignment> delete(@PathVariable int id) {
        Optional<Assignment> assignment = assignmentDao.findById(id);

        if (assignment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        assignmentDao.delete(assignment.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/assignment/{id}")
    @JsonView(AssignmentView.class)
    public ResponseEntity<Assignment> update(@PathVariable int id, @RequestBody @Validated() Assignment assignmentToUpdate) {
        Optional<Assignment> assignment = assignmentDao.findById(id);

        if (assignment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (assignmentToUpdate.getTicket() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (assignmentToUpdate.getManager() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (assignmentToUpdate.getTechnician() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        assignmentToUpdate.setIdAssignment(id);
        assignmentToUpdate.setAssigmentDate(assignment.get().getAssigmentDate());
        assignmentDao.save(assignmentToUpdate);

        return new ResponseEntity<>(assignmentToUpdate, HttpStatus.OK);
    }

    @PutMapping("assignment/close/{id}")
    public ResponseEntity<Assignment> close(@PathVariable int id, @RequestBody @Validated() Assignment assignmentToUpdate) {
        Optional<Assignment> assignment = assignmentDao.findById(id);

        if (assignment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        assignmentToUpdate.setIdAssignment(id);
        assignmentToUpdate.setAssigmentDate(assignment.get().getAssigmentDate());
        assignmentToUpdate.setTicket(assignment.get().getTicket());
        assignmentToUpdate.setManager(assignment.get().getManager());
        assignmentToUpdate.setTechnician(assignment.get().getTechnician());
        assignmentToUpdate.setEndDate(LocalDateTime.now());
        assignmentDao.save(assignmentToUpdate);

        return new ResponseEntity<>(assignmentToUpdate, HttpStatus.OK);
    }
}
