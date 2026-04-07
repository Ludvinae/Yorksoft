package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.TechnicianDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechnicianService {

    protected final TechnicianDao technicianDao;
}
