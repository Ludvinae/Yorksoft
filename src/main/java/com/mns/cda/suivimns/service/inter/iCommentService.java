package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Comment;

import java.util.List;
import java.util.Optional;

public interface iCommentService {
    List<Comment> findAll();

    Optional<Comment> findById(int id);

    Comment save(Comment comment);

    void delete(Comment comment);

    Comment update(Comment commentToUpdate, int id) throws CommentNotFoundException;

    class CommentNotFoundException extends Exception {
    }
}
