package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.dto.VersionTypeDto;
import com.mns.cda.suivimns.service.inter.iVersionTypeService;

import java.util.List;

public class MockVersionTypeService implements iVersionTypeService {

    @Override
    public List<VersionTypeDto> findAll() {
        return List.of();
    }

    @Override
    public VersionTypeDto findById(int id) {
        return null;
    }

    @Override
    public VersionTypeDto save(VersionTypeDto versionType) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public VersionTypeDto update(int id, VersionTypeDto versionTypeToUpdate) throws VersionTypeNotFoundException {
        return null;
    }
}
