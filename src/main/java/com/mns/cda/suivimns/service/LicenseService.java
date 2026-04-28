package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.LicenseDao;
import com.mns.cda.suivimns.model.License;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.service.inter.iLicenseService;
import com.mns.cda.suivimns.service.inter.iStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseService implements iLicenseService {

    protected final LicenseDao licenseDao;

    @Override
    public List<License> findAll() {
        return licenseDao.findAll();
    }

    @Override
    public Optional<License> findById(int id) {
        return licenseDao.findById(id);
    }

    @Override
    public License save(License license) {
        license.setIdLicense(null);
        return licenseDao.save(license);
    }

    @Override
    public void delete(License license) {
        licenseDao.delete(license);
    }

    @Override
    public License update(License licenseToUpdate, int id) throws iLicenseService.LicenseNotFoundException {
        License currentLicense = licenseDao.findById(id)
                .orElseThrow(iLicenseService.LicenseNotFoundException::new);

        currentLicense.setExpirationDate(licenseToUpdate.getExpirationDate());

        return licenseDao.save(currentLicense);
    }
}
