package com.mns.cda.suivimns.dao.search;

import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.Version;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class KnowledgeSpecification {

    public static Specification<Knowledge> search(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return null;
            }

            String pattern = "%" + search.toLowerCase() + "%";

            return cb.or(cb.like(cb.lower(root.get("subject")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern));
        };
    }

    public static Specification<Knowledge> theme(Integer idTheme) {
        return (root, query, cb) ->
                idTheme == null
                        ? null
                        : cb.equal(root.get("theme").get("idTheme"), idTheme);
    }

    public static Specification<Knowledge> version(Integer idVersion) {
        return (root, query, cb) -> {
            if (idVersion == null) {
                return null;
            }

            Join<Knowledge, Version> versionJoin = root.join("versionList");

            return cb.equal(versionJoin.get("idVersion"), idVersion);
        };
    }
}
