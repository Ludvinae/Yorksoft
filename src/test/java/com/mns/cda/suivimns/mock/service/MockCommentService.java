package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.service.inter.iCommentService;

import java.util.List;
import java.util.Optional;

public class MockCommentService implements iCommentService {
    @Override
    public List<Comment> findAll() {
        return List.of();
    }

    @Override
    public Optional<Comment> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public void delete(Comment comment) {

    }

    @Override
    public Comment update(Comment commentToUpdate, int id) throws CommentNotFoundException {
        return null;
    }
}
