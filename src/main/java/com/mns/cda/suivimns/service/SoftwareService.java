package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import com.mns.cda.suivimns.dto.SoftwareDto;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.service.inter.iSoftwareService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoftwareService implements iSoftwareService {

    protected final SoftwareDao softwareDao;
    protected final SoftwareTypeDao softwareTypeDao;

    @Override
    public List<Software> findAll() {
        return softwareDao.findAll();
    }

    @Override
    public Optional<Software> findById(int id) {
        return softwareDao.findById(id);
    }

    @Override
    public Software save(Software software) {

        software.setIdSoftware(null);
        return softwareDao.save(software);
    }

    @Override
    public void delete(Software software) {
        softwareDao.delete(software);
    }

    @Override
    public Software update(Software softwareToUpdate, int id) throws iSoftwareService.SoftwareNotFoundException {
        Optional<Software> software = softwareDao.findById(id);

        if (software.isEmpty()) {
            throw new iSoftwareService.SoftwareNotFoundException();
        }

        softwareToUpdate.setIdSoftware(software.get().getIdSoftware());

        return softwareDao.save(softwareToUpdate);
    }

    @Transactional
    @Override
    public Software createSoftware(SoftwareDto softwareToCreate) {
        Software software = new Software();
        software.setName(softwareToCreate.name());
        software.setDescription(softwareToCreate.description());

        software.setType(softwareToCreate.type());
        software.setVersionList(softwareToCreate.versionList());

        return softwareDao.save(software);
    }
}
