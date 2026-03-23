package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.AssignmentDao;
import com.mns.cda.suivimns.model.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AssignmentController {

    protected AssignmentDao assignmentDao;

    @Autowired
    public AssignmentController(AssignmentDao assignmentDao) {
        this.assignmentDao = assignmentDao;
    }

    @GetMapping("/assignment/list")
    public List<Assignment> getAll() {
        return assignmentDao.findAll();
    }

    @GetMapping("/assignment/{id}")
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
    public ResponseEntity<Assignment> update(@PathVariable int id, @RequestBody @Validated() Assignment assignmentToUpdate) {
        Optional<Assignment> assignment = assignmentDao.findById(id);

        if (assignment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        assignmentToUpdate.setIdAssignment(id);
        assignmentDao.save(assignmentToUpdate);

        return new ResponseEntity<>(assignmentToUpdate, HttpStatus.OK);
    }

}
