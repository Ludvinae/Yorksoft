package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import com.mns.cda.suivimns.dto.entity.SoftwareTypeDto;
import com.mns.cda.suivimns.mapper.entity.SoftwareTypeMapper;
import com.mns.cda.suivimns.model.SoftwareType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoftwareTypeService  {

    public static class SoftwareTypeNotFoundException extends RuntimeException {
    }

    protected final SoftwareTypeDao softwareTypeDao;
    protected final SoftwareTypeMapper softwareTypeMapper;

    public List<SoftwareTypeDto> findAll() {
        return softwareTypeMapper.toDtoList(softwareTypeDao.findAll());
    }

    public SoftwareTypeDto findById(int id) throws SoftwareTypeService.SoftwareTypeNotFoundException {
        SoftwareType softwareType = softwareTypeDao.findById(id)
                .orElseThrow(SoftwareTypeService.SoftwareTypeNotFoundException::new);

        return softwareTypeMapper.toDto(softwareType);
    }

    public SoftwareTypeDto save(SoftwareTypeDto dto) {
        SoftwareType softwareType = softwareTypeMapper.toEntity(dto);
        softwareType.setIdSoftwareType(null);
        SoftwareType saved = softwareTypeDao.save(softwareType);

        return softwareTypeMapper.toDto(saved);
    }

    public void delete(int id) throws SoftwareTypeService.SoftwareTypeNotFoundException {
        SoftwareType softwareType = softwareTypeDao.findById(id)
                .orElseThrow(SoftwareTypeService.SoftwareTypeNotFoundException::new);

        softwareTypeDao.delete(softwareType);
    }

    public SoftwareTypeDto update(int id, SoftwareTypeDto softwareTypeToUpdate) throws SoftwareTypeService.SoftwareTypeNotFoundException {

        SoftwareType currentSoftwareType = softwareTypeDao.findById(id)
                .orElseThrow(SoftwareTypeService.SoftwareTypeNotFoundException::new);

        softwareTypeMapper.updateEntityFromDto(softwareTypeToUpdate, currentSoftwareType);

        return softwareTypeMapper.toDto(softwareTypeDao.save(currentSoftwareType));
    }
}
