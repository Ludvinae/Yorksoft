package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.model.Impact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImpactService  {

    public static class ImpactNotFoundException extends Exception {
    }

    protected final ImpactDao impactDao;

    public List<Impact> findAll() {
        return impactDao.findAll();
    }

    public Optional<Impact> findById(int id) {
        return impactDao.findById(id);
    }

    public Impact save(Impact impact) {
        impact.setIdImpact(null);
        return impactDao.save(impact);
    }

    public void delete(Impact impact) {
        impactDao.delete(impact);
    }

    public Impact update(Impact impactToUpdate, int id) throws ImpactNotFoundException {
        Impact currentImpact = impactDao.findById(id)
                .orElseThrow(ImpactNotFoundException::new);

        currentImpact.setDesignation(impactToUpdate.getDesignation());
        currentImpact.setPriorityFactor(impactToUpdate.getPriorityFactor());
        currentImpact.setDescription(impactToUpdate.getDescription());

        return impactDao.save(currentImpact);
    }
}
