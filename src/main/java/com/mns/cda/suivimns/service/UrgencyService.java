package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.UrgencyDao;
import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.service.inter.iUrgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrgencyService implements iUrgencyService {

    protected final UrgencyDao urgencyDao;

    @Override
    public List<Urgency> findAll() {
        return urgencyDao.findAll();
    }

    @Override
    public Optional<Urgency> findById(int id) {
        return urgencyDao.findById(id);
    }

    @Override
    public void save(Urgency urgency) {
        urgency.setIdUrgency(null);
        urgencyDao.save(urgency);
    }

    @Override
    public void delete(Urgency urgency) {
        urgencyDao.delete(urgency);
    }

    @Override
    public void update(Urgency urgencyToUpdate, int id) throws iUrgencyService.UrgencyNotFoundException {
        Optional<Urgency> urgency = urgencyDao.findById(id);

        if (urgency.isEmpty()) {
            throw new iUrgencyService.UrgencyNotFoundException();
        }

        urgencyToUpdate.setIdUrgency(urgency.get().getIdUrgency());

        urgencyDao.save(urgencyToUpdate);
    }
}
