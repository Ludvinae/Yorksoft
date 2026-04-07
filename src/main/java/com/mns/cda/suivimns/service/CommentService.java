package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    public static class CommentNotFoundException extends Exception {}

    protected final CommentDao commentDao;

    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    public Optional<Comment> findById(int id) {
        return commentDao.findById(id);
    }

    public void save(Comment comment) {
        comment.setIdComment(null);
        commentDao.save(comment);
    }

    public void delete(Comment comment) {
        commentDao.delete(comment);
    }

    public void update(Comment commentToUpdate, int id) throws CommentService.CommentNotFoundException {
        Optional<Comment> comment = commentDao.findById(id);

        if (comment.isEmpty()) {
            throw new CommentService.CommentNotFoundException();
        }

        commentToUpdate.setIdComment(comment.get().getIdComment());

        commentDao.save(commentToUpdate);
    }
}
