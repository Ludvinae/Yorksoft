package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.service.inter.iSoftwareTypeService;
import com.mns.cda.suivimns.service.inter.iStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoftwareTypeService implements iSoftwareTypeService {

    protected final SoftwareTypeDao softwareTypeDao;

    @Override
    public List<SoftwareType> findAll() {
        return softwareTypeDao.findAll();
    }

    @Override
    public Optional<SoftwareType> findById(int id) {
        return softwareTypeDao.findById(id);
    }

    @Override
    public SoftwareType save(SoftwareType softwareType) {
        softwareType.setIdSoftwareType(null);
        return softwareTypeDao.save(softwareType);
    }

    @Override
    public void delete(SoftwareType softwareType) {
        softwareTypeDao.delete(softwareType);
    }

    @Override
    public SoftwareType update(SoftwareType softwareTypeToUpdate, int id) throws iSoftwareTypeService.SoftwareTypeNotFoundException {
        SoftwareType currentSoftwareType = softwareTypeDao.findById(id)
                .orElseThrow(iSoftwareTypeService.SoftwareTypeNotFoundException::new);

        currentSoftwareType.setDesignation(softwareTypeToUpdate.getDesignation());

        return softwareTypeDao.save(currentSoftwareType);
    }
}
