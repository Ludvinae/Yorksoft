package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.CommentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    protected final CommentDao commentDao;
}
