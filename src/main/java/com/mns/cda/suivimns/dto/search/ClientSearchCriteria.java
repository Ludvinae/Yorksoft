package com.mns.cda.suivimns.dto.search;

public record ClientSearchCriteria(
        String keyword,
        Integer softwareId
) {
}
