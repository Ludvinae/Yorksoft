package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.dto.SoftwareDto;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.service.inter.iSoftwareService;

import java.util.List;
import java.util.Optional;

public class MockSoftwareService implements iSoftwareService {
    @Override
    public Software createSoftware(SoftwareDto softwareToCreate) {
        return null;
    }

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
    public Software save(Software software) {
        return null;
    }

    @Override
    public void delete(Software software) {

    }

    @Override
    public void update(Software softwareToUpdate, int id) throws SoftwareNotFoundException {

    }
}
