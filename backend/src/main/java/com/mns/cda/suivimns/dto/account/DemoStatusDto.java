package com.mns.cda.suivimns.dto.account;

/**
 * Tells the login page whether trial accounts can be created, and how long they last.
 */
public record DemoStatusDto(boolean enabled, int ttlHours) {
}
