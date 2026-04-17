package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Status;

import java.util.List;
import java.util.Optional;

public interface iStatusService {
    List<Status> findAll();

    Optional<Status> findById(int id);

    Optional<Status> findByDesignation(String designation);

    void save(Status status);

    void delete(Status status);

    void update(Status statusToUpdate, int id) throws StatusNotFoundException;

    public static class StatusNotFoundException extends Exception {
    }
}
