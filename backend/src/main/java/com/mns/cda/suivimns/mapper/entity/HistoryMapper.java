package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.entity.HistoryDto;
import com.mns.cda.suivimns.exception.AppUserNotFoundException;
import com.mns.cda.suivimns.exception.StatusNotFoundException;
import com.mns.cda.suivimns.exception.TicketNotFoundException;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class HistoryMapper {

    @Autowired
    protected TicketDao ticketDao;

    @Autowired
    protected AppUserDao appUserDao;

    @Autowired
    protected StatusDao statusDao;

    @Mapping(target = "idTicket", source = "ticket")
    @Mapping(target = "idActor", source = "actor")
    @Mapping(target = "idStatus", source = "status")
    public abstract HistoryDto toDto(History history);

    //@Mapping(target = "idHistoryType", source = "historyType")
    public abstract List<HistoryDto> toDtoList(List<History> history);

    @Mapping(target="ticket", source="idTicket")
    @Mapping(target="actor", source="idActor")
    @Mapping(target="status", source="idStatus")
    public abstract History toEntity(HistoryDto dto);


    // Method helper pour ID vers ENTITE
    protected Ticket mapIdToTicket(Integer id) {
        return ticketDao.findById(id).orElseThrow(TicketNotFoundException::new);
    }

    protected AppUser mapIdToAppUser(Integer id) {
        return appUserDao.findById(id).orElseThrow(AppUserNotFoundException::new);
    }

    protected Status mapIdToStatus(Integer id) {
        return statusDao.findById(id).orElseThrow(StatusNotFoundException::new);
    }

    // Method helper pour ENTITE vers ID
    protected Integer mapTicketToId(Ticket ticket) {
        return ticket != null ? ticket.getIdTicket() : null;
    }

    protected Integer mapAppUserToId(AppUser appUser) {
        return appUser != null ? appUser.getIdAppUser() : null;
    }

    protected Integer mapStatusToId(Status status) {
        return status != null ? status.getIdStatus() : null;
    }

}
