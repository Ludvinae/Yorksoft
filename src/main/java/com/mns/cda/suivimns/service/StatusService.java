package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService {

    public static class StatusNotFoundException extends Exception {}

    protected final StatusDao statusDao;

    public List<Status> findAll() {
        return statusDao.findAll();
    }

    public Optional<Status> findById(int id) {
        return statusDao.findById(id);
    }

    public Optional<Status> findByDesignation(String designation) {
        return statusDao.findByDesignation(designation);
    }

    public void save(Status status) {
        status.setIdStatus(null);
        statusDao.save(status);
    }

    public void delete(Status status) {
        statusDao.delete(status);
    }

    public void update(Status statusToUpdate, int id) throws StatusService.StatusNotFoundException {
        Optional<Status> status = statusDao.findById(id);

        if (status.isEmpty()) {
            throw new StatusService.StatusNotFoundException();
        }

        statusToUpdate.setIdStatus(status.get().getIdStatus());

        statusDao.save(statusToUpdate);
    }
}
