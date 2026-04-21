package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.UrgencyDao;
import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.service.inter.iUrgencyService;
import com.mns.cda.suivimns.service.inter.iVersionTypeService;
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
    public Urgency save(Urgency urgency) {
        urgency.setIdUrgency(null);
        return urgencyDao.save(urgency);
    }

    @Override
    public void delete(Urgency urgency) {
        urgencyDao.delete(urgency);
    }

    @Override
    public Urgency update(Urgency urgencyToUpdate, int id) throws iUrgencyService.UrgencyNotFoundException {
        Urgency currentUrgency = urgencyDao.findById(id)
                .orElseThrow(iUrgencyService.UrgencyNotFoundException::new);

        currentUrgency.setDesignation(urgencyToUpdate.getDesignation());
        currentUrgency.setDescription(urgencyToUpdate.getDescription());
        currentUrgency.setPriorityFactor(urgencyToUpdate.getPriorityFactor());

        return urgencyDao.save(currentUrgency);
    }
}
