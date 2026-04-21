package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.SoftwareType;

import java.util.List;
import java.util.Optional;

public interface iSoftwareTypeService {
    List<SoftwareType> findAll();

    Optional<SoftwareType> findById(int id);

    SoftwareType save(SoftwareType softwareType);

    void delete(SoftwareType softwareType);

    void update(SoftwareType softwareTypeToUpdate, int id) throws SoftwareTypeNotFoundException;

    class SoftwareTypeNotFoundException extends Exception {
    }
}
