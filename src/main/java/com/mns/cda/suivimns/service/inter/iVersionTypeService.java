package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.dto.VersionTypeCreateDto;
import com.mns.cda.suivimns.dto.VersionTypeResponseDto;

import java.util.List;

public interface iVersionTypeService {
    List<VersionTypeResponseDto> findAll();

    VersionTypeResponseDto findById(int id) throws VersionTypeNotFoundException;

    VersionTypeResponseDto save(VersionTypeCreateDto versionType);

    void delete(int id) throws iVersionTypeService.VersionTypeNotFoundException;

    VersionTypeResponseDto update(int id, VersionTypeCreateDto versionTypeToUpdate) throws VersionTypeNotFoundException;

    class VersionTypeNotFoundException extends Exception {
    }
}
