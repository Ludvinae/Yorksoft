package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.model.Knowledge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KnowledgeService {

    public static class KnowledgeNotFoundException extends Exception {}

    protected final KnowledgeDao knowledgeDao;

    public List<Knowledge> findAll() {
        return knowledgeDao.findAll();
    }

    public Optional<Knowledge> findById(int id) {
        return knowledgeDao.findById(id);
    }

    public void save(Knowledge knowledge) {
        knowledge.setIdKnowledge(null);
        knowledgeDao.save(knowledge);
    }

    public void delete(Knowledge knowledge) {
        knowledgeDao.delete(knowledge);
    }

    public void update(Knowledge knowledgeToUpdate, int id) throws KnowledgeService.KnowledgeNotFoundException {
        Optional<Knowledge> knowledge = knowledgeDao.findById(id);

        if (knowledge.isEmpty()) {
            throw new KnowledgeService.KnowledgeNotFoundException();
        }

        knowledgeToUpdate.setIdKnowledge(knowledge.get().getIdKnowledge());

        knowledgeDao.save(knowledgeToUpdate);
    }
}
