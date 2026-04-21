package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.service.inter.iTechnicianService;

import java.util.List;
import java.util.Optional;

public class MockTechnicianService implements iTechnicianService {
    @Override
    public List<Technician> findAll() {
        return List.of();
    }

    @Override
    public Optional<Technician> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Technician save(Technician technician) {
        return null;
    }

    @Override
    public void delete(Technician technician) {

    }

    @Override
    public void update(Technician technicianToUpdate, int id) throws TechnicianNotFoundException {

    }
}
