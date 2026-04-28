package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import com.mns.cda.suivimns.dto.flat.SoftwareDtoFlat;
import com.mns.cda.suivimns.model.Software;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoftwareService {

    public static class SoftwareNotFoundException extends Exception {
    }

    protected final SoftwareDao softwareDao;
    protected final SoftwareTypeDao softwareTypeDao;

    public List<Software> findAll() {
        return softwareDao.findAll();
    }

    public Optional<Software> findById(int id) {
        return softwareDao.findById(id);
    }

    public Software save(Software software) {

        software.setIdSoftware(null);
        return softwareDao.save(software);
    }

    public void delete(Software software) {
        softwareDao.delete(software);
    }

    public Software update(Software softwareToUpdate, int id) throws SoftwareNotFoundException {
        Software currentSoftware = softwareDao.findById(id)
                .orElseThrow(SoftwareNotFoundException::new);

        currentSoftware.setName(softwareToUpdate.getName());
        currentSoftware.setDescription(softwareToUpdate.getDescription());

        currentSoftware.setType(softwareToUpdate.getType());

        return softwareDao.save(currentSoftware);
    }

    @Transactional
    public Software createSoftware(SoftwareDtoFlat softwareToCreate) {
        Software software = new Software();
        software.setName(softwareToCreate.name());
        software.setDescription(softwareToCreate.description());

        software.setType(softwareToCreate.type());
        //software.setVersionList(softwareToCreate.versionList());

        return softwareDao.save(software);
    }
}
