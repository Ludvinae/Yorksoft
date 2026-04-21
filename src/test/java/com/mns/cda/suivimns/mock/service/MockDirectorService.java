package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Director;
import com.mns.cda.suivimns.service.inter.iDirectorService;

import java.util.List;
import java.util.Optional;

public class MockDirectorService implements iDirectorService {
    @Override
    public List<Director> findAll() {
        return List.of();
    }

    @Override
    public Optional<Director> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Director save(Director director) {
        return null;
    }

    @Override
    public void delete(Director director) {

    }

    @Override
    public Director update(Director directorToUpdate, int id) throws DirectorNotFoundException {
        return null;
    }
}
