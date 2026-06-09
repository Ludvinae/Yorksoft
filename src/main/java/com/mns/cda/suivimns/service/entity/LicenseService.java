package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.LicenseDao;
import com.mns.cda.suivimns.dto.entity.LicenseDto;
import com.mns.cda.suivimns.exception.LicenseNotFoundException;
import com.mns.cda.suivimns.mapper.entity.LicenseMapper;
import com.mns.cda.suivimns.model.License;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseService  {

    protected final LicenseDao licenseDao;
    protected final LicenseMapper licenseMapper;

    public List<LicenseDto> findAll() {
        return licenseMapper.toDtoList(licenseDao.findAll());
    }

    public LicenseDto findById(int id) {
        License license = licenseDao.findById(id)
                .orElseThrow(LicenseNotFoundException::new);

        return licenseMapper.toDto(license);
    }

    public LicenseDto save(LicenseDto dto) {
        License license = licenseMapper.toEntity(dto);
        license.setIdLicense(null);
        License saved = licenseDao.save(license);

        return licenseMapper.toDto(saved);
    }

    public void delete(int id) {
        License license = licenseDao.findById(id)
                .orElseThrow(LicenseNotFoundException::new);

        licenseDao.delete(license);
    }

    public LicenseDto update(int id, LicenseDto licenseToUpdate) {

        License currentLicense = licenseDao.findById(id)
                .orElseThrow(LicenseNotFoundException::new);

        licenseMapper.updateEntityFromDto(licenseToUpdate, currentLicense);

        return licenseMapper.toDto(licenseDao.save(currentLicense));
    }
}
