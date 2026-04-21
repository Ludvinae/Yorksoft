package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Impact;

import java.util.List;
import java.util.Optional;

public interface iImpactService {
    List<Impact> findAll();

    Optional<Impact> findById(int id);

    Impact save(Impact impact);

    void delete(Impact impact);

    void update(Impact impactToUpdate, int id) throws ImpactNotFoundException;

    class ImpactNotFoundException extends Exception {
    }
}
