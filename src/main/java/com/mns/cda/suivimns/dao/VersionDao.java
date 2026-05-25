package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.entity.VersionDto;
import com.mns.cda.suivimns.dto.flat.VersionDetailDto;
import com.mns.cda.suivimns.dto.flat.VersionSelectDto;
import com.mns.cda.suivimns.model.Version;
import com.mns.cda.suivimns.service.entity.VersionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionDao extends JpaRepository<Version, Integer> {

    @Query("""
        SELECT new com.mns.cda.suivimns.dto.flat.VersionSelectDto(
            v.idVersion, CONCAT(v.versionNumber, ' ', t.designation))
        FROM Version v
        JOIN v.software s
        JOIN v.versionType t
        WHERE s.idSoftware = :idSoftware
    """)
    List<VersionSelectDto> findAllBySoftware(@Param("idSoftware") Integer idSoftware);

    @Query(""" 
        SELECT new com.mns.cda.suivimns.dto.flat.VersionDetailDto(
            v.idVersion, v.versionNumber, t.designation,
            s.name, v.publicationDate)
        FROM Version v
        JOIN v.versionType t
        JOIN v.software s
    """)
    List<VersionDetailDto> findAllDetail();

    @Query(""" 
        SELECT new com.mns.cda.suivimns.dto.flat.VersionDetailDto(
            v.idVersion, v.versionNumber, t.designation,
            s.name, v.publicationDate)
        FROM Version v
        JOIN v.versionType t
        JOIN v.software s
        WHERE v.idVersion = :id
    """)
    VersionDetailDto findByIdDetail(Integer id) throws VersionService.VersionNotFoundException;
}
