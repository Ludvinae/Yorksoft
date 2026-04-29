package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.ClassificationDto;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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
    @Mapping(target = "id", ignore = true) // ⚠️ important
    public abstract Classification toEntity(ClassificationDto dto);

    // -------- AFTER MAPPING --------
    @AfterMapping
    protected void setCompositeKey(ClassificationDto dto, @MappingTarget Classification entity) {
        if (dto.idTicket() == null || dto.idTheme() == null) return;

        ClassificationKey key = new ClassificationKey(
                dto.idTicket(),
                dto.idTheme()
        );

        entity.setId(key);
    }

    // -------- HELPERS --------

    protected Integer mapTicketToId(Ticket ticket) {
        return ticket != null ? ticket.getIdTicket() : null;
    }

    protected Integer mapThemeToId(Theme theme) {
        return theme != null ? theme.getIdTheme() : null;
    }

    protected Ticket mapIdToTicket(Integer id) {
        return ticketDao.getReferenceById(id);
    }

    protected Theme mapIdToTheme(Integer id) {
        return themeDao.getReferenceById(id);
    }

}