package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.model.Software;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoftwareService {

    public static class SoftwareNotFoundException extends Exception {}

    protected final SoftwareDao softwareDao;

    public List<Software> findAll() {
        return softwareDao.findAll();
    }

    public Optional<Software> findById(int id) {
        return softwareDao.findById(id);
    }

    public void save(Software software) {
        software.setIdSoftware(null);
        softwareDao.save(software);
    }

    public void delete(Software software) {
        softwareDao.delete(software);
    }

    public void update(Software softwareToUpdate, int id) throws SoftwareService.SoftwareNotFoundException {
        Optional<Software> software = softwareDao.findById(id);

        if (software.isEmpty()) {
            throw new SoftwareService.SoftwareNotFoundException();
        }

        softwareToUpdate.setIdSoftware(software.get().getIdSoftware());

        softwareDao.save(softwareToUpdate);
    }
}
