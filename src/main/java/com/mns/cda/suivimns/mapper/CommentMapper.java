package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.CommentDto;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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

    // Method helper pour Update
    @Mapping(target = "idComment", ignore = true)
    @Mapping(target = "ticket", source = "idTicket")
    @Mapping(target = "author", source = "idAuthor")
    public abstract void updateEntityFromDto(CommentDto dto, @MappingTarget Comment entity);
}
