package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.dao.ImpactDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImpactService {

    protected final ImpactDao impactDao;
}
