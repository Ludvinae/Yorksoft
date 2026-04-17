package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.service.inter.iUrgencyService;

import java.util.List;
import java.util.Optional;

public class MockUrgencyService implements iUrgencyService {
    @Override
    public List<Urgency> findAll() {
        return List.of();
    }

    @Override
    public Optional<Urgency> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Urgency urgency) {

    }

    @Override
    public void delete(Urgency urgency) {

    }

    @Override
    public void update(Urgency urgencyToUpdate, int id) throws UrgencyNotFoundException {

    }
}
