package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ManagerDao;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.service.inter.iManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService implements iManagerService {

    protected final ManagerDao managerDao;

    @Override
    public List<Manager> findAll() {
        return managerDao.findAll();
    }

    @Override
    public Optional<Manager> findById(int id) {
        return managerDao.findById(id);
    }

    @Override
    public void save(Manager manager) {
        manager.setIdAppUser(null);
        managerDao.save(manager);
    }

    @Override
    public void delete(Manager manager) {
        managerDao.delete(manager);
    }

    @Override
    public void update(Manager managerToUpdate, int id) throws iManagerService.ManagerNotFoundException {
        Optional<Manager> manager = managerDao.findById(id);

        if (manager.isEmpty()) {
            throw new iManagerService.ManagerNotFoundException();
        }

        managerToUpdate.setIdAppUser(manager.get().getIdAppUser());

        managerDao.save(managerToUpdate);
    }
}
