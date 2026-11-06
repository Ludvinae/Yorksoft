package com.mns.cda.suivimns.unit.security;

import com.mns.cda.suivimns.exception.TrialRateLimitException;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.security.TrialActionLimiter;
import com.mns.cda.suivimns.security.TrialActionLimiter.Action;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrialActionLimiterUnitTest {

    private final TrialActionLimiter limiter = new TrialActionLimiter(2, 2);

    @Test
    void shouldBlockTrialAccountOnceItReachedTheLimit() {
        AppUserDetails trial = user(1, (byte) 0);
        limiter.check(trial, Action.TICKET);
        limiter.check(trial, Action.TICKET);

        TrialRateLimitException ex = assertThrows(TrialRateLimitException.class,
                () -> limiter.check(trial, Action.TICKET));
        assertTrue(ex.getRetryAfterSeconds() > 0);
    }

    @Test
    void shouldCountTicketsAndCommentsSeparately() {
        AppUserDetails trial = user(1, (byte) 0);
        limiter.check(trial, Action.TICKET);
        limiter.check(trial, Action.TICKET);

        assertDoesNotThrow(() -> limiter.check(trial, Action.COMMENT));
    }

    @Test
    void shouldNeverLimitRealAccounts() {
        AppUserDetails technician = user(2, (byte) 1);

        assertDoesNotThrow(() -> {
            for (int i = 0; i < 10; i++) {
                limiter.check(technician, Action.COMMENT);
            }
        });
    }

    private static AppUserDetails user(int id, byte rank) {
        Technician technician = new Technician();
        technician.setIdAppUser(id);
        technician.setFirstName("First");
        technician.setLastName("Last");
        technician.setRank(rank);
        return new AppUserDetails(technician);
    }
}
