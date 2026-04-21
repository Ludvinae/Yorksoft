package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    protected final iCommentService iCommentService;

    @GetMapping("/list")
    public List<Comment> getAll() {
        return iCommentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(@PathVariable int id) {

        Optional<Comment> comment = iCommentService.findById(id);
        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> create(@RequestBody @Validated(OnCreate.class) Comment comment) {
        comment.setIdComment(null);
        comment.setLastModification(null);
        iCommentService.save(comment);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Comment> comment = iCommentService.findById(id);
        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iCommentService.delete(comment.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Comment commentToUpdate) {
        Optional<Comment> comment = iCommentService.findById(id);

        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commentToUpdate.setIdComment(comment.get().getIdComment());
        commentToUpdate.setDateSent(comment.get().getDateSent());

        iCommentService.save(commentToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
