package com.mns.cda.suivimns.unit.security;

import com.mns.cda.suivimns.security.LoginAttemptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginAttemptServiceUnitTest {

    private static final String KEY = "127.0.0.1:user@test.fr";

    private LoginAttemptService loginAttemptService;

    @BeforeEach
    void setUp() {
        loginAttemptService = new LoginAttemptService();
        ReflectionTestUtils.setField(loginAttemptService, "maxAttempts", 3);
        ReflectionTestUtils.setField(loginAttemptService, "windowSeconds", 60L);
    }

    @Test
    void shouldNotBeBlockedWhenNoAttemptRecorded() {
        assertFalse(loginAttemptService.isBlocked(KEY));
    }

    @Test
    void shouldNotBlockBeforeMaxAttemptsReached() {
        loginAttemptService.registerFailure(KEY);
        loginAttemptService.registerFailure(KEY);

        assertFalse(loginAttemptService.isBlocked(KEY));
    }

    @Test
    void shouldBlockOnceMaxAttemptsReached() {
        loginAttemptService.registerFailure(KEY);
        loginAttemptService.registerFailure(KEY);
        loginAttemptService.registerFailure(KEY);

        assertTrue(loginAttemptService.isBlocked(KEY));
    }

    @Test
    void shouldResetCounterOnSuccess() {
        loginAttemptService.registerFailure(KEY);
        loginAttemptService.registerFailure(KEY);
        loginAttemptService.registerFailure(KEY);
        assertTrue(loginAttemptService.isBlocked(KEY));

        loginAttemptService.registerSuccess(KEY);

        assertFalse(loginAttemptService.isBlocked(KEY));
    }

    @Test
    void shouldNotAffectOtherKeys() {
        loginAttemptService.registerFailure(KEY);
        loginAttemptService.registerFailure(KEY);
        loginAttemptService.registerFailure(KEY);
        assertTrue(loginAttemptService.isBlocked(KEY));

        assertFalse(loginAttemptService.isBlocked("10.0.0.1:other@test.fr"));
    }

    @Test
    void shouldUnblockOnceWindowExpires() {
        loginAttemptService.registerFailure(KEY);
        loginAttemptService.registerFailure(KEY);
        loginAttemptService.registerFailure(KEY);
        assertTrue(loginAttemptService.isBlocked(KEY));

        // Fenetre deja passee : on force le debut de fenetre dans le passe.
        ReflectionTestUtils.setField(loginAttemptService, "windowSeconds", 0L);

        assertFalse(loginAttemptService.isBlocked(KEY));
    }

    @Test
    void secondsUntilUnblockedShouldBeZeroWhenNoAttempt() {
        assertTrue(loginAttemptService.secondsUntilUnblocked(KEY) == 0);
    }
}
