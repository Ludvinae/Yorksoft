package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dao.DirectorDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorService {

    protected final DirectorDao directorDao;
}
