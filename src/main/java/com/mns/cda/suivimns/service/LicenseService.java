package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.LicenseDao;
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

    public List<License> findAll() {
        return licenseDao.findAll();
    }

    public Optional<License> findById(int id) {
        return licenseDao.findById(id);
    }

    public License save(License license) {
        license.setIdLicense(null);
        return licenseDao.save(license);
    }

    public void delete(License license) {
        licenseDao.delete(license);
    }

    public License update(License licenseToUpdate, int id) throws LicenseNotFoundException {
        License currentLicense = licenseDao.findById(id)
                .orElseThrow(LicenseNotFoundException::new);

        currentLicense.setExpirationDate(licenseToUpdate.getExpirationDate());

        return licenseDao.save(currentLicense);
    }
}
