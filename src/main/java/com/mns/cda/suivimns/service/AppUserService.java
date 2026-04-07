package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AppUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {

    protected final AppUserDao appUserDao;
}
