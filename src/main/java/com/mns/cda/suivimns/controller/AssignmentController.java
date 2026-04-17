package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.service.inter.iAssignmentService;
import com.mns.cda.suivimns.view.AssignmentView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    protected final iAssignmentService iAssignmentService;

    @GetMapping("/list")
    @JsonView(AssignmentView.class)
    public List<Assignment> getAll() {
        return iAssignmentService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(AssignmentView.class)
    public ResponseEntity<Assignment> getById(@PathVariable int id) {
        Optional<Assignment> assignment = iAssignmentService.findById(id);
        if (assignment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(assignment.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Assignment> create(@RequestBody @Validated() Assignment assignment) {

        iAssignmentService.firstSave(assignment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assignment);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Assignment> delete(@PathVariable int id) {
        Optional<Assignment> assignment = iAssignmentService.findById(id);

        if (assignment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iAssignmentService.delete(assignment.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/close/{id}")
    public ResponseEntity<Assignment> close(@PathVariable int id, @RequestBody @Validated() Assignment assignmentToUpdate) throws iAssignmentService.AssignmentNotFoundException {
        try {
            iAssignmentService.update(assignmentToUpdate, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (iAssignmentService.AssignmentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @JsonView(AssignmentView.class)
    public ResponseEntity<Assignment> update(@PathVariable int id, @RequestBody @Validated() Assignment assignmentToUpdate) {
        Optional<Assignment> assignment = iAssignmentService.findById(id);

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

        iAssignmentService.modify(assignmentToUpdate, id);

        return new ResponseEntity<>(assignmentToUpdate, HttpStatus.OK);
    }

}
