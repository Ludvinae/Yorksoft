package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.service.inter.iSoftwareTypeService;

import java.util.List;
import java.util.Optional;

public class MockSoftwareTypeService implements iSoftwareTypeService {
    @Override
    public List<SoftwareType> findAll() {
        return List.of();
    }

    @Override
    public Optional<SoftwareType> findById(int id) {
        return Optional.empty();
    }

    @Override
    public SoftwareType save(SoftwareType softwareType) {
        return null;
    }

    @Override
    public void delete(SoftwareType softwareType) {

    }

    @Override
    public SoftwareType update(SoftwareType softwareTypeToUpdate, int id) throws SoftwareTypeNotFoundException {
        return null;
    }
}
