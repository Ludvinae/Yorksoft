package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.dto.VersionTypeCreateDto;
import com.mns.cda.suivimns.dto.VersionTypeResponseDto;
import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.service.inter.iVersionTypeService;

import java.util.List;
import java.util.Optional;

public class MockVersionTypeService implements iVersionTypeService {

    @Override
    public List<VersionTypeResponseDto> findAll() {
        return List.of();
    }

    @Override
    public VersionTypeResponseDto findById(int id) {
        return null;
    }

    @Override
    public VersionTypeResponseDto save(VersionTypeCreateDto versionType) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public VersionTypeResponseDto update(int id, VersionTypeCreateDto versionTypeToUpdate) throws VersionTypeNotFoundException {
        return null;
    }
}
