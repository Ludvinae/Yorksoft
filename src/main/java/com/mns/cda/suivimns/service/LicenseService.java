package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.LicenseDao;
import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.dao.LicenseDao;
import com.mns.cda.suivimns.model.License;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseService {

    public static class LicenseNotFoundException extends Exception {}

    protected final LicenseDao licenseDao;

    public List<License> findAll() {
        return licenseDao.findAll();
    }

    public Optional<License> findById(int id) {
        return licenseDao.findById(id);
    }

    public void save(License license) {
        license.setIdLicense(null);
        licenseDao.save(license);
    }

    public void delete(License license) {
        licenseDao.delete(license);
    }

    public void update(License licenseToUpdate, int id) throws LicenseService.LicenseNotFoundException {
        Optional<License> license = licenseDao.findById(id);

        if (license.isEmpty()) {
            throw new LicenseService.LicenseNotFoundException();
        }

        licenseToUpdate.setIdLicense(license.get().getIdLicense());

        licenseDao.save(licenseToUpdate);
    }
}
