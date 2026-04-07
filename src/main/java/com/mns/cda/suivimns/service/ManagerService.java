package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ManagerDao;
import com.mns.cda.suivimns.dao.ManagerDao;
import com.mns.cda.suivimns.model.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {

    public static class ManagerNotFoundException extends Exception {}

    protected final ManagerDao managerDao;

    public List<Manager> findAll() {
        return managerDao.findAll();
    }

    public Optional<Manager> findById(int id) {
        return managerDao.findById(id);
    }

    public void save(Manager manager) {
        manager.setIdAppUser(null);
        managerDao.save(manager);
    }

    public void delete(Manager manager) {
        managerDao.delete(manager);
    }

    public void update(Manager managerToUpdate, int id) throws ManagerService.ManagerNotFoundException {
        Optional<Manager> manager = managerDao.findById(id);

        if (manager.isEmpty()) {
            throw new ManagerService.ManagerNotFoundException();
        }

        managerToUpdate.setIdAppUser(manager.get().getIdAppUser());

        managerDao.save(managerToUpdate);
    }
}
