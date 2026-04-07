package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ClassificationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassificationService {

    protected final ClassificationDao classificationDao;
}
