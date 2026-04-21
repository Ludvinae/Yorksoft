package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Impact;
import com.mns.cda.suivimns.service.inter.iImpactService;

import java.util.List;
import java.util.Optional;

public class MockImpactService implements iImpactService {
    @Override
    public List<Impact> findAll() {
        return List.of();
    }

    @Override
    public Optional<Impact> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Impact save(Impact impact) {
        return null;
    }

    @Override
    public void delete(Impact impact) {

    }

    @Override
    public Impact update(Impact impactToUpdate, int id) throws ImpactNotFoundException {
        return null;
    }
}
