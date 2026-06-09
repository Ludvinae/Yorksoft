package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.dto.entity.SoftwareDto;
import com.mns.cda.suivimns.dto.flat.SoftwareDetailDto;
import com.mns.cda.suivimns.exception.SoftwareNotFoundException;
import com.mns.cda.suivimns.mapper.entity.SoftwareMapper;
import com.mns.cda.suivimns.model.Software;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoftwareService {

    protected final SoftwareDao softwareDao;
    protected final SoftwareMapper softwareMapper;

    public List<SoftwareDto> findAll() {
        return softwareMapper.toDtoList(softwareDao.findAll());
    }

    public List<SoftwareDetailDto> findAllDetail() {
        return softwareDao.findAllDetail();
    }

    public SoftwareDto findById(int id) {
        Software software = softwareDao.findById(id)
                .orElseThrow(SoftwareNotFoundException::new);

        return softwareMapper.toDto(software);
    }

    public SoftwareDetailDto findByIdDetail(int id) {
        return softwareDao.findByIdDetail(id);
    }

    public SoftwareDto save(SoftwareDto dto) {
        Software software = softwareMapper.toEntity(dto);
        software.setIdSoftware(null);
        Software saved = softwareDao.save(software);

        return softwareMapper.toDto(saved);
    }

    public void delete(int id) {
        Software software = softwareDao.findById(id)
                .orElseThrow(SoftwareNotFoundException::new);

        softwareDao.delete(software);
    }

    public SoftwareDto update(int id, SoftwareDto softwareToUpdate) {

        Software currentSoftware = softwareDao.findById(id)
                .orElseThrow(SoftwareNotFoundException::new);

        softwareMapper.updateEntityFromDto(softwareToUpdate, currentSoftware);

        return softwareMapper.toDto(softwareDao.save(currentSoftware));
    }
}
