package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.service.inter.iSoftwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoftwareService implements iSoftwareService {

    protected final SoftwareDao softwareDao;

    @Override
    public List<Software> findAll() {
        return softwareDao.findAll();
    }

    @Override
    public Optional<Software> findById(int id) {
        return softwareDao.findById(id);
    }

    @Override
    public void save(Software software) {
        software.setIdSoftware(null);
        softwareDao.save(software);
    }

    @Override
    public void delete(Software software) {
        softwareDao.delete(software);
    }

    @Override
    public void update(Software softwareToUpdate, int id) throws iSoftwareService.SoftwareNotFoundException {
        Optional<Software> software = softwareDao.findById(id);

        if (software.isEmpty()) {
            throw new iSoftwareService.SoftwareNotFoundException();
        }

        softwareToUpdate.setIdSoftware(software.get().getIdSoftware());

        softwareDao.save(softwareToUpdate);
    }
}
