package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import com.mns.cda.suivimns.model.SoftwareType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoftwareTypeService  {

    public static class SoftwareTypeNotFoundException extends Exception {
    }

    protected final SoftwareTypeDao softwareTypeDao;

    public List<SoftwareType> findAll() {
        return softwareTypeDao.findAll();
    }

    public Optional<SoftwareType> findById(int id) {
        return softwareTypeDao.findById(id);
    }

    public SoftwareType save(SoftwareType softwareType) {
        softwareType.setIdSoftwareType(null);
        return softwareTypeDao.save(softwareType);
    }

    public void delete(SoftwareType softwareType) {
        softwareTypeDao.delete(softwareType);
    }

    public SoftwareType update(SoftwareType softwareTypeToUpdate, int id) throws SoftwareTypeNotFoundException {
        SoftwareType currentSoftwareType = softwareTypeDao.findById(id)
                .orElseThrow(SoftwareTypeNotFoundException::new);

        currentSoftwareType.setDesignation(softwareTypeToUpdate.getDesignation());

        return softwareTypeDao.save(currentSoftwareType);
    }
}
