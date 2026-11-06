package com.mns.cda.suivimns.security;

import com.mns.cda.suivimns.exception.TrialRateLimitException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Anti-flooding limits for trial accounts only (20 tickets and 20 comments per hour by default).
 * Real accounts are never limited.
 */
@Component
public class TrialActionLimiter {

    private static final long WINDOW_SECONDS = 3600;

    public enum Action { TICKET, COMMENT }

    private final FixedWindowCounter ticketCounter;
    private final FixedWindowCounter commentCounter;

    public TrialActionLimiter(@Value("${app.demo.max-tickets-per-hour:20}") int maxTickets,
                              @Value("${app.demo.max-comments-per-hour:20}") int maxComments) {
        this.ticketCounter = new FixedWindowCounter(maxTickets, WINDOW_SECONDS);
        this.commentCounter = new FixedWindowCounter(maxComments, WINDOW_SECONDS);
    }

    /**
     * Counts one action for a trial account, or throws TrialRateLimitException (429) once its limit is reached.
     */
    public void check(AppUserDetails user, Action action) {
        if (!user.isTrialTechnician()) {
            return;
        }
        FixedWindowCounter counter = action == Action.TICKET ? ticketCounter : commentCounter;
        String key = String.valueOf(user.getId());
        if (!counter.tryAcquire(key)) {
            throw new TrialRateLimitException(counter.secondsUntilReset(key));
        }
    }
}
