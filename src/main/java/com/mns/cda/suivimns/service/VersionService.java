package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionDao;
import com.mns.cda.suivimns.model.Version;
import com.mns.cda.suivimns.service.inter.iVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VersionService implements iVersionService {

    protected final VersionDao versionDao;

    @Override
    public List<Version> findAll() {
        return versionDao.findAll();
    }

    @Override
    public Optional<Version> findById(int id) {
        return versionDao.findById(id);
    }

    @Override
    public Version save(Version version) {
        version.setIdVersion(null);
        return versionDao.save(version);
    }

    @Override
    public void delete(Version version) {
        versionDao.delete(version);
    }

    @Override
    public Version update(Version versionToUpdate, int id) throws iVersionService.VersionNotFoundException {
        Optional<Version> version = versionDao.findById(id);

        if (version.isEmpty()) {
            throw new iVersionService.VersionNotFoundException();
        }

        versionToUpdate.setIdVersion(version.get().getIdVersion());

        return versionDao.save(versionToUpdate);
    }
}
