package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.flat.VersionListDto;
import com.mns.cda.suivimns.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionDao extends JpaRepository<Version, Integer> {

    @Query(""" 
            SELECT new com.mns.cda.suivimns.dto.flat.VersionListDto(
                v.idVersion, v.versionNumber, t.designation,
                s.name, v.publicationDate)
                FROM Version v
                JOIN VersionType t
                JOIN Software s
            """)
    List<VersionListDto> findAllDto();
}
