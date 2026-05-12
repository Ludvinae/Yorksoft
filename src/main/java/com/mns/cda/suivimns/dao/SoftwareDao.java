package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.flat.SoftwareDetailDto;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.service.entity.SoftwareService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoftwareDao extends JpaRepository<Software, Integer> {

    @Query("""
        SELECT new com.mns.cda.suivimns.dto.flat.SoftwareDetailDto(
            s.idSoftware, s.name, s.description, t.designation)
        FROM Software s
        JOIN s.type t
    """)
    List<SoftwareDetailDto> findAllDetail();

    @Query("""
        SELECT new com.mns.cda.suivimns.dto.flat.SoftwareDetailDto(
            s.idSoftware, s.name, s.description, t.designation)
        FROM Software s
        JOIN s.type t
        WHERE s.idSoftware = :id
    """)
    SoftwareDetailDto findByIdDetail(int id) throws SoftwareService.SoftwareNotFoundException;
}
