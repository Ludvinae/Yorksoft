package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionTypeDao;
import com.mns.cda.suivimns.dao.VersionTypeDao;
import com.mns.cda.suivimns.model.VersionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VersionTypeService {

    public static class VersionTypeNotFoundException extends Exception {}

    protected final VersionTypeDao versionTypeDao;

    public List<VersionType> findAll() {
        return versionTypeDao.findAll();
    }

    public Optional<VersionType> findById(int id) {
        return versionTypeDao.findById(id);
    }

    public void save(VersionType versionType) {
        versionType.setIdVersionType(null);
        versionTypeDao.save(versionType);
    }

    public void delete(VersionType versionType) {
        versionTypeDao.delete(versionType);
    }

    public void update(VersionType versionTypeToUpdate, int id) throws VersionTypeService.VersionTypeNotFoundException {
        Optional<VersionType> versionType = versionTypeDao.findById(id);

        if (versionType.isEmpty()) {
            throw new VersionTypeService.VersionTypeNotFoundException();
        }

        versionTypeToUpdate.setIdVersionType(versionType.get().getIdVersionType());

        versionTypeDao.save(versionTypeToUpdate);
    }
}
