package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.model.Technician;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TechnicianService  {

    public static class TechnicianNotFoundException extends Exception {
    }

    protected final TechnicianDao technicianDao;

    public List<Technician> findAll() {
        return technicianDao.findAll();
    }

    public Optional<Technician> findById(int id) {
        return technicianDao.findById(id);
    }

    public Technician save(Technician technician) {
        technician.setIdAppUser(null);
        return technicianDao.save(technician);
    }

    public void delete(Technician technician) {
        technicianDao.delete(technician);
    }

    public Technician update(Technician technicianToUpdate, int id) throws TechnicianNotFoundException {
        Optional<Technician> technician = technicianDao.findById(id);

        if (technician.isEmpty()) {
            throw new TechnicianNotFoundException();
        }

        technicianToUpdate.setIdAppUser(technician.get().getIdAppUser());

        return technicianDao.save(technicianToUpdate);
    }
}
