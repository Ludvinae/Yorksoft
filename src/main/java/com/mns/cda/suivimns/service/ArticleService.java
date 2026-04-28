package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ArticleDao;
import com.mns.cda.suivimns.model.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    public static class ArticleNotFoundException extends Exception {
    }

    protected final ArticleDao articleDao;

    public List<Article> findAll() {
        return articleDao.findAll();
    }

    public Optional<Article> findById(int id) {
        return articleDao.findById(id);
    }

    public Article save(Article article) {
        article.setIdArticle(null);
        article.setModificationDate(null);
        article.setCreationDate(LocalDateTime.now());

        return articleDao.save(article);
    }

    public void delete(Article article) {
        articleDao.delete(article);
    }

    public Article update(Article articleToUpdate, int id) throws ArticleNotFoundException {
        Article currentArticle = articleDao.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        currentArticle.setContent(articleToUpdate.getContent());

        return articleDao.save(currentArticle);
    }
}
