package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.DirectorDao;
import com.mns.cda.suivimns.model.Director;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService  {


    public static class DirectorNotFoundException extends Exception {
    }

    protected final DirectorDao directorDao;

    public List<Director> findAll() {
        return directorDao.findAll();
    }

    public Optional<Director> findById(int id) {
        return directorDao.findById(id);
    }

    public Director save(Director director) {
        director.setIdAppUser(null);
        return directorDao.save(director);
    }


    public void delete(Director director) {
        directorDao.delete(director);
    }


    public Director update(Director directorToUpdate, int id) throws DirectorNotFoundException {
        Optional<Director> director = directorDao.findById(id);

        if (director.isEmpty()) {
            throw new DirectorNotFoundException();
        }

        directorToUpdate.setIdAppUser(director.get().getIdAppUser());

        return directorDao.save(directorToUpdate);
    }
}
