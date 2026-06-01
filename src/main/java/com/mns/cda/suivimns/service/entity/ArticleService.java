package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.ArticleDao;
import com.mns.cda.suivimns.dto.entity.ArticleDto;
import com.mns.cda.suivimns.exception.AppUserNotFoundException;
import com.mns.cda.suivimns.exception.ArticleNotFoundException;
import com.mns.cda.suivimns.exception.ArticleNotOwnedException;
import com.mns.cda.suivimns.exception.UnauthorizedTechnicianException;
import com.mns.cda.suivimns.mapper.entity.ArticleMapper;
import com.mns.cda.suivimns.model.AppUser;
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

    protected final AppUserDao appUserDao;
    protected final ActivityService activityService;

    public List<ArticleDto> findAll() {
        return articleMapper.toDtoList(articleDao.findAll());
    }

    public ArticleDto findById(int id) {
        Article article = articleDao.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        return articleMapper.toDto(article);
    }

    public ArticleDto save(ArticleDto dto, AppUserDetails user) {
        if (user.getTechnician() == null) {
            throw new UnauthorizedTechnicianException();
        }

        Article article = articleMapper.toEntity(dto);
        article.setIdArticle(null);
        article.setTechnician(user.getTechnician());
        Article saved = articleDao.save(article);

        AppUser author = appUserDao.findById(user.getId()).orElseThrow(AppUserNotFoundException::new);
        activityService.log(author, "A écrit un article à propos de la connaissance #" + dto.idKnowledge());

        return articleMapper.toDto(saved);
    }

    public void delete(int id, AppUserDetails user) {
        Article article = articleDao.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        AppUser author = appUserDao.findById(user.getId()).orElseThrow(AppUserNotFoundException::new);
        activityService.log(author, "A effacé l'article #" + id);

        articleDao.delete(article);
    }

    public ArticleDto update(int id, ArticleDto articleToUpdate, AppUserDetails userDetails) {

        Article currentArticle = articleDao.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        // On verifie si l'utilisateur est admin ou s'il est le proprietaire de la ressource
        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                !currentArticle.getTechnician().getIdAppUser().equals(userDetails.getId())) {
            throw new ArticleNotOwnedException();
        }

        articleMapper.updateEntityFromDto(articleToUpdate, currentArticle);

        AppUser author = appUserDao.findById(userDetails.getId()).orElseThrow(AppUserNotFoundException::new);
        activityService.log(author, "A édité l'article #" + id);

        return articleMapper.toDto(articleDao.save(currentArticle));
    }
}
