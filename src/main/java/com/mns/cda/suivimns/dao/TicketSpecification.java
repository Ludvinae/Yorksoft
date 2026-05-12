package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.Ticket;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

public class TicketSpecification {

    public static Specification<Ticket> hasStatus(StatusEnum status) {
        return (root, query, cb) ->
                status == null
                        ? null
                        : cb.equal(root.get("currentStatus"), status);
    }

    public static Specification<Ticket> hasNotStatus(StatusEnum statusExcluded) {
        return (root, query, cb) ->
                statusExcluded == null
                        ? null
                        : cb.notEqual(root.get("currentStatus"), statusExcluded);
    }

    public static Specification<Ticket> hasClient(Integer clientId) {
        return (root, query, cb) ->
                clientId == null
                        ? null
                        : cb.equal(root.get("client").get("idAppUser"), clientId);
    }

    public static Specification<Ticket> hasSoftware(Integer softwareId) {
        return (root, query, cb) ->
                softwareId == null
                        ? null
                        : cb.equal(
                        root.get("version")
                                .get("software")
                                .get("idSoftware"),
                        softwareId
                );
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
                createdAfter == null ? null : cb.greaterThanOrEqualTo(root.get("openDate"), createdAfter.atStartOfDay());
    }

    public static Specification<Ticket> openedBefore(LocalDate createdBefore) {
        return (root, query, cb) ->
                createdBefore == null ? null : cb.lessThanOrEqualTo(root.get("openDate"), createdBefore.atTime(LocalTime.MAX));
    }

}
