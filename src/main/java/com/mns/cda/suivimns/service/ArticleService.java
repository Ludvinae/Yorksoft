package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ArticleDao;
import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.service.inter.iArticleService;
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
    public void save(Article article) {
        article.setIdArticle(null);
        article.setModificationDate(null);
        article.setCreationDate(LocalDateTime.now());

        articleDao.save(article);
    }

    @Override
    public void delete(Article article) {
        articleDao.delete(article);
    }

    @Override
    public void update(Article articleToUpdate, int id) throws iArticleService.ArticleNotFoundException {
        Optional<Article> article = articleDao.findById(id);

        if (article.isEmpty()) {
            throw new iArticleService.ArticleNotFoundException();
        }

        articleToUpdate.setIdArticle(article.get().getIdArticle());
        articleToUpdate.setCreationDate(null);
        articleToUpdate.setModificationDate(LocalDateTime.now());

        articleDao.save(articleToUpdate);
    }
}
