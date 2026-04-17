package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.service.inter.iTechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TechnicianService implements iTechnicianService {

    protected final TechnicianDao technicianDao;

    @Override
    public List<Technician> findAll() {
        return technicianDao.findAll();
    }

    @Override
    public Optional<Technician> findById(int id) {
        return technicianDao.findById(id);
    }

    @Override
    public void save(Technician technician) {
        technician.setIdAppUser(null);
        technicianDao.save(technician);
    }

    @Override
    public void delete(Technician technician) {
        technicianDao.delete(technician);
    }

    @Override
    public void update(Technician technicianToUpdate, int id) throws iTechnicianService.TechnicianNotFoundException {
        Optional<Technician> technician = technicianDao.findById(id);

        if (technician.isEmpty()) {
            throw new iTechnicianService.TechnicianNotFoundException();
        }

        technicianToUpdate.setIdAppUser(technician.get().getIdAppUser());

        technicianDao.save(technicianToUpdate);
    }
}
