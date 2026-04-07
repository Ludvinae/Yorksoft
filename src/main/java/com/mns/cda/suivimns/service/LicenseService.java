package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.dao.LicenseDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LicenseService {

    protected final LicenseDao licenseDao;
}
