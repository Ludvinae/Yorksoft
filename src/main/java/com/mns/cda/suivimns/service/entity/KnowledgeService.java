package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.dto.entity.KnowledgeDto;
import com.mns.cda.suivimns.exception.KnowledgeNotFoundException;
import com.mns.cda.suivimns.mapper.entity.KnowledgeMapper;
import com.mns.cda.suivimns.model.Knowledge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeService  {

    protected final KnowledgeDao knowledgeDao;
    protected final KnowledgeMapper knowledgeMapper;

    public List<KnowledgeDto> findAll() {
        return knowledgeMapper.toDtoList(knowledgeDao.findAll());
    }

    public KnowledgeDto findById(int id) throws KnowledgeNotFoundException {
        Knowledge knowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeNotFoundException::new);

        return knowledgeMapper.toDto(knowledge);
    }

    public KnowledgeDto save(KnowledgeDto dto) {
        Knowledge knowledge = knowledgeMapper.toEntity(dto);
        knowledge.setIdKnowledge(null);
        Knowledge saved = knowledgeDao.save(knowledge);

        return knowledgeMapper.toDto(saved);
    }

    public void delete(int id) throws KnowledgeNotFoundException {
        Knowledge knowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeNotFoundException::new);

        knowledgeDao.delete(knowledge);
    }

    public KnowledgeDto update(int id, KnowledgeDto knowledgeToUpdate) throws KnowledgeNotFoundException {

        Knowledge currentKnowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeNotFoundException::new);

        knowledgeMapper.updateEntityFromDto(knowledgeToUpdate, currentKnowledge);

        return knowledgeMapper.toDto(knowledgeDao.save(currentKnowledge));
    }
}
