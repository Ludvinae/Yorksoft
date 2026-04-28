package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionDao;
import com.mns.cda.suivimns.dto.VersionDto;
import com.mns.cda.suivimns.mapper.VersionMapper;
import com.mns.cda.suivimns.model.Version;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VersionService  {

    public static class VersionNotFoundException extends Exception {
    }

    protected final VersionDao versionDao;
    protected final VersionMapper versionMapper;

    public List<VersionDto> findAll() {
        return versionMapper.toDtoList(versionDao.findAll());
    }

    public Optional<Version> findById(int id) {
        return versionDao.findById(id);
    }

    public VersionDto save(VersionDto dto) {
        Version version = versionMapper.toEntity(dto);
        version.setIdVersion(null);
        Version saved = versionDao.save(version);

        return versionMapper.toDto(saved);
    }

    public void delete(Version version) {
        versionDao.delete(version);
    }

    public Version update(Version versionToUpdate, int id) throws VersionNotFoundException {
        Version currentVersion = versionDao.findById(id)
                .orElseThrow(VersionNotFoundException::new);

        currentVersion.setVersionNumber(versionToUpdate.getVersionNumber());
        currentVersion.setPublicationDate(versionToUpdate.getPublicationDate());

        currentVersion.setVersionType(versionToUpdate.getVersionType());

        return versionDao.save(currentVersion);
    }
}
