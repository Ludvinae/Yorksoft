package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.service.inter.iKnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KnowledgeService implements iKnowledgeService {

    protected final KnowledgeDao knowledgeDao;

    @Override
    public List<Knowledge> findAll() {
        return knowledgeDao.findAll();
    }

    @Override
    public Optional<Knowledge> findById(int id) {
        return knowledgeDao.findById(id);
    }

    @Override
    public Knowledge save(Knowledge knowledge) {
        knowledge.setIdKnowledge(null);
        return knowledgeDao.save(knowledge);
    }

    @Override
    public void delete(Knowledge knowledge) {
        knowledgeDao.delete(knowledge);
    }

    @Override
    public void update(Knowledge knowledgeToUpdate, int id) throws iKnowledgeService.KnowledgeNotFoundException {
        Optional<Knowledge> knowledge = knowledgeDao.findById(id);

        if (knowledge.isEmpty()) {
            throw new iKnowledgeService.KnowledgeNotFoundException();
        }

        knowledgeToUpdate.setIdKnowledge(knowledge.get().getIdKnowledge());

        knowledgeDao.save(knowledgeToUpdate);
    }
}
