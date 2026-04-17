package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Version;

import java.util.List;
import java.util.Optional;

public interface iVersionService {
    List<Version> findAll();

    Optional<Version> findById(int id);

    void save(Version version);

    void delete(Version version);

    void update(Version versionToUpdate, int id) throws VersionNotFoundException;

    public static class VersionNotFoundException extends Exception {
    }
}
