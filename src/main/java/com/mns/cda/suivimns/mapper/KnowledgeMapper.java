package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.dao.VersionDao;
import com.mns.cda.suivimns.dto.KnowledgeDto;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Version;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class KnowledgeMapper {
    
    @Autowired
    protected ThemeDao themeDao;

    @Autowired
    protected VersionDao versionDao;


    @Mapping(target = "idTheme", source = "theme")
    @Mapping(target = "versionIds", source = "versionList")
    public abstract KnowledgeDto toDto(Knowledge knowledge);

    //@Mapping(target = "idKnowledgeType", source = "knowledgeType")
    public abstract List<KnowledgeDto> toDtoList(List<Knowledge> knowledge);

    @Mapping(target="theme", source="idTheme")
    @Mapping(target = "versionList", source = "versionIds")
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

}
