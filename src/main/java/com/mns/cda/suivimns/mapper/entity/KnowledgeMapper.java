package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dao.ProcedureDao;
import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.dao.VersionDao;
import com.mns.cda.suivimns.dto.entity.KnowledgeDto;
import com.mns.cda.suivimns.model.Procedure;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Version;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class KnowledgeMapper {
    
    @Autowired
    protected ThemeDao themeDao;

    @Autowired
    protected VersionDao versionDao;

    @Autowired
    protected ProcedureDao procedureDao;


    @Mapping(target = "idTheme", source = "theme")
    @Mapping(target = "versionIds", source = "versionList")
    @Mapping(target = "procedureIds", source = "procedureList")
    public abstract KnowledgeDto toDto(Knowledge knowledge);

    //@Mapping(target = "idKnowledgeType", source = "knowledgeType")
    public abstract List<KnowledgeDto> toDtoList(List<Knowledge> knowledge);

    @Mapping(target="theme", source="idTheme")
    @Mapping(target = "versionList", source = "versionIds")
    @Mapping(target = "procedureList", source = "procedureIds")
    public abstract Knowledge toEntity(KnowledgeDto dto);


    // Method helper pour ID vers ENTITE
    protected Theme mapIdToTheme(Integer id) {
        return themeDao.getReferenceById(id);
    }

    protected List<Version> mapIdsToVersions(List<Integer> ids) {
        if (ids == null) return null;

        return ids.stream()
                .map(versionDao::getReferenceById)
                .toList();
    }

    protected List<Procedure> mapIdsToProcedures(List<Integer> ids) {
        if (ids == null) return null;

        return ids.stream()
                .map(procedureDao::getReferenceById)
                .toList();
    }


    // Method helper pour ENTITE vers ID
    protected Integer mapThemeToId(Theme theme) {
        return theme != null ? theme.getIdTheme() : null;
    }

    protected List<Integer> mapVersionsToIds(List<Version> versions) {
        if (versions == null) return null;

        return versions.stream()
                .map(Version::getIdVersion)
                .toList();
    }

    protected List<Integer> mapProceduresToIds(List<Procedure> procedures) {
        if (procedures == null) return null;

        return procedures.stream()
                .map(Procedure::getIdProcedure)
                .toList();
    }

    // Method helper pour Update
    @Mapping(target = "idKnowledge", ignore = true)
    @Mapping(target = "procedureList", ignore = true)
    @Mapping(target = "theme", source = "idTheme")
    @Mapping(target = "versionList", ignore = true)
    public abstract void updateEntityFromDto(KnowledgeDto dto, @MappingTarget Knowledge entity);

    // Merge intelligent pour modifier la liste de versions en update
    @AfterMapping
    protected void updateVersions(KnowledgeDto dto, @MappingTarget Knowledge entity) {
        if (dto.versionIds() == null) return;

        List<Version> current = entity.getVersionList();

        List<Version> updated = dto.versionIds().stream()
                .map(versionDao::getReferenceById)
                .toList();

        current.clear();
        current.addAll(updated);
    }
}
