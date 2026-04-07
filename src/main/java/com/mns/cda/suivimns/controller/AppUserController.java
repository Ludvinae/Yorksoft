package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.AppUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AppUserController {

    protected final AppUserDao appUserDao;
}
