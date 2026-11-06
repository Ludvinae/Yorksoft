package com.mns.cda.suivimns.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Limits how many trial accounts one IP can create (3 per hour by default).
 */
@Component
public class DemoCreationLimiter {

    private final FixedWindowCounter counter;

    public DemoCreationLimiter(@Value("${app.demo.max-accounts-per-ip:3}") int maxPerIp,
                               @Value("${app.demo.ip-window-seconds:3600}") long windowSeconds) {
        this.counter = new FixedWindowCounter(maxPerIp, windowSeconds);
    }

    public boolean tryAcquire(String ip) {
        return counter.tryAcquire(ip);
    }

    public long secondsUntilReset(String ip) {
        return counter.secondsUntilReset(ip);
    }
}
