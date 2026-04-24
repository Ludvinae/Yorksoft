package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.flat.ClientDto;
import com.mns.cda.suivimns.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientDao extends JpaRepository<Client, Integer> {

    Optional<Client> findByEmail(String email);

    @Query("SELECT new com.mns.cda.suivimns.dto.flat.ClientDto(" +
            "c.idAppUser, c.firstName, c.lastName, c.email, " +
            "c.phoneNumber, c.importance) " +
            "FROM Client c " +
            "WHERE c.idAppUser = :id ")
    Optional<ClientDto> getClient(@Param("id") int id);

    @Query("SELECT new com.mns.cda.suivimns.dto.flat.ClientDto(" +
                  "c.idAppUser, c.firstName, c.lastName, c.email, " +
                  "c.phoneNumber, c.importance) " +
                  "FROM Client c ")
    List<ClientDto> getAllClient();
}
