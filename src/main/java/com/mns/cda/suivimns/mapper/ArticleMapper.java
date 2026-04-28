package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.dto.ArticleDto;
import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.Technician;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ArticleMapper {

    @Autowired
    protected KnowledgeDao knowledgeDao;

    @Autowired
    protected TechnicianDao technicianDao;

    @Mapping(target = "idKnowledge", source = "knowledge")
    @Mapping(target = "idAuthor", source = "technician")
    public abstract ArticleDto toDto(Article article);

    //@Mapping(target = "idArticleType", source = "articleType")
    public abstract List<ArticleDto> toDtoList(List<Article> article);

    @Mapping(target="knowledge", source="idKnowledge")
    @Mapping(target="technician", source="idAuthor")
    public abstract Article toEntity(ArticleDto dto);


    // Method helper pour ID vers ENTITE
    protected Knowledge mapIdToKnowledge(Integer id) {
        return knowledgeDao.getReferenceById(id);
    }

    protected Technician mapIdToTechnician(Integer id) {
        return technicianDao.getReferenceById(id);
    }

    // Method helper pour ENTITE vers ID
    protected Integer mapKnowledgeToId(Knowledge knowledge) {
        return knowledge != null ? knowledge.getIdKnowledge() : null;
    }

    protected Integer mapTechnicianToId(Technician author) {
        return author != null ? author.getIdAppUser() : null;
    }
}
