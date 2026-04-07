package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.TechnicianDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TechnicianController {

    protected final TechnicianDao technicianDao;
}
