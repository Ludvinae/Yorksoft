package com.mns.cda.suivimns.config;

import com.mns.cda.suivimns.dto.config.ErrorResponseDto;
import com.mns.cda.suivimns.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionInterceptor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto constraintViolationInterceptor(MethodArgumentNotValidException ex) {
        StringBuilder message = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            message.append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append(" | ");
        }

        return new ErrorResponseDto(
                400, "BAD_REQUEST", message.toString()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto constraintViolationDatabase(DataIntegrityViolationException ex) {
        return new ErrorResponseDto(
                409, "CONFLICT", "Erreur de contrainte");
    }


    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleIllegalState(IllegalStateException ex) {

        return new ErrorResponseDto(400, "ILLEGAL_STATE", "Statut invalide");
    }


    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleMissingTicket(TicketNotFoundException ex) {

        return new ErrorResponseDto(404, "TICKET_NOT_FOUND", "Impossible de retrouver ce ticket");
    }

    @ExceptionHandler(IllegalStatusTransitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleIllegalTransition(
            IllegalStatusTransitionException ex
    ) {
        return new ErrorResponseDto(
                400,"BAD_STATUS_REQUEST", "Transition de statut invalide"
        );
    }

    @ExceptionHandler(MissingCurrentHistoryException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseDto handleMissingCurrentHistory(
            MissingCurrentHistoryException ex
    ) {
        return new ErrorResponseDto(
                422,"UNPROCESSABLE_HISTORY", "Historique courant introuvable"
        );
    }

    @ExceptionHandler(AppUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleAppUserNotFound(
            AppUserNotFoundException ex
    ) {
        return new ErrorResponseDto(
                404,
                "USER_NOT_FOUND",
                "L'utilisateur n'existe pas"
        );
    }

    @ExceptionHandler(AssignmentConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleTechnicianAlreadyAssigned(
            AssignmentConflictException ex
    ) {
        return new ErrorResponseDto(
                409,
                "ASSIGNMENT_CONFLICT",
                "Le technicien est déja attribué"
        );
    }

    @ExceptionHandler(TicketAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleAccessDenied(TicketAccessDeniedException ex) {

        return new ErrorResponseDto(403, "TICKET_ACCESS_DENIED", "Non autorisé à acceder à ce ticket");
    }

    @ExceptionHandler(InvalidUserRoleException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleInvalidRole(InvalidUserRoleException ex) {

        return new ErrorResponseDto(500, "INVALID_USER_ROLE", "Role non reconnu par le serveur");
    }

    @ExceptionHandler(InvalidStatusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleInvalidRole(InvalidStatusException ex) {

        return new ErrorResponseDto(500, "INVALID_STATUS", "Statut non reconnu par le serveur");
    }


    @ExceptionHandler(StatusNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleMissingStatus(StatusNotFoundException ex) {

        return new ErrorResponseDto(404, "STATUS_NOT_FOUND", "Impossible de retrouver ce statut");
    }

    @ExceptionHandler(InvalidSortCriteriaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidSortCriteria(InvalidSortCriteriaException ex) {

        return new ErrorResponseDto(400, "INVALID_SORT_CRITERIA", "Critère de tri non reconnu");
    }

    @ExceptionHandler(MissingTicketSolutionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleMissingSolution(MissingTicketSolutionException ex) {

        return new ErrorResponseDto(409, "MISSING_TICKET_SOLUTION",
                "Le ticket doit contenir une solution afin de pouvoir être considéré résolu");
    }

    @ExceptionHandler(TicketNotEditableInCurrentStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleMissingSolution(TicketNotEditableInCurrentStateException ex) {

        return new ErrorResponseDto(409, "NOT_EDITABLE_IN_CURRENT_STATE",
                "Prérequis manquant afin de transitionner vers un nouvel état");
    }

    @ExceptionHandler(MissingAssignedTechnicianException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMissingAssignedTechnician(MissingAssignedTechnicianException ex) {

        return new ErrorResponseDto(400, "NO_TECHNICIAN_ASSIGNED",
                "Transition requires a technician to be assigned");
    }

    @ExceptionHandler(MissingStatusTransitionJustificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMissingJustification(MissingStatusTransitionJustificationException ex) {

        return new ErrorResponseDto(400, "MISSING_JUSTIFICATION",
                "Transition requires a justification");
    }
}
