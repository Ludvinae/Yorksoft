package com.mns.cda.suivimns.unit.security;

import com.mns.cda.suivimns.security.FixedWindowCounter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FixedWindowCounterUnitTest {

    @Test
    void shouldRefuseOnceTheKeyReachedItsLimit() {
        FixedWindowCounter counter = new FixedWindowCounter(2, 60);

        assertTrue(counter.tryAcquire("1.2.3.4"));
        assertTrue(counter.tryAcquire("1.2.3.4"));
        assertFalse(counter.tryAcquire("1.2.3.4"));
        assertTrue(counter.secondsUntilReset("1.2.3.4") > 0);
    }

    @Test
    void shouldCountEachKeySeparately() {
        FixedWindowCounter counter = new FixedWindowCounter(2, 60);
        counter.tryAcquire("1.2.3.4");
        counter.tryAcquire("1.2.3.4");

        assertTrue(counter.tryAcquire("5.6.7.8"));
    }

    @Test
    void shouldAllowAgainOnceTheWindowIsOver() {
        FixedWindowCounter counter = new FixedWindowCounter(2, 0);
        counter.tryAcquire("1.2.3.4");
        counter.tryAcquire("1.2.3.4");

        assertTrue(counter.tryAcquire("1.2.3.4"));
    }
}
