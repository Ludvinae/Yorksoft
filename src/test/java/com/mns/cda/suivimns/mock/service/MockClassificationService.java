package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.service.inter.iClassificationService;

import java.util.List;
import java.util.Optional;

public class MockClassificationService implements iClassificationService {
    @Override
    public List<Classification> findAll() {
        return List.of();
    }

    @Override
    public Optional<Classification> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Classification classification) {

    }

    @Override
    public void delete(Classification classification) {

    }

    @Override
    public void update(Classification classificationToUpdate, int id) throws ClassificationNotFoundException {

    }

    @Override
    public Theme getTheme(Integer ticketId) {
        return null;
    }
}
