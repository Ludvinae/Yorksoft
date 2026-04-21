package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.service.inter.iClassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassificationService implements iClassificationService {

    // NEED REWORKING TO ACCOUNT FOR ID FOR BOTH LINKED TABLES

    protected final ClassificationDao classificationDao;

    @Override
    public List<Classification> findAll() {
        return classificationDao.findAll();
    }

    @Override
    public Optional<Classification> findById(int id) {
        return classificationDao.findById(id);
    }

    @Override
    public Classification save(Classification classification) {

        return classificationDao.save(classification);
    }

    @Override
    public void delete(Classification classification) {
        classificationDao.delete(classification);
    }




    @Override
    public Theme getTheme(Integer ticketId) {
        Optional<Classification> classification = classificationDao.findLatestByTicket(ticketId);
        if (classification.isEmpty()) {
            return null;
        }
        return classification.get().getTheme();
    }
}
