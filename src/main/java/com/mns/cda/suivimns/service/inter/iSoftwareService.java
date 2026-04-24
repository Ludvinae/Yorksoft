package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.dto.flat.SoftwareDto;
import com.mns.cda.suivimns.model.Software;

import java.util.List;
import java.util.Optional;

public interface iSoftwareService {
    Software createSoftware(SoftwareDto softwareToCreate);

    List<Software> findAll();

    Optional<Software> findById(int id);

    Software save(Software software);

    void delete(Software software);

    Software update(Software softwareToUpdate, int id) throws SoftwareNotFoundException;

    class SoftwareNotFoundException extends Exception {
    }
}
