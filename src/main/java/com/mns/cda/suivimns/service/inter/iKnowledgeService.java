package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Knowledge;

import java.util.List;
import java.util.Optional;

public interface iKnowledgeService {
    List<Knowledge> findAll();

    Optional<Knowledge> findById(int id);

    Knowledge save(Knowledge knowledge);

    void delete(Knowledge knowledge);

    Knowledge update(Knowledge knowledgeToUpdate, int id) throws KnowledgeNotFoundException;

    class KnowledgeNotFoundException extends Exception {
    }
}
