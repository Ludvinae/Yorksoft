package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.service.inter.iCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService implements iCommentService {

    protected final CommentDao commentDao;

    @Override
    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    @Override
    public Optional<Comment> findById(int id) {
        return commentDao.findById(id);
    }

    @Override
    public void save(Comment comment) {
        comment.setIdComment(null);
        commentDao.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentDao.delete(comment);
    }

    @Override
    public void update(Comment commentToUpdate, int id) throws iCommentService.CommentNotFoundException {
        Optional<Comment> comment = commentDao.findById(id);

        if (comment.isEmpty()) {
            throw new iCommentService.CommentNotFoundException();
        }

        commentToUpdate.setIdComment(comment.get().getIdComment());

        commentDao.save(commentToUpdate);
    }
}
