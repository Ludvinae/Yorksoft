package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.service.inter.iArticleService;

import java.util.List;
import java.util.Optional;

public class MockArticleService implements iArticleService {
    @Override
    public List<Article> findAll() {
        return List.of();
    }

    @Override
    public Optional<Article> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Article save(Article article) {
        return null;
    }

    @Override
    public void delete(Article article) {

    }

    @Override
    public Article update(Article articleToUpdate, int id) throws ArticleNotFoundException {
        return null;
    }
}
