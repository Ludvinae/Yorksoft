package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.details.TicketDetailComment;
import com.mns.cda.suivimns.dto.entity.CommentDto;
import com.mns.cda.suivimns.dto.flat.CommentEditDto;
import com.mns.cda.suivimns.dto.flat.PostCommentDto;
import com.mns.cda.suivimns.exception.AppUserNotFoundException;
import com.mns.cda.suivimns.exception.CommentNotFoundException;
import com.mns.cda.suivimns.exception.CommentNotOwnedException;
import com.mns.cda.suivimns.exception.TicketNotFoundException;
import com.mns.cda.suivimns.mapper.entity.CommentMapper;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService  {


    protected final CommentDao commentDao;
    protected final CommentMapper commentMapper;

    protected final TicketDao ticketDao;
    protected final AppUserDao appUserDao;

    protected final ActivityService activityService;

    public List<CommentDto> findAll() {
        return commentMapper.toDtoList(commentDao.findAll());
    }

    public CommentDto findById(int id) throws CommentNotFoundException {
        Comment comment = commentDao.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        return commentMapper.toDto(comment);
    }

    public TicketDetailComment save(PostCommentDto dto, AppUserDetails appUser) {
        Ticket ticket = ticketDao.findById(dto.idTicket()).orElseThrow(TicketNotFoundException::new);

        AppUser author = appUserDao.findById(appUser.getId()).orElseThrow(AppUserNotFoundException::new);
        String authorName = author.getFirstName() + " " + author.getLastName();

        Comment comment = new Comment(null, dto.content(), null, null, ticket, author);
        Comment saved = commentDao.save(comment);

        activityService.log(author, "A commenté le ticket #" + ticket.getIdTicket());

        return new TicketDetailComment(saved.getIdComment(), saved.getContent(),
                saved.getDateSent(), saved.getLastModification(), authorName, appUser.getUserRole(), appUser.getTechnician().getRank());
    }

    public void delete(int id, AppUserDetails userDetails) {
        Comment comment = commentDao.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        // On verifie si l'utilisateur est admin ou s'il est le proprietaire de la ressource
        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                !comment.getAuthor().getIdAppUser().equals(userDetails.getId())) {
            throw new CommentNotOwnedException();
        }

        AppUser actor = appUserDao.findById(userDetails.getId()).orElseThrow(AppUserNotFoundException::new);
        activityService.log(actor, "A effacer le commentaire #" + id);

        commentDao.delete(comment);
    }

    public TicketDetailComment update(int id, CommentEditDto commentToUpdate, AppUserDetails userDetails) {

        Comment currentComment = commentDao.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        // On verifie si l'utilisateur est admin ou s'il est le proprietaire de la ressource
        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                !currentComment.getAuthor().getIdAppUser().equals(userDetails.getId())) {
            throw new CommentNotOwnedException();
        }

        currentComment.setContent(commentToUpdate.content());

        Comment commentSaved = commentDao.save(currentComment);

        AppUser editor = appUserDao.findById(userDetails.getId()).orElseThrow(AppUserNotFoundException::new);
        activityService.log(editor, "A édité le commentaire #" + id);

        return ticketDao.ticketDetailComments(commentSaved.getTicket().getIdTicket()).get(id);

    }
}
