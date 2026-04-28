package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dao.ManagerDao;
import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.CommentDto;
import com.mns.cda.suivimns.dto.CommentDto;
import com.mns.cda.suivimns.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
   
    @Autowired
    protected TicketDao ticketDao;

    @Autowired
    protected AppUserDao appUserDao;

    @Mapping(target = "idTicket", source = "ticket")
    @Mapping(target = "idAuthor", source = "author")
    public abstract CommentDto toDto(Comment comment);

    //@Mapping(target = "idCommentType", source = "commentType")
    public abstract List<CommentDto> toDtoList(List<Comment> comment);

    @Mapping(target="ticket", source="idTicket")
    @Mapping(target="author", source="idAuthor")
    public abstract Comment toEntity(CommentDto dto);


    // Method helper pour ID vers ENTITE
    protected Ticket mapIdToTicket(Integer id) {
        return ticketDao.getReferenceById(id);
    }

    protected AppUser mapIdToAppUser(Integer id) {
        return appUserDao.getReferenceById(id);
    }

    // Method helper pour ENTITE vers ID
    protected Integer mapTicketToId(Ticket ticket) {
        return ticket != null ? ticket.getIdTicket() : null;
    }

    protected Integer mapAppUserToId(AppUser appUser) {
        return appUser != null ? appUser.getIdAppUser() : null;
    }
}
