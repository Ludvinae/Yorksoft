package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.DirectorDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class DirectorController {

    protected final DirectorDao directorDao;
}
