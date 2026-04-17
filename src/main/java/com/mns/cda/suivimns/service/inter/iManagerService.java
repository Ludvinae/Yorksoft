package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Manager;

import java.util.List;
import java.util.Optional;

public interface iManagerService {
    List<Manager> findAll();

    Optional<Manager> findById(int id);

    void save(Manager manager);

    void delete(Manager manager);

    void update(Manager managerToUpdate, int id) throws ManagerNotFoundException;

    class ManagerNotFoundException extends Exception {
    }
}
