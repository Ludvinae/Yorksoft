package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.License;
import com.mns.cda.suivimns.service.inter.iLicenseService;

import java.util.List;
import java.util.Optional;

public class MockLicenseService implements iLicenseService {
    @Override
    public List<License> findAll() {
        return List.of();
    }

    @Override
    public Optional<License> findById(int id) {
        return Optional.empty();
    }

    @Override
    public License save(License license) {
        return null;
    }

    @Override
    public void delete(License license) {

    }

    @Override
    public void update(License licenseToUpdate, int id) throws LicenseNotFoundException {

    }
}
