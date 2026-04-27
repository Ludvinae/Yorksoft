package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.dto.VersionTypeDto;

import java.util.List;

public interface iVersionTypeService {
    List<VersionTypeDto> findAll();

    VersionTypeDto findById(int id) throws VersionTypeNotFoundException;

    VersionTypeDto save(VersionTypeCreateDto versionType);

    void delete(int id) throws iVersionTypeService.VersionTypeNotFoundException;

    VersionTypeDto update(int id, VersionTypeCreateDto versionTypeToUpdate) throws VersionTypeNotFoundException;

    class VersionTypeNotFoundException extends Exception {
    }
}
