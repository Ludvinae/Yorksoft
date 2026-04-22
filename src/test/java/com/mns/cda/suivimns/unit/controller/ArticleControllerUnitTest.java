package com.mns.cda.suivimns.unit.controller;

import com.mns.cda.suivimns.model.Article;
import org.junit.jupiter.api.BeforeEach;

public class ArticleControllerUnitTest {



    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article();
        article.setIdArticle(1);
        article.setContent("Test content");
    }
}
