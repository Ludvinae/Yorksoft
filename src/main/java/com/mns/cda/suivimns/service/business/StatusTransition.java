package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.service.StatusService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusTransition {

    public static class IllegalStatusTransitionException extends Exception {}

    public static class MissingStatusReferenceException extends Exception {}

    private final StatusDao statusDao;

    public StatusEnum entityToEnum(Status status) throws IllegalStatusTransitionException{
        return switch (status.getDesignation()) {
            case "Ouvert" -> StatusEnum.OPEN;
            case "Clos" -> StatusEnum.CLOSED;
            case "En cours" -> StatusEnum.IN_PROGRESS;
            case "En attente client" -> StatusEnum.IN_PROGRESS;
            case "En attente tier" -> StatusEnum.IN_PROGRESS;
            case "Résolu" -> StatusEnum.IN_PROGRESS;
            case "Attribué" -> StatusEnum.IN_PROGRESS;
            case "Rejected" -> StatusEnum.REJECTED;
            default -> throw new IllegalStatusTransitionException();
        };
    }

    public Status enumToEntity(StatusEnum status) throws MissingStatusReferenceException, StatusService.StatusNotFoundException {
        String statusDesignation = switch (status) {
            case OPEN -> "Ouvert";
            case CLOSED -> "Clos";
            case IN_PROGRESS -> "En cours";
            case WAITING_CLIENT ->  "En attente client";
            case WAITING_THIRD_PARTY ->   "En attente tier";
            case SOLVED -> "Résolu";
            case ASSIGNED -> "Attribué";
            case REJECTED -> "Rejected";
            default -> throw new MissingStatusReferenceException();
        };
        return statusDao.findByDesignation(statusDesignation).orElseThrow(StatusService.StatusNotFoundException::new);
    }
}
