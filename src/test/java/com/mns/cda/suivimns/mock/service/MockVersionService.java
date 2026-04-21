package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Version;
import com.mns.cda.suivimns.service.inter.iVersionService;

import java.util.List;
import java.util.Optional;

public class MockVersionService implements iVersionService {
    @Override
    public List<Version> findAll() {
        return List.of();
    }

    @Override
    public Optional<Version> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Version save(Version version) {
        return null;
    }

    @Override
    public void delete(Version version) {

    }

    @Override
    public void update(Version versionToUpdate, int id) throws VersionNotFoundException {

    }
}
