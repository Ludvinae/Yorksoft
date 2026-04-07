package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.model.Impact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImpactService {

    public static class ImpactNotFoundException extends Exception {}

    protected final ImpactDao impactDao;

    public List<Impact> findAll() {
        return impactDao.findAll();
    }

    public Optional<Impact> findById(int id) {
        return impactDao.findById(id);
    }

    public void save(Impact impact) {
        impact.setIdImpact(null);
        impactDao.save(impact);
    }

    public void delete(Impact impact) {
        impactDao.delete(impact);
    }

    public void update(Impact impactToUpdate, int id) throws ImpactService.ImpactNotFoundException {
        Optional<Impact> impact = impactDao.findById(id);

        if (impact.isEmpty()) {
            throw new ImpactService.ImpactNotFoundException();
        }

        impactToUpdate.setIdImpact(impact.get().getIdImpact());

        impactDao.save(impactToUpdate);
    }
}
