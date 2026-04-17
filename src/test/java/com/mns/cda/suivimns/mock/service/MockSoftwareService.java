package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.service.inter.iSoftwareService;

import java.util.List;
import java.util.Optional;

public class MockSoftwareService implements iSoftwareService {
    @Override
    public List<Software> findAll() {
        return List.of();
    }

    @Override
    public Optional<Software> findById(int id) {
        if (id == 1) return Optional.of(
                new Software(1, "TestSoft", "this is exclusively  for testing purpose",
                        new SoftwareType(11, "mock type"),null));

        return Optional.empty();
    }

    @Override
    public void save(Software software) {

    }

    @Override
    public void delete(Software software) {

    }

    @Override
    public void update(Software softwareToUpdate, int id) throws SoftwareNotFoundException {

    }
}
