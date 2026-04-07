package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.model.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryService {

    protected final HistoryDao historyDao;
}
