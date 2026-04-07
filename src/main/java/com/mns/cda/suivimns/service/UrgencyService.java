package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.dao.UrgencyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrgencyService {

    protected final UrgencyDao urgencyDao;
}
