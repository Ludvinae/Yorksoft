package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.model.Impact;
import com.mns.cda.suivimns.service.inter.iImpactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImpactService implements iImpactService {

    protected final ImpactDao impactDao;

    @Override
    public List<Impact> findAll() {
        return impactDao.findAll();
    }

    @Override
    public Optional<Impact> findById(int id) {
        return impactDao.findById(id);
    }

    @Override
    public Impact save(Impact impact) {
        impact.setIdImpact(null);
        return impactDao.save(impact);
    }

    @Override
    public void delete(Impact impact) {
        impactDao.delete(impact);
    }

    @Override
    public void update(Impact impactToUpdate, int id) throws iImpactService.ImpactNotFoundException {
        Optional<Impact> impact = impactDao.findById(id);

        if (impact.isEmpty()) {
            throw new iImpactService.ImpactNotFoundException();
        }

        impactToUpdate.setIdImpact(impact.get().getIdImpact());

        impactDao.save(impactToUpdate);
    }
}
