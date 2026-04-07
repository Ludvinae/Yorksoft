package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.StatusDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService {

    protected final StatusDao statusDao;
}
