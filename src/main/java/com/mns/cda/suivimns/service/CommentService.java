package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.CommentDao;
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


    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    public Optional<Comment> findById(int id) {
        return commentDao.findById(id);
    }

    public Comment save(Comment comment) {
        comment.setIdComment(null);
        comment.setDateSent(null);
        comment.setLastModification(null);
        return commentDao.save(comment);
    }


    public void delete(Comment comment) {
        commentDao.delete(comment);
    }


    public Comment update(Comment commentToUpdate, int id) throws CommentNotFoundException {
        Comment currentComment = commentDao.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        // Modification des champs qui sont authorisés à changer
        currentComment.setContent(commentToUpdate.getContent());

        return commentDao.save(currentComment);
    }
}
