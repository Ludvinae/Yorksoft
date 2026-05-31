package com.mns.cda.suivimns.service.search;

import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dao.search.ClientSpecification;
import com.mns.cda.suivimns.dto.search.ClientListDto;
import com.mns.cda.suivimns.dto.search.ClientSearchCriteria;
import com.mns.cda.suivimns.exception.InvalidSortCriteriaException;
import com.mns.cda.suivimns.mapper.entity.ClientMapper;
import com.mns.cda.suivimns.model.Client;
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
            throws InvalidSortCriteriaException {

        validateSort(pageable);

        Specification<Client> spec = Specification
                .where(ClientSpecification.hasLicense(criteria.softwareId()))
                .and(ClientSpecification.containsKeyword(criteria.keyword()))
                .and(ClientSpecification.hasOpenTicket(criteria.hasOpenTicket()))
                .and(ClientSpecification.importanceGreaterThan(criteria.importanceGreaterThan()));


        return clientDao
                .findAll(spec, pageable)
                .map(clientMapper::toListDto);
    }

    private void validateSort(Pageable pageable) throws InvalidSortCriteriaException {
        for (Sort.Order order : pageable.getSort()) {
            if (!ALLOWED_SORTS.contains(order.getProperty())) {
                throw new InvalidSortCriteriaException();
            }
        }
    }

    private static final Set<String> ALLOWED_SORTS = Set.of(
            "firstName",
            "lastName",
            "email",
            "phoneNumber",
            "importance"
    );
}
