package com.mns.cda.suivimns.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Compteur de tentatives de connexion en memoire, par cle (IP + email tente).
 * Fenetre glissante simple : une cle est bloquee des que son compteur atteint
 * maxAttempts, et le compteur repart a zero une fois windowSeconds ecoules
 * depuis la premiere tentative echouee de la fenetre.
 */
@Component
public class LoginAttemptService {

    @Value("${app.login.max-attempts:5}")
    private int maxAttempts;

    @Value("${app.login.window-seconds:600}")
    private long windowSeconds;

    private final ConcurrentHashMap<String, Attempt> attemptsByKey = new ConcurrentHashMap<>();

    public boolean isBlocked(String key) {
        Attempt attempt = attemptsByKey.get(key);
        if (attempt == null) {
            return false;
        }
        if (isExpired(attempt)) {
            attemptsByKey.remove(key, attempt);
            return false;
        }
        return attempt.count.get() >= maxAttempts;
    }

    public void registerFailure(String key) {
        attemptsByKey.compute(key, (k, existing) -> {
            if (existing == null || isExpired(existing)) {
                return new Attempt(System.currentTimeMillis());
            }
            existing.count.incrementAndGet();
            return existing;
        });
    }

    public void registerSuccess(String key) {
        attemptsByKey.remove(key);
    }

    public long secondsUntilUnblocked(String key) {
        Attempt attempt = attemptsByKey.get(key);
        if (attempt == null) {
            return 0;
        }
        long elapsedSeconds = (System.currentTimeMillis() - attempt.windowStart) / 1000;
        return Math.max(0, windowSeconds - elapsedSeconds);
    }

    private boolean isExpired(Attempt attempt) {
        return System.currentTimeMillis() - attempt.windowStart >= windowSeconds * 1000;
    }

    private static final class Attempt {
        private final long windowStart;
        private final AtomicInteger count;

        private Attempt(long windowStart) {
            this.windowStart = windowStart;
            this.count = new AtomicInteger(1);
        }
    }
}
