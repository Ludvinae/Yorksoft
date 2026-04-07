package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KnowledgeService {

    protected final KnowledgeDao knowledgeDao;
}
