package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ThemeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {

    protected final ThemeDao themeDao;
}
