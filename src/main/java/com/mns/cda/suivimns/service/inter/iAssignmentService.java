package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Assignment;

import java.util.List;
import java.util.Optional;

public interface iAssignmentService {
    List<Assignment> findAll();

    Optional<Assignment> findById(int id);

    void modify(Assignment assignment, int id);

    Assignment firstSave(Assignment assignment);

    void close(Assignment assignment, int id);


    class AssignmentNotFoundException extends Exception {
    }

    class AssignmentBadRequestException extends Exception {
    }
}
