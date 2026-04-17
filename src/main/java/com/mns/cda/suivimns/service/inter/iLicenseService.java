package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.License;

import java.util.List;
import java.util.Optional;

public interface iLicenseService {
    List<License> findAll();

    Optional<License> findById(int id);

    void save(License license);

    void delete(License license);

    void update(License licenseToUpdate, int id) throws LicenseNotFoundException;

    public static class LicenseNotFoundException extends Exception {
    }
}
