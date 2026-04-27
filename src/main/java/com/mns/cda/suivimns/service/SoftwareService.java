package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import com.mns.cda.suivimns.dto.flat.SoftwareDtoFlat;
import com.mns.cda.suivimns.model.Software;
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
        Software currentSoftware = softwareDao.findById(id)
                .orElseThrow(iSoftwareService.SoftwareNotFoundException::new);

        currentSoftware.setName(softwareToUpdate.getName());
        currentSoftware.setDescription(softwareToUpdate.getDescription());

        currentSoftware.setType(softwareToUpdate.getType());

        return softwareDao.save(currentSoftware);
    }

    @Transactional
    @Override
    public Software createSoftware(SoftwareDtoFlat softwareToCreate) {
        Software software = new Software();
        software.setName(softwareToCreate.name());
        software.setDescription(softwareToCreate.description());

        software.setType(softwareToCreate.type());
        software.setVersionList(softwareToCreate.versionList());

        return softwareDao.save(software);
    }
}
