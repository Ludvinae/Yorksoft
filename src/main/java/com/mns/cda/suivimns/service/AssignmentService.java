package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AssignmentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    protected final AssignmentDao assignmentDao;
}
