package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.dto.entity.CommentDto;
import com.mns.cda.suivimns.mapper.entity.CommentMapper;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService  {


    public static class CommentNotFoundException extends RuntimeException {
    }

    public static class CommentNotOwnedException extends RuntimeException {}

    protected final CommentDao commentDao;
    protected final CommentMapper commentMapper;

    public List<CommentDto> findAll() {
        return commentMapper.toDtoList(commentDao.findAll());
    }

    public CommentDto findById(int id) throws CommentService.CommentNotFoundException {
        Comment comment = commentDao.findById(id)
                .orElseThrow(CommentService.CommentNotFoundException::new);

        return commentMapper.toDto(comment);
    }

    public CommentDto save(CommentDto dto) {
        Comment comment = commentMapper.toEntity(dto);
        comment.setIdComment(null);
        Comment saved = commentDao.save(comment);

        return commentMapper.toDto(saved);
    }

    public void delete(int id, AppUserDetails userDetails) throws CommentService.CommentNotFoundException {
        Comment comment = commentDao.findById(id)
                .orElseThrow(CommentService.CommentNotFoundException::new);

        // On verifie si l'utilisateur est admin ou s'il est le proprietaire de la ressource
        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                !comment.getAuthor().getIdAppUser().equals(userDetails.getId())) {
            throw new CommentNotOwnedException();
        }

        commentDao.delete(comment);
    }

    public CommentDto update(int id, CommentDto commentToUpdate, AppUserDetails userDetails) throws CommentService.CommentNotFoundException {

        Comment currentComment = commentDao.findById(id)
                .orElseThrow(CommentService.CommentNotFoundException::new);

        // On verifie si l'utilisateur est admin ou s'il est le proprietaire de la ressource
        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                !currentComment.getAuthor().getIdAppUser().equals(userDetails.getId())) {
            throw new CommentNotOwnedException();
        }

        commentMapper.updateEntityFromDto(commentToUpdate, currentComment);

        return commentMapper.toDto(commentDao.save(currentComment));
    }
}
