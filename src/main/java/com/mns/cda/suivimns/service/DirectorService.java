package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.DirectorDao;
import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dao.DirectorDao;
import com.mns.cda.suivimns.model.Director;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {

    public static class DirectorNotFoundException extends Exception {}

    protected final DirectorDao directorDao;

    public List<Director> findAll() {
        return directorDao.findAll();
    }

    public Optional<Director> findById(int id) {
        return directorDao.findById(id);
    }

    public void save(Director director) {
        director.setIdAppUser(null);
        directorDao.save(director);
    }

    public void delete(Director director) {
        directorDao.delete(director);
    }

    public void update(Director directorToUpdate, int id) throws DirectorService.DirectorNotFoundException {
        Optional<Director> director = directorDao.findById(id);

        if (director.isEmpty()) {
            throw new DirectorService.DirectorNotFoundException();
        }

        directorToUpdate.setIdAppUser(director.get().getIdAppUser());

        directorDao.save(directorToUpdate);
    }
}
