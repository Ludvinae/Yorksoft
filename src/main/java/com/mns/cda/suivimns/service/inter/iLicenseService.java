package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.License;

import java.util.List;
import java.util.Optional;

public interface iLicenseService {
    List<License> findAll();

    Optional<License> findById(int id);

    License save(License license);

    void delete(License license);

    License update(License licenseToUpdate, int id) throws LicenseNotFoundException;

    class LicenseNotFoundException extends Exception {
    }
}
