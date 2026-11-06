package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.dao.search.KnowledgeSpecification;
import com.mns.cda.suivimns.dto.details.KnowledgeArticleDto;
import com.mns.cda.suivimns.dto.entity.KnowledgeDto;
import com.mns.cda.suivimns.dto.search.KnowledgeSelect;
import com.mns.cda.suivimns.dto.search.KnowledgeSearch;
import com.mns.cda.suivimns.exception.KnowledgeNotFoundException;
import com.mns.cda.suivimns.exception.TrialAccountRestrictedException;
import com.mns.cda.suivimns.mapper.entity.KnowledgeMapper;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeService  {

    protected final KnowledgeDao knowledgeDao;
    protected final KnowledgeMapper knowledgeMapper;
    protected final AppUserDao appUserDao;

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeService.class);

    public List<KnowledgeDto> findAll() {
        return knowledgeMapper.toDtoList(knowledgeDao.findAll());
    }

    public KnowledgeDto findById(int id) {
        Knowledge knowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeNotFoundException::new);

        return knowledgeMapper.toDto(knowledge);
    }

    public KnowledgeDto save(KnowledgeDto dto, AppUserDetails principal) {
        logger.debug("Saving knowledge: {}, for theme {}", dto.subject(), dto.idTheme());

        Knowledge knowledge = knowledgeMapper.toEntity(dto);
        knowledge.setIdKnowledge(null);
        // The author always comes from the authenticated user, never from the request body
        if (principal != null) {
            knowledge.setAuthor(appUserDao.getReferenceById(principal.getId()));
        }

        Knowledge saved = knowledgeDao.save(knowledge);

        return knowledgeMapper.toDto(saved);
    }

    public void delete(int id) {
        Knowledge knowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeNotFoundException::new);

        knowledgeDao.delete(knowledge);
    }

    public KnowledgeDto update(int id, KnowledgeDto knowledgeToUpdate, AppUserDetails principal) {

        Knowledge currentKnowledge = knowledgeDao.findById(id)
                .orElseThrow(KnowledgeNotFoundException::new);

        // Trial accounts may only edit the articles they wrote, never the shared demo articles
        if (principal.isTrialTechnician() && !isAuthor(currentKnowledge, principal)) {
            throw new TrialAccountRestrictedException();
        }

        knowledgeMapper.updateEntityFromDto(knowledgeToUpdate, currentKnowledge);

        return knowledgeMapper.toDto(knowledgeDao.save(currentKnowledge));
    }

    private static boolean isAuthor(Knowledge knowledge, AppUserDetails principal) {
        return knowledge.getAuthor() != null && knowledge.getAuthor().getIdAppUser() == principal.getId();
    }

    @Transactional(readOnly = true)
    public List<KnowledgeArticleDto> searchArticles(String search, Integer idTheme) {
        Specification<Knowledge> spec = Specification.allOf(
                KnowledgeSpecification.search(search),
                KnowledgeSpecification.theme(idTheme)
        );

        List<Knowledge> knowledgeList = knowledgeDao.findAll(
                spec,
                Sort.by(Sort.Order.asc("theme.designation"), Sort.Order.asc("subject"))
        );

        return knowledgeMapper.toArticleList(knowledgeList);
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
