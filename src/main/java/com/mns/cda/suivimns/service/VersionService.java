package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionDao;
import com.mns.cda.suivimns.dto.VersionDto;
import com.mns.cda.suivimns.dto.flat.VersionDetailDto;
import com.mns.cda.suivimns.mapper.VersionMapper;
import com.mns.cda.suivimns.model.Version;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VersionService  {

    // Custom exception
    public static class VersionNotFoundException extends RuntimeException {}

    protected final VersionDao versionDao;
    protected final VersionMapper versionMapper;

    public List<VersionDto> findAll() {
        return versionMapper.toDtoList(versionDao.findAll());
    }

    public List<VersionDetailDto> findAllDetail() {
        return versionDao.findAllDetail();
    }

    public VersionDto findById(int id) throws VersionNotFoundException {
        Version version = versionDao.findById(id)
                .orElseThrow(VersionService.VersionNotFoundException::new);

        return versionMapper.toDto(version);
    }

    public VersionDetailDto findByIdDetail(int id) throws VersionNotFoundException {
        return versionDao.findByIdDetail(id);
    }

    public VersionDto save(VersionDto dto) {
        Version version = versionMapper.toEntity(dto);
        version.setIdVersion(null);
        Version saved = versionDao.save(version);

        return versionMapper.toDto(saved);
    }

    public void delete(int id) throws VersionNotFoundException {
        Version version = versionDao.findById(id)
                .orElseThrow(VersionService.VersionNotFoundException::new);

        versionDao.delete(version);
    }

    public VersionDto update(int id, VersionDto versionToUpdate) throws VersionNotFoundException {

        Version currentVersion = versionDao.findById(id)
                .orElseThrow(VersionNotFoundException::new);

        versionMapper.updateEntityFromDto(versionToUpdate, currentVersion);

        return versionMapper.toDto(versionDao.save(currentVersion));
    }
}
