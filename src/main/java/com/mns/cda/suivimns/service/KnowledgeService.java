package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.model.Knowledge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KnowledgeService  {

    public static class KnowledgeNotFoundException extends Exception {
    }

    protected final KnowledgeDao knowledgeDao;

    public List<Knowledge> findAll() {
        return knowledgeDao.findAll();
    }

    public Optional<Knowledge> findById(int id) {
        return knowledgeDao.findById(id);
    }

    public Knowledge save(Knowledge knowledge) {
        knowledge.setIdKnowledge(null);
        return knowledgeDao.save(knowledge);
    }

    public void delete(Knowledge knowledge) {
        knowledgeDao.delete(knowledge);
    }

    public Knowledge update(Knowledge knowledgeToUpdate, int id) throws KnowledgeNotFoundException {
        Knowledge currentKnowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeNotFoundException::new);

        currentKnowledge.setSubject(knowledgeToUpdate.getSubject());

        currentKnowledge.setVersionList(knowledgeToUpdate.getVersionList());
        currentKnowledge.setTheme(knowledgeToUpdate.getTheme());

        return knowledgeDao.save(currentKnowledge);
    }
}
