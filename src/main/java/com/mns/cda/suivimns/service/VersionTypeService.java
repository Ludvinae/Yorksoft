package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionTypeDao;
import com.mns.cda.suivimns.dto.VersionTypeDto;
import com.mns.cda.suivimns.mapper.VersionTypeMapper;
import com.mns.cda.suivimns.model.VersionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VersionTypeService {

    // Custom exception
    public static class VersionTypeNotFoundException extends Exception {}

    protected final VersionTypeDao versionTypeDao;
    private final VersionTypeMapper typeMapper;

    public List<VersionTypeDto> findAll() {
        return typeMapper.toDtoList(versionTypeDao.findAll());
    }

    public VersionTypeDto findById(int id) throws VersionTypeNotFoundException{
        VersionType type = versionTypeDao.findById(id)
                .orElseThrow(VersionTypeNotFoundException::new);

        return typeMapper.toDto(type);
    }

    public VersionTypeDto save(VersionTypeDto createDto) {

        VersionType type = typeMapper.toEntity(createDto);
        type.setIdVersionType(null);
        if (type.getUrgencyMalus() == null) {
            type.setUrgencyMalus((byte) 0);
        }
        VersionType saved = versionTypeDao.save(type);

        return typeMapper.toDto(saved);
    }

    public void delete(int id) throws VersionTypeNotFoundException {
        VersionType type = versionTypeDao.findById(id)
                .orElseThrow(VersionTypeNotFoundException::new);

        versionTypeDao.delete(type);
    }

    public VersionTypeDto update(int id, VersionTypeDto versionTypeToUpdate) throws VersionTypeNotFoundException {

        VersionType type = versionTypeDao.findById(id)
                .orElseThrow(VersionTypeNotFoundException::new);

        type.setDesignation(versionTypeToUpdate.designation());
        type.setUrgencyMalus(versionTypeToUpdate.urgencyMalus());

        return typeMapper.toDto(versionTypeDao.save(type));
    }
}
