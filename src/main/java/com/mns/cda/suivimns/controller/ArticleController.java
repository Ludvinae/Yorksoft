package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/article")
@CrossOrigin
@RequiredArgsConstructor
public class ArticleController {

    protected final iArticleService iArticleService;

    @GetMapping("/list")
    public List<Article> getAll() {
        return iArticleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getById(@PathVariable int id) {
        Optional<Article> article = iArticleService.findById(id);

        if (article.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(article.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Article> create(@RequestBody @Validated(OnCreate.class) Article article) {

        iArticleService.save(article);

        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Article> delete(@PathVariable int id) {
        Optional<Article> article = iArticleService.findById(id);

        if (article.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iArticleService.delete(article.get());
        return new ResponseEntity<>(article.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Article articleToUpdate) throws iArticleService.ArticleNotFoundException {

        try {
            iArticleService.update(articleToUpdate, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (iArticleService.ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
