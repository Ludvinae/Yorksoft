package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.DirectorDao;
import com.mns.cda.suivimns.model.Director;
import com.mns.cda.suivimns.service.inter.iDirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService implements iDirectorService {

    protected final DirectorDao directorDao;

    @Override
    public List<Director> findAll() {
        return directorDao.findAll();
    }

    @Override
    public Optional<Director> findById(int id) {
        return directorDao.findById(id);
    }

    @Override
    public Director save(Director director) {
        director.setIdAppUser(null);
        return directorDao.save(director);
    }

    @Override
    public void delete(Director director) {
        directorDao.delete(director);
    }

    @Override
    public Director update(Director directorToUpdate, int id) throws iDirectorService.DirectorNotFoundException {
        Optional<Director> director = directorDao.findById(id);

        if (director.isEmpty()) {
            throw new iDirectorService.DirectorNotFoundException();
        }

        directorToUpdate.setIdAppUser(director.get().getIdAppUser());

        return directorDao.save(directorToUpdate);
    }
}
