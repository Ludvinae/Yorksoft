package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ArticleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    protected final ArticleDao articleDao;
}
