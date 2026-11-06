package com.mns.cda.suivimns.unit.security;

import com.mns.cda.suivimns.security.DemoCreationLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DemoCreationLimiterUnitTest {

    private DemoCreationLimiter limiter;

    @BeforeEach
    void setUp() {
        limiter = new DemoCreationLimiter();
        ReflectionTestUtils.setField(limiter, "maxPerIp", 2);
        ReflectionTestUtils.setField(limiter, "windowSeconds", 60L);
    }

    @Test
    void shouldRefuseOnceTheIpReachedItsLimit() {
        assertTrue(limiter.tryAcquire("1.2.3.4"));
        assertTrue(limiter.tryAcquire("1.2.3.4"));
        assertFalse(limiter.tryAcquire("1.2.3.4"));
        assertTrue(limiter.secondsUntilReset("1.2.3.4") > 0);
    }

    @Test
    void shouldCountEachIpSeparately() {
        limiter.tryAcquire("1.2.3.4");
        limiter.tryAcquire("1.2.3.4");

        assertTrue(limiter.tryAcquire("5.6.7.8"));
    }

    @Test
    void shouldAllowAgainOnceTheWindowIsOver() {
        ReflectionTestUtils.setField(limiter, "windowSeconds", 0L);
        limiter.tryAcquire("1.2.3.4");
        limiter.tryAcquire("1.2.3.4");

        assertTrue(limiter.tryAcquire("1.2.3.4"));
    }
}
