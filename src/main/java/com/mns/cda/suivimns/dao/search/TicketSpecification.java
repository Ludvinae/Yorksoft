package com.mns.cda.suivimns.dao.search;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.Ticket;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class TicketSpecification {

    public static Specification<Ticket> hasStatuses(Set<StatusEnum> statuses) {
        return (root, query, cb) ->
                statuses == null ? null : root
                        .get("currentStatus").in(statuses);
    }

    public static Specification<Ticket> hasNotStatuses(Set<StatusEnum> statusesExcluded) {
        return (root, query, cb) ->
                statusesExcluded == null ? null : cb
                        .not(root.get("currentStatus").in(statusesExcluded));
    }

    public static Specification<Ticket> hasClient(Integer clientId) {
        return (root, query, cb) ->
                clientId == null ? null : cb
                        .equal(root.get("client").get("idAppUser"), clientId);
    }

    public static Specification<Ticket> hasVersion(Integer versionId) {
        return (root, query, cb) ->
                versionId == null ? null : cb
                        .equal(root.get("version").get("idVersion"), versionId);
    }

    public static Specification<Ticket> hasSoftware(Integer softwareId) {
        return (root, query, cb) ->
                softwareId == null ? null : cb
                        .equal(root.get("version").get("software").get("idSoftware"), softwareId);
    }

    public static Specification<Ticket> containsKeyword(String keyword) {
        return (root, query, cb) ->
                keyword == null || keyword.isBlank()
                        ? null
                        : cb.like(
                        cb.lower(root.get("title")),
                        "%" + keyword.toLowerCase() + "%"
                );
    }

    public static Specification<Ticket> openedAfter(LocalDate createdAfter) {
        return (root, query, cb) ->
                createdAfter == null ? null : cb
                        .greaterThanOrEqualTo(root.get("openDate"), createdAfter.atStartOfDay());
    }

    public static Specification<Ticket> openedBefore(LocalDate createdBefore) {
        return (root, query, cb) ->
                createdBefore == null ? null : cb
                        .lessThanOrEqualTo(root.get("openDate"), createdBefore.atTime(LocalTime.MAX));
    }

    public static Specification<Ticket> closedAfter(LocalDate closedAfter) {
        return (root, query, cb) ->
                closedAfter == null ? null : cb
                        .greaterThanOrEqualTo(root.get("closeDate"), closedAfter.atStartOfDay());
    }

    public static Specification<Ticket> closedBefore(LocalDate closedBefore) {
        return (root, query, cb) ->
                closedBefore == null ? null : cb
                        .lessThanOrEqualTo(root.get("closeDate"), closedBefore.atTime(LocalTime.MAX));
    }

    public static Specification<Ticket> priorityGreaterThan(Integer priority) {
        return (root, query, cb) ->
                priority == null ? null : cb.greaterThan(root.get("currentPriority"), priority);
    }

    public static Specification<Ticket> priorityLessThan(Integer priority) {
        return (root, query, cb) ->
                priority == null ? null : cb.lessThan(root.get("currentPriority"), priority);
    }

    public static Specification<Ticket> priorityEquals(Integer priority) {
        return (root, query, cb) ->
                priority == null ? null : cb.equal(root.get("currentPriority"), priority);
    }

    public static Specification<Ticket> assignedTo(Integer idAppUser) {
        return (root, query, cb) ->
                idAppUser == null ? null : cb.equal(root.get("currentTechnician").get("idAppUser"), idAppUser);
    }

}
