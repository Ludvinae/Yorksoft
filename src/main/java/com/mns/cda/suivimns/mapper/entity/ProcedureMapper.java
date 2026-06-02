package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.dto.entity.ProcedureDto;
import com.mns.cda.suivimns.model.Procedure;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.Technician;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProcedureMapper {

    @Autowired
    protected KnowledgeDao knowledgeDao;

    @Autowired
    protected TechnicianDao technicianDao;

    @Mapping(target = "idKnowledge", source = "knowledge")
    public abstract ProcedureDto toDto(Procedure procedure);


    public abstract List<ProcedureDto> toDtoList(List<Procedure> procedure);

    @Mapping(target="knowledge", source="idKnowledge")
    public abstract Procedure toEntity(ProcedureDto dto);


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

    // Method helper pour Update
    @Mapping(target = "idProcedure", ignore = true)
    @Mapping(target = "knowledge", source = "idKnowledge")
    public abstract void updateEntityFromDto(ProcedureDto dto, @MappingTarget Procedure entity);
}
