package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Software;

import java.util.List;
import java.util.Optional;

public interface iSoftwareService {
    List<Software> findAll();

    Optional<Software> findById(int id);

    void save(Software software);

    void delete(Software software);

    void update(Software softwareToUpdate, int id) throws SoftwareNotFoundException;

    class SoftwareNotFoundException extends Exception {
    }
}
