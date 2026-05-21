package com.mns.cda.suivimns.service.search;

import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dao.search.ClientSpecification;
import com.mns.cda.suivimns.dao.search.TicketSpecification;
import com.mns.cda.suivimns.dto.search.ClientListDto;
import com.mns.cda.suivimns.dto.search.ClientSearchCriteria;
import com.mns.cda.suivimns.dto.search.TicketListDto;
import com.mns.cda.suivimns.dto.search.TicketSearchCriteria;
import com.mns.cda.suivimns.mapper.entity.ClientMapper;
import com.mns.cda.suivimns.model.Client;
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
public class ClientQueryService {

    private final ClientDao clientDao;
    private final ClientMapper clientMapper;

    public Page<ClientListDto> search(ClientSearchCriteria criteria, Pageable pageable)
            throws TicketQueryService.InvalidSortCriteriaException {

        validateSort(pageable);

        Specification<Client> spec = Specification
                .where(ClientSpecification.hasLicense(criteria.softwareId()))
                .and(ClientSpecification.containsKeyword(criteria.keyword()));


        return clientDao
                .findAll(spec, pageable)
                .map(clientMapper::toListDto);
    }

    private void validateSort(Pageable pageable) throws TicketQueryService.InvalidSortCriteriaException {
        for (Sort.Order order : pageable.getSort()) {
            if (!ALLOWED_SORTS.contains(order.getProperty())) {
                throw new TicketQueryService.InvalidSortCriteriaException();
            }
        }
    }

    private static final Set<String> ALLOWED_SORTS = Set.of(
            "firstName",
            "lastName",
            "email",
            "importance"
    );
}
