package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ClientDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    protected final ClientDao clientDao;
}
