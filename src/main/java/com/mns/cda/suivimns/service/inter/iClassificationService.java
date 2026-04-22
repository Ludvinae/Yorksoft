package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.keys.ClassificationKey;

import java.util.List;
import java.util.Optional;

public interface iClassificationService {
    List<Classification> findAll();

    Optional<Classification> findById(ClassificationKey id);

    Classification save(Classification classification);

    void delete(Classification classification);

    Theme getTheme(Integer ticketId);

    class ClassificationNotFoundException extends Exception {
    }
}
