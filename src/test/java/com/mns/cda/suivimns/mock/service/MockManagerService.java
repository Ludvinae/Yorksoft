package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.service.inter.iManagerService;

import java.util.List;
import java.util.Optional;

public class MockManagerService implements iManagerService {

    @Override
    public List<Manager> findAll() {
        return List.of();
    }

    @Override
    public Optional<Manager> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Manager save(Manager manager) {
        return null;
    }

    @Override
    public void delete(Manager manager) {

    }

    @Override
    public void update(Manager managerToUpdate, int id) throws ManagerNotFoundException {

    }
}
