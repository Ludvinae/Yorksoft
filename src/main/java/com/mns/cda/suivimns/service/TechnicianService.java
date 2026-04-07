package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.model.Technician;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TechnicianService {

    public static class TechnicianNotFoundException extends Exception {}

    protected final TechnicianDao technicianDao;

    public List<Technician> findAll() {
        return technicianDao.findAll();
    }

    public Optional<Technician> findById(int id) {
        return technicianDao.findById(id);
    }

    public void save(Technician technician) {
        technician.setIdAppUser(null);
        technicianDao.save(technician);
    }

    public void delete(Technician technician) {
        technicianDao.delete(technician);
    }

    public void update(Technician technicianToUpdate, int id) throws TechnicianService.TechnicianNotFoundException {
        Optional<Technician> technician = technicianDao.findById(id);

        if (technician.isEmpty()) {
            throw new TechnicianService.TechnicianNotFoundException();
        }

        technicianToUpdate.setIdAppUser(technician.get().getIdAppUser());

        technicianDao.save(technicianToUpdate);
    }
}
