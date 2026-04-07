package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.UrgencyDao;
import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.dao.UrgencyDao;
import com.mns.cda.suivimns.model.Urgency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrgencyService {

    public static class UrgencyNotFoundException extends Exception {}

    protected final UrgencyDao urgencyDao;

    public List<Urgency> findAll() {
        return urgencyDao.findAll();
    }

    public Optional<Urgency> findById(int id) {
        return urgencyDao.findById(id);
    }

    public void save(Urgency urgency) {
        urgency.setIdUrgency(null);
        urgencyDao.save(urgency);
    }

    public void delete(Urgency urgency) {
        urgencyDao.delete(urgency);
    }

    public void update(Urgency urgencyToUpdate, int id) throws UrgencyService.UrgencyNotFoundException {
        Optional<Urgency> urgency = urgencyDao.findById(id);

        if (urgency.isEmpty()) {
            throw new UrgencyService.UrgencyNotFoundException();
        }

        urgencyToUpdate.setIdUrgency(urgency.get().getIdUrgency());

        urgencyDao.save(urgencyToUpdate);
    }
}
