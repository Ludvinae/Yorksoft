package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.SoftwareDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftwareService {

    protected final SoftwareDao softwareDao;
}
