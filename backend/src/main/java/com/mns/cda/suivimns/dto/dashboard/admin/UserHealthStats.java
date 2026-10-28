package com.mns.cda.suivimns.dto.dashboard.admin;

/**
 * Admin dashboard "Utilisateurs" KPI row: account integrity and security indicators.
 */
public record UserHealthStats(
        long withoutRole,
        long multipleRoles,
        long uninitializedPasswords,
        long inactiveEmployees,
        long neverActiveEmployees,
        long admins
) {
}
