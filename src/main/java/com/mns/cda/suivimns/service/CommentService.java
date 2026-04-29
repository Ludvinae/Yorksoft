package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.dto.CommentDto;
import com.mns.cda.suivimns.mapper.CommentMapper;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService  {


    public static class CommentNotFoundException extends Exception {
    }

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

    public void delete(int id) throws CommentService.CommentNotFoundException {
        Comment comment = commentDao.findById(id)
                .orElseThrow(CommentService.CommentNotFoundException::new);

        commentDao.delete(comment);
    }

    public CommentDto update(int id, CommentDto commentToUpdate) throws CommentService.CommentNotFoundException {

        Comment currentComment = commentDao.findById(id)
                .orElseThrow(CommentService.CommentNotFoundException::new);

        commentMapper.updateEntityFromDto(commentToUpdate, currentComment);

        return commentMapper.toDto(commentDao.save(currentComment));
    }
}
