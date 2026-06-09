package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.dao.search.KnowledgeSpecification;
import com.mns.cda.suivimns.dto.entity.KnowledgeDto;
import com.mns.cda.suivimns.dto.search.KnowledgeSelect;
import com.mns.cda.suivimns.dto.search.KnowledgeSearch;
import com.mns.cda.suivimns.exception.KnowledgeNotFoundException;
import com.mns.cda.suivimns.mapper.entity.KnowledgeMapper;
import com.mns.cda.suivimns.model.Knowledge;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public KnowledgeDto findById(int id) {
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

    public void delete(int id) {
        Knowledge knowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeNotFoundException::new);

        knowledgeDao.delete(knowledge);
    }

    public KnowledgeDto update(int id, KnowledgeDto knowledgeToUpdate) {

        Knowledge currentKnowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeNotFoundException::new);

        knowledgeMapper.updateEntityFromDto(knowledgeToUpdate, currentKnowledge);

        return knowledgeMapper.toDto(knowledgeDao.save(currentKnowledge));
    }

    public List<KnowledgeSelect> search(KnowledgeSearch request) {

        Specification<Knowledge> spec =
                Specification.allOf(
                        KnowledgeSpecification.search(request.search()),
                        KnowledgeSpecification.theme(request.idTheme()),
                        KnowledgeSpecification.version(request.idVersion())
                );

        List<Knowledge> knowledgeList =
                knowledgeDao.findAll(
                        spec,
                        Sort.by(
                                Sort.Order.asc("theme.designation"),
                                Sort.Order.asc("subject")
                        )
                );

        return knowledgeMapper.toKnowledgeSelectList(knowledgeList);
    }
}
