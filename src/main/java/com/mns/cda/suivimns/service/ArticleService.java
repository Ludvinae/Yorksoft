package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ArticleDao;
import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.model.License;
import com.mns.cda.suivimns.service.inter.iArticleService;
import com.mns.cda.suivimns.service.inter.iLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService implements iArticleService {

    protected final ArticleDao articleDao;

    @Override
    public List<Article> findAll() {
        return articleDao.findAll();
    }

    @Override
    public Optional<Article> findById(int id) {
        return articleDao.findById(id);
    }

    @Override
    public Article save(Article article) {
        article.setIdArticle(null);
        article.setModificationDate(null);
        article.setCreationDate(LocalDateTime.now());

        return articleDao.save(article);
    }

    @Override
    public void delete(Article article) {
        articleDao.delete(article);
    }

    @Override
    public Article update(Article articleToUpdate, int id) throws iArticleService.ArticleNotFoundException {
        Article currentArticle = articleDao.findById(id)
                .orElseThrow(iArticleService.ArticleNotFoundException::new);

        currentArticle.setContent(articleToUpdate.getContent());

        return articleDao.save(currentArticle);
    }
}
