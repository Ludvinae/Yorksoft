package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.service.inter.iCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Comment save(Comment comment) {
        comment.setIdComment(null);
        comment.setDateSent(null);
        comment.setLastModification(null);
        return commentDao.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentDao.delete(comment);
    }

    @Override
    public Comment update(Comment commentToUpdate, int id) throws iCommentService.CommentNotFoundException {
        Comment currentComment = commentDao.findById(id)
                .orElseThrow(iCommentService.CommentNotFoundException::new);

        // Modification des champs qui sont authorisés à changer
        currentComment.setContent(commentToUpdate.getContent());

        return commentDao.save(currentComment);
    }
}
