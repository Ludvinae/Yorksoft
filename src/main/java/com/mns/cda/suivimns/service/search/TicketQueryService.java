package com.mns.cda.suivimns.service.search;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dao.TicketSpecification;
import com.mns.cda.suivimns.dto.search.TicketListDto;
import com.mns.cda.suivimns.dto.search.TicketSearchCriteria;
import com.mns.cda.suivimns.mapper.entity.TicketMapper;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TicketQueryService {

    private final TicketDao ticketDao;
    private final TicketMapper ticketMapper;

    public Page<TicketListDto> search(TicketSearchCriteria criteria, Pageable pageable) {
        Specification<Ticket> spec = Specification
                .where(TicketSpecification.hasStatus(criteria.status()))
                .and(TicketSpecification.hasClient(criteria.clientId()))
                .and(TicketSpecification.hasSoftware(criteria.softwareId()))
                .and(TicketSpecification.containsKeyword(criteria.keyword()))
                .and(TicketSpecification.openedAfter(criteria.createdAfter()))
                .and(TicketSpecification.openedBefore(criteria.createdBefore()));

        return ticketDao
                .findAll(spec, pageable)
                .map(ticketMapper::toListDto);
    }
}
