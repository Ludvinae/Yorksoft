package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.dto.VersionDto;
import com.mns.cda.suivimns.model.Version;

import java.util.List;
import java.util.Optional;

public interface iVersionService {
    List<VersionDto> findAll();

    Optional<Version> findById(int id);

    VersionDto save(VersionDto version);

    void delete(Version version);

    Version update(Version versionToUpdate, int id) throws VersionNotFoundException;

    class VersionNotFoundException extends Exception {
    }
}
