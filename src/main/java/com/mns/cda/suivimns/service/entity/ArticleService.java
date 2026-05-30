package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.ArticleDao;
import com.mns.cda.suivimns.dto.entity.ArticleDto;
import com.mns.cda.suivimns.exception.ArticleNotFoundException;
import com.mns.cda.suivimns.exception.ArticleNotOwnedException;
import com.mns.cda.suivimns.mapper.entity.ArticleMapper;
import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleService {

    protected final ArticleDao articleDao;
    protected final ArticleMapper articleMapper;

    public List<ArticleDto> findAll() {
        return articleMapper.toDtoList(articleDao.findAll());
    }

    public ArticleDto findById(int id) throws ArticleNotFoundException {
        Article article = articleDao.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        return articleMapper.toDto(article);
    }

    public ArticleDto save(ArticleDto dto) {
        Article article = articleMapper.toEntity(dto);
        article.setIdArticle(null);
        Article saved = articleDao.save(article);

        return articleMapper.toDto(saved);
    }

    public void delete(int id) throws ArticleNotFoundException {
        Article article = articleDao.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        articleDao.delete(article);
    }

    public ArticleDto update(int id, ArticleDto articleToUpdate, AppUserDetails userDetails) throws ArticleNotFoundException {

        Article currentArticle = articleDao.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        // On verifie si l'utilisateur est admin ou s'il est le proprietaire de la ressource
        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                !currentArticle.getTechnician().getIdAppUser().equals(userDetails.getId())) {
            throw new ArticleNotOwnedException();
        }

        articleMapper.updateEntityFromDto(articleToUpdate, currentArticle);

        return articleMapper.toDto(articleDao.save(currentArticle));
    }
}
