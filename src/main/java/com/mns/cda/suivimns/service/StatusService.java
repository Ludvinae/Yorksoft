package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.service.inter.iStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService implements iStatusService {

    protected final StatusDao statusDao;

    @Override
    public List<Status> findAll() {
        return statusDao.findAll();
    }

    @Override
    public Optional<Status> findById(int id) {
        return statusDao.findById(id);
    }

    @Override
    public Optional<Status> findByDesignation(String designation) {
        return statusDao.findByDesignation(designation);
    }

    @Override
    public void save(Status status) {
        status.setIdStatus(null);
        statusDao.save(status);
    }

    @Override
    public void delete(Status status) {
        statusDao.delete(status);
    }

    @Override
    public void update(Status statusToUpdate, int id) throws iStatusService.StatusNotFoundException {
        Optional<Status> status = statusDao.findById(id);

        if (status.isEmpty()) {
            throw new iStatusService.StatusNotFoundException();
        }

        statusToUpdate.setIdStatus(status.get().getIdStatus());

        statusDao.save(statusToUpdate);
    }
}
