package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService {

    public static class StatusNotFoundException extends Exception {
    }

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

    public Status save(Status status) {
        status.setIdStatus(null);
        return statusDao.save(status);
    }

    public void delete(Status status) {
        statusDao.delete(status);
    }

    public Status update(Status statusToUpdate, int id) throws StatusNotFoundException {
        Status currentStatus = statusDao.findById(id)
                .orElseThrow(StatusNotFoundException::new);

        currentStatus.setDesignation(statusToUpdate.getDesignation());
        currentStatus.setDisplayOrder(statusToUpdate.getDisplayOrder());

        return statusDao.save(currentStatus);
    }
}
