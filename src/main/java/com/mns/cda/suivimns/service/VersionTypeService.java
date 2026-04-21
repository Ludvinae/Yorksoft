package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionTypeDao;
import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.service.inter.iVersionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VersionTypeService implements iVersionTypeService {

    protected final VersionTypeDao versionTypeDao;

    @Override
    public List<VersionType> findAll() {
        return versionTypeDao.findAll();
    }

    @Override
    public Optional<VersionType> findById(int id) {
        return versionTypeDao.findById(id);
    }

    @Override
    public VersionType save(VersionType versionType) {
        versionType.setIdVersionType(null);
        return versionTypeDao.save(versionType);
    }

    @Override
    public void delete(VersionType versionType) {
        versionTypeDao.delete(versionType);
    }

    @Override
    public void update(VersionType versionTypeToUpdate, int id) throws iVersionTypeService.VersionTypeNotFoundException {
        Optional<VersionType> versionType = versionTypeDao.findById(id);

        if (versionType.isEmpty()) {
            throw new iVersionTypeService.VersionTypeNotFoundException();
        }

        versionTypeToUpdate.setIdVersionType(versionType.get().getIdVersionType());

        versionTypeDao.save(versionTypeToUpdate);
    }
}
