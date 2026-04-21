package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.service.inter.iKnowledgeService;

import java.util.List;
import java.util.Optional;

public class MockKnowledgeService implements iKnowledgeService {
    @Override
    public List<Knowledge> findAll() {
        return List.of();
    }

    @Override
    public Optional<Knowledge> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Knowledge save(Knowledge knowledge) {
        return null;
    }

    @Override
    public void delete(Knowledge knowledge) {

    }

    @Override
    public Knowledge update(Knowledge knowledgeToUpdate, int id) throws KnowledgeNotFoundException {
        return null;
    }
}
