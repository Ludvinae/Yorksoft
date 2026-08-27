package com.mns.cda.suivimns.config;

import com.mns.cda.suivimns.dto.config.ErrorResponseDto;
import com.mns.cda.suivimns.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

        logger.error(message.toString());
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

    @ExceptionHandler(AppUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleMissingUser(AppUserNotFoundException ex) {

        return new ErrorResponseDto(404, "USER_NOT_FOUND", "Impossible de retrouver cette utilisateur");
    }

    @ExceptionHandler(RessourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleMissingRessource(RessourceNotFoundException ex) {

        return new ErrorResponseDto(404, "RESSOURCE_NOT_FOUND", "Impossible de retrouver cette ressource");
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


    @ExceptionHandler(AssignmentConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleTechnicianAssignmentConflict(
            AssignmentConflictException ex
    ) {
        return new ErrorResponseDto(
                409,
                "ASSIGNMENT_CONFLICT",
                "Technicien non assigné ou déja assigné"
        );
    }

    @ExceptionHandler(TicketAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleAccessDenied(TicketAccessDeniedException ex) {

        return new ErrorResponseDto(403, "TICKET_ACCESS_DENIED", "Non autorisé à acceder à ce ticket");
    }

    @ExceptionHandler(InvalidUserRoleException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleInvalidRole(InvalidUserRoleException ex) {

        return new ErrorResponseDto(401, "INVALID_USER_ROLE", "Role non reconnu par le serveur");
    }

    @ExceptionHandler(InvalidStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidRole(InvalidStatusException ex) {

        return new ErrorResponseDto(400, "INVALID_STATUS", "Statut non reconnu par le serveur");
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

    @ExceptionHandler(TicketNotEditableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleEditingClosedTicket(TicketNotEditableException ex) {

        return new ErrorResponseDto(409, "NOT_EDITABLE",
                "Ticket déja clos");
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

    @ExceptionHandler(UnauthorizedTechnicianException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleUnauthorizedTechnician(UnauthorizedTechnicianException ex) {

        return new ErrorResponseDto(403, "UNAUTHORIZED_TECHNICIAN",
                "This technician can't handle this ressource");
    }


    // A ameliorer pour eviter de reveler des informations
    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleEmailConflict(EmailAlreadyUsedException ex) {

        return new ErrorResponseDto(409, "EMAIL_UNAVAILABLE",
                "Email déja utilisé");
    }

    @ExceptionHandler(BadPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleWrongPassword(BadPasswordException ex) {

        return new ErrorResponseDto(400, "BAD_PASSWORD",
                "Mot de passe incorrect");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleUnauthorizedUser(AuthenticationException ex) {

        return new ErrorResponseDto(401, "UNAUTHORIZED_USER",
                "User have to be logged in");
    }

    @ExceptionHandler(RessourceNotOwnedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleAccountNotOwned(RessourceNotOwnedException ex) {

        return new ErrorResponseDto(403, "RESSOURCE_NOT_OWNED",
                "User doesn't own this ressource");
    }

    @ExceptionHandler(SelfDeletionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleSelfDeletion(SelfDeletionException ex) {

        return new ErrorResponseDto(400, "CANNOT_DELETE_SELF",
                "Un utilisateur ne peut pas supprimer son propre compte");
    }

    @ExceptionHandler(LastAdminException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleLastAdmin(LastAdminException ex) {

        return new ErrorResponseDto(409, "LAST_ADMIN",
                "Impossible de supprimer le dernier compte administrateur");
    }

    @ExceptionHandler(IncoherentHistoryTimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleIncoherentHistory(IncoherentHistoryTimeException ex) {

        return new ErrorResponseDto(500, "INCOHERENT_HISTORY",
                "Problème d'incoherence de l'historique");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleAccessDenied(
            AccessDeniedException ex) {

        return new ErrorResponseDto(
                403,
                "ACCESS_DENIED",
                "Accès refusé"
        );
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleAuthenticationError(
            AuthenticationCredentialsNotFoundException ex) {

        return new ErrorResponseDto(
                401,
                "UNAUTHORIZED",
                "Authentification requise"
        );
    }

    // Interception automatique des exceptions non gérés plus haut
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleUnexpectedException(Exception ex) {

        logger.error("Unexpected error", ex);
        return new ErrorResponseDto(
                500,
                "INTERNAL_ERROR",
                "Une erreur interne est survenue"
        );
    }
}
