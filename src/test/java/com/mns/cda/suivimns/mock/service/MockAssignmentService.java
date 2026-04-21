package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.service.inter.iAssignmentService;

import java.util.List;
import java.util.Optional;

public class MockAssignmentService implements iAssignmentService {
    @Override
    public List<Assignment> findAll() {
        return List.of();
    }

    @Override
    public Optional<Assignment> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void modify(Assignment assignment, int id) {

    }

    @Override
    public Assignment firstSave(Assignment assignment) {
        return null;
    }

    @Override
    public void close(Assignment assignment, int id) {

    }

    @Override
    public void delete(Assignment assignment) {

    }

    @Override
    public void update(Assignment assignmentToUpdate, int id) throws AssignmentNotFoundException {

    }
}
