package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.entity.ClassificationDto;
import com.mns.cda.suivimns.exception.ThemeNotFoundException;
import com.mns.cda.suivimns.exception.TicketNotFoundException;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ClassificationMapper {

    @Autowired
    protected TicketDao ticketDao;

    @Autowired
    protected ThemeDao themeDao;

    // -------- ENTITY → DTO --------

    @Mapping(target = "idTicket", source = "ticket")
    @Mapping(target = "idTheme", source = "theme")
    public abstract ClassificationDto toDto(Classification classification);

    public abstract List<ClassificationDto> toDtoList(List<Classification> classifications);

    // -------- DTO → ENTITY --------

    @Mapping(target = "ticket", source = "idTicket")
    @Mapping(target = "theme", source = "idTheme")
    public abstract Classification toEntity(ClassificationDto dto);

    // -------- HELPERS --------

    protected Integer mapTicketToId(Ticket ticket) {
        return ticket != null ? ticket.getIdTicket() : null;
    }

    protected Integer mapThemeToId(Theme theme) {
        return theme != null ? theme.getIdTheme() : null;
    }

    protected Ticket mapIdToTicket(Integer id) {
        return ticketDao.findById(id).orElseThrow(TicketNotFoundException::new);
    }

    protected Theme mapIdToTheme(Integer id) {
        return themeDao.findById(id).orElseThrow(ThemeNotFoundException::new);
    }
}