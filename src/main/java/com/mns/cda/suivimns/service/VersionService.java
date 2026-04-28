package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionDao;
import com.mns.cda.suivimns.dto.VersionDto;
import com.mns.cda.suivimns.mapper.VersionMapper;
import com.mns.cda.suivimns.model.Version;
import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.service.inter.iVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VersionService implements iVersionService {

    protected final VersionDao versionDao;
    protected final VersionMapper versionMapper;

    @Override
    public List<VersionDto> findAll() {
        return versionMapper.toDtoList(versionDao.findAll());
    }

    @Override
    public Optional<Version> findById(int id) {
        return versionDao.findById(id);
    }

    @Override
    public VersionDto save(VersionDto dto) {
        Version version = versionMapper.toEntity(dto);
        version.setIdVersion(null);
        Version saved = versionDao.save(version);

        return versionMapper.toDto(saved);
    }

    @Override
    public void delete(Version version) {
        versionDao.delete(version);
    }

    @Override
    public Version update(Version versionToUpdate, int id) throws iVersionService.VersionNotFoundException {
        Version currentVersion = versionDao.findById(id)
                .orElseThrow(iVersionService.VersionNotFoundException::new);

        currentVersion.setVersionNumber(versionToUpdate.getVersionNumber());
        currentVersion.setPublicationDate(versionToUpdate.getPublicationDate());

        currentVersion.setVersionType(versionToUpdate.getVersionType());

        return versionDao.save(currentVersion);
    }
}
