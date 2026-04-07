package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import com.mns.cda.suivimns.model.SoftwareType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoftwareTypeService {

    public static class SoftwareTypeNotFoundException extends Exception {}

    protected final SoftwareTypeDao softwareTypeDao;

    public List<SoftwareType> findAll() {
        return softwareTypeDao.findAll();
    }

    public Optional<SoftwareType> findById(int id) {
        return softwareTypeDao.findById(id);
    }

    public void save(SoftwareType softwareType) {
        softwareType.setIdSoftwareType(null);
        softwareTypeDao.save(softwareType);
    }

    public void delete(SoftwareType softwareType) {
        softwareTypeDao.delete(softwareType);
    }

    public void update(SoftwareType softwareTypeToUpdate, int id) throws SoftwareTypeService.SoftwareTypeNotFoundException {
        Optional<SoftwareType> softwareType = softwareTypeDao.findById(id);

        if (softwareType.isEmpty()) {
            throw new SoftwareTypeService.SoftwareTypeNotFoundException();
        }

        softwareTypeToUpdate.setIdSoftwareType(softwareType.get().getIdSoftwareType());

        softwareTypeDao.save(softwareTypeToUpdate);
    }
}
