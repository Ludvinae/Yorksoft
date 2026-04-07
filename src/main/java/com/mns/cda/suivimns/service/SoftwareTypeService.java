package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftwareTypeService {

    protected final SoftwareTypeDao softwareTypeDao;
}
