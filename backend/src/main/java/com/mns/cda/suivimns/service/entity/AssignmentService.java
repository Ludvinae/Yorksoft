package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AssignmentDao;
import com.mns.cda.suivimns.dto.entity.AssignmentDto;
import com.mns.cda.suivimns.exception.AssignmentNotFoundException;
import com.mns.cda.suivimns.mapper.entity.AssignmentMapper;
import com.mns.cda.suivimns.model.Assignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    protected final AssignmentDao assignmentDao;
    protected final AssignmentMapper assignmentMapper;

    public List<AssignmentDto> findAll() {
        return assignmentMapper.toDtoList(assignmentDao.findAll());
    }

    public AssignmentDto findById(int id) {
        Assignment assignment = assignmentDao.findById(id)
                .orElseThrow(AssignmentNotFoundException::new);

        return assignmentMapper.toDto(assignment);
    }

    public AssignmentDto save(AssignmentDto dto) {
        Assignment assignment = assignmentMapper.toEntity(dto);
        assignment.setIdAssignment(null);
        Assignment saved = assignmentDao.save(assignment);

        return assignmentMapper.toDto(saved);
    }


}
