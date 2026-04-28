package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ManagerDao;
import com.mns.cda.suivimns.model.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService  {

    public static class ManagerNotFoundException extends Exception {
    }

    protected final ManagerDao managerDao;

    public List<Manager> findAll() {
        return managerDao.findAll();
    }

    public Optional<Manager> findById(int id) {
        return managerDao.findById(id);
    }

    public Manager save(Manager manager) {
        manager.setIdAppUser(null);
        return managerDao.save(manager);
    }

    public void delete(Manager manager) {
        managerDao.delete(manager);
    }

    public Manager update(Manager managerToUpdate, int id) throws ManagerNotFoundException {
        Optional<Manager> manager = managerDao.findById(id);

        if (manager.isEmpty()) {
            throw new ManagerNotFoundException();
        }

        managerToUpdate.setIdAppUser(manager.get().getIdAppUser());

        return managerDao.save(managerToUpdate);
    }
}
