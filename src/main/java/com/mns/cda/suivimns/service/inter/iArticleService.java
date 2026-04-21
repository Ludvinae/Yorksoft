package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Article;

import java.util.List;
import java.util.Optional;

public interface iArticleService {
    List<Article> findAll();

    Optional<Article> findById(int id);

    Article save(Article article);

    void delete(Article article);

    Article update(Article articleToUpdate, int id) throws ArticleNotFoundException;

    class ArticleNotFoundException extends Exception {
    }
}
