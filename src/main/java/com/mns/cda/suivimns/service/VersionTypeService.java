package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.VersionTypeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionTypeService {

    protected final VersionTypeDao versionTypeDao;
}
