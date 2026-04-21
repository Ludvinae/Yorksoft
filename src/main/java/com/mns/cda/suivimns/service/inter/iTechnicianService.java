package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Technician;

import java.util.List;
import java.util.Optional;

public interface iTechnicianService {
    List<Technician> findAll();

    Optional<Technician> findById(int id);

    Technician save(Technician technician);

    void delete(Technician technician);

    Technician update(Technician technicianToUpdate, int id) throws TechnicianNotFoundException;

    class TechnicianNotFoundException extends Exception {
    }
}
