package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Urgency;

import java.util.List;
import java.util.Optional;

public interface iUrgencyService {
    List<Urgency> findAll();

    Optional<Urgency> findById(int id);

    void save(Urgency urgency);

    void delete(Urgency urgency);

    void update(Urgency urgencyToUpdate, int id) throws UrgencyNotFoundException;

    class UrgencyNotFoundException extends Exception {
    }
}
