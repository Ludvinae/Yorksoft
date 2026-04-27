package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionTypeDao;
import com.mns.cda.suivimns.dto.VersionTypeDto;
import com.mns.cda.suivimns.mapper.VersionTypeMapper;
import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.service.inter.iVersionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VersionTypeService implements iVersionTypeService {

    protected final VersionTypeDao versionTypeDao;
    private final VersionTypeMapper typeMapper;

    @Override
    public List<VersionTypeDto> findAll() {
        return typeMapper.toDtoList(versionTypeDao.findAll());
    }

    @Override
    public VersionTypeDto findById(int id) throws iVersionTypeService.VersionTypeNotFoundException{
        VersionType type = versionTypeDao.findById(id)
                .orElseThrow(VersionTypeNotFoundException::new);

        return typeMapper.toDto(type);
    }

    @Override
    public VersionTypeDto save(VersionTypeCreateDto createDto) {

        VersionType type = typeMapper.toEntity(createDto);
        VersionType saved = versionTypeDao.save(type);

        return typeMapper.toDto(saved);
    }

    @Override
    public void delete(int id) throws iVersionTypeService.VersionTypeNotFoundException {
        VersionType type = versionTypeDao.findById(id)
                .orElseThrow(VersionTypeNotFoundException::new);

        versionTypeDao.delete(type);
    }

    @Override
    public VersionTypeDto update(int id, VersionTypeCreateDto versionTypeToUpdate) throws iVersionTypeService.VersionTypeNotFoundException {

        VersionType type = versionTypeDao.findById(id)
                .orElseThrow(iVersionTypeService.VersionTypeNotFoundException::new);

        //typeMapper.updateEntityFromDto(versionTypeToUpdate, type);
        typeMapper.toEntity(versionTypeToUpdate);

        VersionType saved = versionTypeDao.save(type);

        return typeMapper.toDto(saved);
    }
}
