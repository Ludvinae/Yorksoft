package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.ArticleDao;
import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.AppUserService;
import com.mns.cda.suivimns.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/article")
@CrossOrigin
@RequiredArgsConstructor
public class ArticleController {

    protected final ArticleService articleService;

    @GetMapping("/list")
    public List<Article> getAll() {
        return articleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getById(@PathVariable int id) {
        Optional<Article> article = articleService.findById(id);

        if (article.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(article.get(), HttpStatus.OK);
    }

    @PostMapping("/article")
    public ResponseEntity<Article> create(@RequestBody @Validated(OnCreate.class) Article article) {

        articleService.save(article);

        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Article> delete(@PathVariable int id) {
        Optional<Article> article = articleService.findById(id);

        if (article.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        articleService.delete(article.get());
        return new ResponseEntity<>(article.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<Article> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Article articleToUpdate) throws ArticleService.ArticleNotFoundException {

        try {
            articleService.update(articleToUpdate, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ArticleService.ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
