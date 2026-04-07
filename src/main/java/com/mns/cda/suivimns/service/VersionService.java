package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionDao;
import com.mns.cda.suivimns.dao.VersionDao;
import com.mns.cda.suivimns.model.Version;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VersionService {

    public static class VersionNotFoundException extends Exception {}

    protected final VersionDao versionDao;

    public List<Version> findAll() {
        return versionDao.findAll();
    }

    public Optional<Version> findById(int id) {
        return versionDao.findById(id);
    }

    public void save(Version version) {
        version.setIdVersion(null);
        versionDao.save(version);
    }

    public void delete(Version version) {
        versionDao.delete(version);
    }

    public void update(Version versionToUpdate, int id) throws VersionService.VersionNotFoundException {
        Optional<Version> version = versionDao.findById(id);

        if (version.isEmpty()) {
            throw new VersionService.VersionNotFoundException();
        }

        versionToUpdate.setIdVersion(version.get().getIdVersion());

        versionDao.save(versionToUpdate);
    }
}
