package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Director;

import java.util.List;
import java.util.Optional;

public interface iDirectorService {
    List<Director> findAll();

    Optional<Director> findById(int id);

    Director save(Director director);

    void delete(Director director);

    Director update(Director directorToUpdate, int id) throws DirectorNotFoundException;

    class DirectorNotFoundException extends Exception {
    }
}
