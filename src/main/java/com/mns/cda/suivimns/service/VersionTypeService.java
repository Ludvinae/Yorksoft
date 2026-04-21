package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionTypeDao;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.service.inter.iCommentService;
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
    public VersionType update(VersionType versionTypeToUpdate, int id) throws iVersionTypeService.VersionTypeNotFoundException {
        VersionType currentType = versionTypeDao.findById(id)
                .orElseThrow(iVersionTypeService.VersionTypeNotFoundException::new);

        currentType.setDesignation(versionTypeToUpdate.getDesignation());
        currentType.setUrgencyMalus(versionTypeToUpdate.getUrgencyMalus());

        return versionTypeDao.save(currentType);
    }
}
