package com.mns.cda.suivimns.dao.search;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Ticket;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                !StringUtils.hasText(keyword)
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

    /** true: no close date; false: has a close date (whatever the status). */
    public static Specification<Ticket> isNotClosed(Boolean isNotClosed) {
        return (root, query, cb) -> {
            if (isNotClosed == null) return null;
            return isNotClosed ? cb.isNull(root.get("closeDate")) : cb.isNotNull(root.get("closeDate"));
        };
    }

    public static Specification<Ticket> isOverdue(Boolean hasExceededSla) {

        return (root, query, cb) ->
                hasExceededSla == null ? null : root.get("overdue").in(hasExceededSla);
    }

    /**
     * A ticket must have exactly one active History row (endDate null) whose status matches currentStatus.
     * true: tickets breaking that rule; false: tickets respecting it.
     */
    public static Specification<Ticket> hasStatusMismatch(Boolean hasStatusMismatch) {
        return (root, query, cb) -> {
            if (hasStatusMismatch == null) return null;

            Subquery<Long> activeCount = query.subquery(Long.class);
            Root<History> active = activeCount.from(History.class);
            activeCount.select(cb.count(active))
                    .where(cb.equal(active.get("ticket"), root), cb.isNull(active.get("endDate")));

            Subquery<Integer> matching = query.subquery(Integer.class);
            Root<History> match = matching.from(History.class);
            matching.select(match.get("idHistory"))
                    .where(cb.equal(match.get("ticket"), root),
                            cb.isNull(match.get("endDate")),
                            cb.equal(match.get("status").get("code"), root.get("currentStatus")));

            Predicate mismatch = cb.or(cb.notEqual(activeCount, 1L), cb.not(cb.exists(matching)));
            return hasStatusMismatch ? mismatch : cb.not(mismatch);
        };
    }

    /**
     * The active Assignment (endDate null) must be unique and match currentTechnician. An assignment is only
     * ended by a reassignment, so every status reached through ASSIGNED keeps one; OPEN never has one.
     * true: tickets breaking those rules; false: tickets respecting them.
     */
    public static Specification<Ticket> hasAssignmentIssue(Boolean hasAssignmentIssue) {
        return (root, query, cb) -> {
            if (hasAssignmentIssue == null) return null;

            Subquery<Long> activeCount = query.subquery(Long.class);
            Root<Assignment> active = activeCount.from(Assignment.class);
            activeCount.select(cb.count(active))
                    .where(cb.equal(active.get("ticket"), root), cb.isNull(active.get("endDate")));

            Subquery<Integer> wrongTechnician = query.subquery(Integer.class);
            Root<Assignment> wrong = wrongTechnician.from(Assignment.class);
            wrongTechnician.select(wrong.get("idAssignment"))
                    .where(cb.equal(wrong.get("ticket"), root),
                            cb.isNull(wrong.get("endDate")),
                            cb.or(cb.isNull(wrong.get("technician")),
                                    cb.isNull(root.get("currentTechnician")),
                                    cb.notEqual(wrong.get("technician"), root.get("currentTechnician"))));

            Subquery<Integer> endBeforeStart = query.subquery(Integer.class);
            Root<Assignment> dates = endBeforeStart.from(Assignment.class);
            endBeforeStart.select(dates.get("idAssignment"))
                    .where(cb.equal(dates.get("ticket"), root),
                            cb.lessThan(dates.get("endDate"), dates.get("assignmentDate")));

            Predicate missingAssignment = cb.and(
                    cb.equal(activeCount, 0L),
                    cb.or(cb.isNotNull(root.get("currentTechnician")),
                            cb.not(root.get("currentStatus").in(StatusEnum.OPEN, StatusEnum.REJECTED))));
            Predicate assignedWhileOpen = cb.and(
                    cb.greaterThan(activeCount, 0L),
                    cb.equal(root.get("currentStatus"), StatusEnum.OPEN));

            Predicate issue = cb.or(
                    cb.greaterThan(activeCount, 1L),
                    missingAssignment,
                    assignedWhileOpen,
                    cb.exists(wrongTechnician),
                    cb.exists(endBeforeStart));
            return hasAssignmentIssue ? issue : cb.not(issue);
        };
    }
}
