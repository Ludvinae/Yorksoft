package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.VersionType;

import java.util.List;
import java.util.Optional;

public interface iVersionTypeService {
    List<VersionType> findAll();

    Optional<VersionType> findById(int id);

    VersionType save(VersionType versionType);

    void delete(VersionType versionType);

    VersionType update(VersionType versionTypeToUpdate, int id) throws VersionTypeNotFoundException;

    class VersionTypeNotFoundException extends Exception {
    }
}
