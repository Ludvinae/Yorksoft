package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {

    protected CommentDao commentDao;

    @Autowired
    public CommentController(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @GetMapping("/comment/list")
    public List<Comment> getAll() {
        return commentDao.findAll();
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getById(@PathVariable int id) {

        Optional<Comment> comment = commentDao.findById(id);
        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> create(@RequestBody @Validated(OnCreate.class) Comment comment) {
        comment.setIdComment(null);
        comment.setLastModification(null);
        commentDao.save(comment);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Comment> delete(@PathVariable int id) {
        Optional<Comment> comment = commentDao.findById(id);
        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commentDao.delete(comment.get());
        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<Comment> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Comment commentToUpdate) {
        Optional<Comment> comment = commentDao.findById(id);

        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commentToUpdate.setIdComment(comment.get().getIdComment());
        commentToUpdate.setDateSent(comment.get().getDateSent());

        commentDao.save(commentToUpdate);
        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
    }
}
