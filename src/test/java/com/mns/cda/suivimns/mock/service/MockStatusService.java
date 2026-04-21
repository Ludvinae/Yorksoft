package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.service.inter.iStatusService;

import java.util.List;
import java.util.Optional;

public class MockStatusService implements iStatusService {
    @Override
    public List<Status> findAll() {
        return List.of();
    }

    @Override
    public Optional<Status> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Status> findByDesignation(String designation) {
        return Optional.empty();
    }

    @Override
    public Status save(Status status) {
        return null;
    }

    @Override
    public void delete(Status status) {

    }

    @Override
    public Status update(Status statusToUpdate, int id) throws StatusNotFoundException {
        return null;
    }
}
