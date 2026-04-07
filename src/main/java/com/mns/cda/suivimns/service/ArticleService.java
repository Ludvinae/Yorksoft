package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ArticleDao;
import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.model.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    public static class ArticleNotFoundException extends Exception {}

    protected final ArticleDao articleDao;

    public List<Article> findAll() {
        return articleDao.findAll();
    }

    public Optional<Article> findById(int id) {
        return articleDao.findById(id);
    }

    public void save(Article article) {
        article.setIdArticle(null);
        articleDao.save(article);
    }

    public void delete(Article article) {
        articleDao.delete(article);
    }

    public void update(Article articleToUpdate, int id) throws ArticleService.ArticleNotFoundException {
        Optional<Article> article = articleDao.findById(id);

        if (article.isEmpty()) {
            throw new ArticleService.ArticleNotFoundException();
        }

        articleToUpdate.setIdArticle(article.get().getIdArticle());

        articleDao.save(articleToUpdate);
    }
}
