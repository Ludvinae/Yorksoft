package com.mns.cda.suivimns.config;

import com.mns.cda.suivimns.dto.config.ErrorResponseDto;
import com.mns.cda.suivimns.service.entity.AppUserService;
import com.mns.cda.suivimns.service.entity.AssignmentService;
import com.mns.cda.suivimns.service.entity.TicketService;
import com.mns.cda.suivimns.service.workflow.StatusTransition;
import com.mns.cda.suivimns.service.workflow.TicketStatusService;
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
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(TicketService.TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleTicketNotFound(
            TicketService.TicketNotFoundException ex
    ) {
        return new ErrorResponseDto(
                404,"TICKET_NOT_FOUND", "Ticket introuvable"
        );
    }

    @ExceptionHandler(StatusTransition.IllegalStatusTransitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleIllegalTransition(
            StatusTransition.IllegalStatusTransitionException ex
    ) {
        return new ErrorResponseDto(
                400,"BAD_STATUS_REQUEST", "Transition de statut invalide"
        );
    }

    @ExceptionHandler(TicketStatusService.MissingCurrentHistoryException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseDto handleMissingCurrentHistory(
            TicketStatusService.MissingCurrentHistoryException ex
    ) {
        return new ErrorResponseDto(
                422,"UNPROCESSABLE_HISTORY", "Historique courant introuvable"
        );
    }

    @ExceptionHandler(AppUserService.AppUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleAppUserNotFound(
            AppUserService.AppUserNotFoundException ex
    ) {
        return new ErrorResponseDto(
                404,
                "USER_NOT_FOUND",
                "L'utilisateur n'existe pas"
        );
    }

    @ExceptionHandler(AssignmentService.AssignmentConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleTechnicianAlreadyAssigned(
            AssignmentService.AssignmentConflictException ex
    ) {
        return new ErrorResponseDto(
                409,
                "ASSIGNMENT_CONFLICT",
                "Le technicien est déja attribué"
        );
    }
}
