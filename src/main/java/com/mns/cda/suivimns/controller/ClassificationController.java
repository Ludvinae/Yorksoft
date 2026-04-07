package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.ClassificationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ClassificationController {

    protected final ClassificationDao classificationDao;
}
