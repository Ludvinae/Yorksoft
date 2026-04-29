package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.LicenseDao;
import com.mns.cda.suivimns.dao.LicenseDao;
import com.mns.cda.suivimns.dto.LicenseDto;
import com.mns.cda.suivimns.mapper.LicenseMapper;
import com.mns.cda.suivimns.model.License;
import com.mns.cda.suivimns.model.License;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseService  {

    public static class LicenseNotFoundException extends Exception {
    }

    protected final LicenseDao licenseDao;
    protected final LicenseMapper licenseMapper;

    public List<LicenseDto> findAll() {
        return licenseMapper.toDtoList(licenseDao.findAll());
    }

    public LicenseDto findById(int id) throws LicenseService.LicenseNotFoundException {
        License license = licenseDao.findById(id)
                .orElseThrow(LicenseService.LicenseNotFoundException::new);

        return licenseMapper.toDto(license);
    }

    public LicenseDto save(LicenseDto dto) {
        License license = licenseMapper.toEntity(dto);
        license.setIdLicense(null);
        License saved = licenseDao.save(license);

        return licenseMapper.toDto(saved);
    }

    public void delete(int id) throws LicenseService.LicenseNotFoundException {
        License license = licenseDao.findById(id)
                .orElseThrow(LicenseService.LicenseNotFoundException::new);

        licenseDao.delete(license);
    }

    public LicenseDto update(int id, LicenseDto licenseToUpdate) throws LicenseService.LicenseNotFoundException {

        License currentLicense = licenseDao.findById(id)
                .orElseThrow(LicenseService.LicenseNotFoundException::new);

        licenseMapper.updateEntityFromDto(licenseToUpdate, currentLicense);

        return licenseMapper.toDto(licenseDao.save(currentLicense));
    }
}
