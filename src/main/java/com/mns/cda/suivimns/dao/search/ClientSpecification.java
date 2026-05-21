package com.mns.cda.suivimns.dao.search;

import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.Ticket;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    public static Specification<Client> containsKeyword(String keyword) {

        return (root, query, cb) -> {

            if (keyword == null || keyword.isBlank()) {
                return null;
            }

            String pattern = "%" + keyword.toLowerCase() + "%";
            Expression<String> fullName = cb
                    .concat(cb.concat(cb.lower(root.get("firstName"))," "), cb
                            .lower(root.get("lastName"))
            );

            Expression<String> reverseFullName = cb
                    .concat(cb.concat(cb.lower(root.get("lastName"))," "), cb
                            .lower(root.get("firstName"))
            );

            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), pattern),
                    cb.like(cb.lower(root.get("lastName")), pattern),
                    cb.like(fullName, pattern),
                    cb.like(reverseFullName, pattern)
            );
        };
    }

    public static Specification<Client> hasLicense(Integer softwareId) {
        return (root, query, cb) -> {

            if (softwareId == null) {return null;}

            query.distinct(true);

            return cb.equal(root.join("licenseList")
                            .join("software")
                            .get("idSoftware"),
                    softwareId);
        };}

    public static Specification<Client> importanceGreaterThan(Integer importance) {
        return (root, query, cb) ->
                importance == null ? null : cb.greaterThan(root.get("importance"), importance);
    }

    public static Specification<Client> hasOpenTicket(Boolean hasOpenTicket) {

        return (root, query, cb) -> {

            if (hasOpenTicket == null || !hasOpenTicket) {
                return null;
            }

            query.distinct(true);

            return cb.isNull(
                    root.join("ticketList")
                            .get("closeDate")
            );
        };
    }
}
