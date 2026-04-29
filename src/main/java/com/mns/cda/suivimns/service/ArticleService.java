package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ArticleDao;
import com.mns.cda.suivimns.dto.ArticleDto;
import com.mns.cda.suivimns.mapper.ArticleMapper;
import com.mns.cda.suivimns.model.Article;
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
    protected final ArticleMapper articleMapper;

    public List<ArticleDto> findAll() {
        return articleMapper.toDtoList(articleDao.findAll());
    }

    public ArticleDto findById(int id) throws ArticleService.ArticleNotFoundException {
        Article article = articleDao.findById(id)
                .orElseThrow(ArticleService.ArticleNotFoundException::new);

        return articleMapper.toDto(article);
    }

    public ArticleDto save(ArticleDto dto) {
        Article article = articleMapper.toEntity(dto);
        article.setIdArticle(null);
        Article saved = articleDao.save(article);

        return articleMapper.toDto(saved);
    }

    public void delete(int id) throws ArticleService.ArticleNotFoundException {
        Article article = articleDao.findById(id)
                .orElseThrow(ArticleService.ArticleNotFoundException::new);

        articleDao.delete(article);
    }

    public ArticleDto update(int id, ArticleDto articleToUpdate) throws ArticleService.ArticleNotFoundException {

        Article currentArticle = articleDao.findById(id)
                .orElseThrow(ArticleService.ArticleNotFoundException::new);

        articleMapper.updateEntityFromDto(articleToUpdate, currentArticle);

        return articleMapper.toDto(articleDao.save(currentArticle));
    }
}
