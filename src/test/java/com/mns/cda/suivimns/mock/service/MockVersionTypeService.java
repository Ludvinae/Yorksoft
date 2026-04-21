package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.service.inter.iVersionTypeService;

import java.util.List;
import java.util.Optional;

public class MockVersionTypeService implements iVersionTypeService {

    @Override
    public List<VersionType> findAll() {
        return List.of();
    }

    @Override
    public Optional<VersionType> findById(int id) {
        return Optional.empty();
    }

    @Override
    public VersionType save(VersionType versionType) {
        return null;
    }

    @Override
    public void delete(VersionType versionType) {

    }

    @Override
    public VersionType update(VersionType versionTypeToUpdate, int id) throws VersionTypeNotFoundException {
        return null;
    }
}
