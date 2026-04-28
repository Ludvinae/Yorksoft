package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.UrgencyDao;
import com.mns.cda.suivimns.model.Urgency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrgencyService  {

    public static class UrgencyNotFoundException extends Exception {
    }

    protected final UrgencyDao urgencyDao;

    public List<Urgency> findAll() {
        return urgencyDao.findAll();
    }

    public Optional<Urgency> findById(int id) {
        return urgencyDao.findById(id);
    }

    public Urgency save(Urgency urgency) {
        urgency.setIdUrgency(null);
        return urgencyDao.save(urgency);
    }

    public void delete(Urgency urgency) {
        urgencyDao.delete(urgency);
    }

    public Urgency update(Urgency urgencyToUpdate, int id) throws UrgencyNotFoundException {
        Urgency currentUrgency = urgencyDao.findById(id)
                .orElseThrow(UrgencyNotFoundException::new);

        currentUrgency.setDesignation(urgencyToUpdate.getDesignation());
        currentUrgency.setDescription(urgencyToUpdate.getDescription());
        currentUrgency.setPriorityFactor(urgencyToUpdate.getPriorityFactor());

        return urgencyDao.save(currentUrgency);
    }
}
