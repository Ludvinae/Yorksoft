package com.mns.cda.suivimns.service.search;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dao.search.TicketSpecification;
import com.mns.cda.suivimns.dto.search.TicketListDto;
import com.mns.cda.suivimns.dto.search.TicketSearchCriteria;
import com.mns.cda.suivimns.mapper.entity.TicketMapper;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class TicketQueryService {

    public static class InvalidSortCriteriaException extends RuntimeException {}

    private final TicketDao ticketDao;
    private final TicketMapper ticketMapper;

    public Page<TicketListDto> search(TicketSearchCriteria criteria, Pageable pageable)
            throws InvalidSortCriteriaException {

        validateSort(pageable);

        Specification<Ticket> spec = Specification
                .where(TicketSpecification.hasStatuses(criteria.statuses()))
                .and(TicketSpecification.hasNotStatuses(criteria.statusesExcluded()))
                .and(TicketSpecification.hasClient(criteria.hasClient()))
                .and(TicketSpecification.hasVersion(criteria.hasVersion()))
                .and(TicketSpecification.hasSoftware(criteria.hasSoftware()))
                .and(TicketSpecification.containsKeyword(criteria.keyword()))
                .and(TicketSpecification.openedAfter(criteria.createdAfter()))
                .and(TicketSpecification.openedBefore(criteria.createdBefore()))
                .and(TicketSpecification.closedAfter(criteria.closedAfter()))
                .and(TicketSpecification.closedBefore(criteria.closedBefore()))
                .and(TicketSpecification.priorityEquals(criteria.priorityEquals()))
                .and(TicketSpecification.priorityGreaterThan(criteria.priorityGreaterThan()))
                .and(TicketSpecification.priorityLessThan(criteria.priorityLessThan()))
                .and(TicketSpecification.assignedTo(criteria.assignedTo()))
                .and(TicketSpecification.isNotClosed(criteria.isNotClosed()))
                .and(TicketSpecification.isOverdue(criteria.isOverdue()));


        return ticketDao
                .findAll(spec, pageable)
                .map(ticketMapper::toListDto);
    }

    private void validateSort(Pageable pageable) throws InvalidSortCriteriaException {
        for (Sort.Order order : pageable.getSort()) {
            if (!ALLOWED_SORTS.contains(order.getProperty())) {
                throw new InvalidSortCriteriaException();
            }
        }
    }

    private static final Set<String> ALLOWED_SORTS = Set.of(
            "openDate",
            "modificationDate",
            "closeDate",
            "slaDeadline",
            "currentPriority",
            "currentStatus",
            "currentTechnician",
            "currentTheme",
            "title",
            "version.software.name",
            "version.versionNumber"
    );
}
