package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.model.Classification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassificationService {

    // NEED REWORKING TO ACCOUNT FOR ID FOR BOTH LINKED TABLES

    public static class ClassificationNotFoundException extends Exception {}

    protected final ClassificationDao classificationDao;

    public List<Classification> findAll() {
        return classificationDao.findAll();
    }

    public Optional<Classification> findById(int id) {
        return classificationDao.findById(id);
    }

    public void save(Classification classification) {

        classificationDao.save(classification);
    }

    public void delete(Classification classification) {
        classificationDao.delete(classification);
    }


    public void update(Classification classificationToUpdate, int id) throws ClassificationService.ClassificationNotFoundException {
        Optional<Classification> classification = classificationDao.findById(id);

        if (classification.isEmpty()) {
            throw new ClassificationService.ClassificationNotFoundException();
        }

        classificationDao.save(classificationToUpdate);
    }
}
