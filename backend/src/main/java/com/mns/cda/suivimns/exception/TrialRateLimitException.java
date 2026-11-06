package com.mns.cda.suivimns.exception;

import lombok.Getter;

@Getter
public class TrialRateLimitException extends RuntimeException {

    private final long retryAfterSeconds;

    public TrialRateLimitException(long retryAfterSeconds) {
        this.retryAfterSeconds = retryAfterSeconds;
    }
}
