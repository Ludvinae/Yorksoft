package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.dto.KnowledgeDto;
import com.mns.cda.suivimns.mapper.KnowledgeMapper;
import com.mns.cda.suivimns.model.Knowledge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeService  {

    public static class KnowledgeNotFoundException extends RuntimeException {
    }

    protected final KnowledgeDao knowledgeDao;
    protected final KnowledgeMapper knowledgeMapper;

    public List<KnowledgeDto> findAll() {
        return knowledgeMapper.toDtoList(knowledgeDao.findAll());
    }

    public KnowledgeDto findById(int id) throws KnowledgeService.KnowledgeNotFoundException {
        Knowledge knowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeService.KnowledgeNotFoundException::new);

        return knowledgeMapper.toDto(knowledge);
    }

    public KnowledgeDto save(KnowledgeDto dto) {
        Knowledge knowledge = knowledgeMapper.toEntity(dto);
        knowledge.setIdKnowledge(null);
        Knowledge saved = knowledgeDao.save(knowledge);

        return knowledgeMapper.toDto(saved);
    }

    public void delete(int id) throws KnowledgeService.KnowledgeNotFoundException {
        Knowledge knowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeService.KnowledgeNotFoundException::new);

        knowledgeDao.delete(knowledge);
    }

    public KnowledgeDto update(int id, KnowledgeDto knowledgeToUpdate) throws KnowledgeService.KnowledgeNotFoundException {

        Knowledge currentKnowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeService.KnowledgeNotFoundException::new);

        knowledgeMapper.updateEntityFromDto(knowledgeToUpdate, currentKnowledge);

        return knowledgeMapper.toDto(knowledgeDao.save(currentKnowledge));
    }
}
