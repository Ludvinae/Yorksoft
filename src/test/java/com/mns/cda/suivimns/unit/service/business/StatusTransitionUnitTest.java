package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.service.business.StatusTransition;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StatusTransitionUnitTest {

    private final StatusTransition service = new StatusTransition();

    // +--------------------------
    // | canTransition
    // +--------------------------
    @Test
    void open_allowed_transitions() {
        assertTrue(service.canTransition(StatusEnum.OPEN, StatusEnum.ASSIGNED));
        assertTrue(service.canTransition(StatusEnum.OPEN, StatusEnum.REJECTED));

        assertFalse(service.canTransition(StatusEnum.OPEN, StatusEnum.IN_PROGRESS));
        assertFalse(service.canTransition(StatusEnum.OPEN, StatusEnum.CLOSED));
    }

    @Test
    void assigned_allowed_transitions() {
        assertTrue(service.canTransition(StatusEnum.ASSIGNED, StatusEnum.IN_PROGRESS));
        assertTrue(service.canTransition(StatusEnum.ASSIGNED, StatusEnum.REJECTED));
        assertTrue(service.canTransition(StatusEnum.ASSIGNED, StatusEnum.ASSIGNED));

        assertFalse(service.canTransition(StatusEnum.ASSIGNED, StatusEnum.CLOSED));
    }

    @Test
    void in_progress_allowed_transitions() {
        assertTrue(service.canTransition(StatusEnum.IN_PROGRESS, StatusEnum.SOLVED));
        assertTrue(service.canTransition(StatusEnum.IN_PROGRESS, StatusEnum.REJECTED));
        assertTrue(service.canTransition(StatusEnum.IN_PROGRESS, StatusEnum.ASSIGNED));
        assertTrue(service.canTransition(StatusEnum.IN_PROGRESS, StatusEnum.WAITING_CLIENT));
        assertTrue(service.canTransition(StatusEnum.IN_PROGRESS, StatusEnum.WAITING_THIRD_PARTY));

        assertFalse(service.canTransition(StatusEnum.IN_PROGRESS, StatusEnum.CLOSED));
    }

    @Test
    void closed_and_rejected_are_terminal() {
        for (StatusEnum next : StatusEnum.values()) {
            assertFalse(service.canTransition(StatusEnum.CLOSED, next));
            assertFalse(service.canTransition(StatusEnum.REJECTED, next));
        }
    }

    // +--------------------------
    // | getAllowedTransitions
    // +--------------------------
    @Test
    void getAllowedTransitionsFromOpen_shouldReturnAllowedSet() {
        assertEquals(
                Set.of(StatusEnum.ASSIGNED, StatusEnum.REJECTED),
                service.getAllowedTransitions(StatusEnum.OPEN)
        );
    }

    @Test
    void getAllowedTransitionsFromAssigned_shouldReturnAllowedSet() {
        assertEquals(
                Set.of(StatusEnum.IN_PROGRESS, StatusEnum.REJECTED, StatusEnum.ASSIGNED),
                service.getAllowedTransitions(StatusEnum.ASSIGNED)
        );
    }

    @Test
    void getAllowedTransitionsFromClosed_shouldReturnEmptySet() {
        assertTrue(service.getAllowedTransitions(StatusEnum.CLOSED).isEmpty());
    }


    @Test
    void all_statuses_are_covered() {
        for (StatusEnum status : StatusEnum.values()) {
            Set<StatusEnum> transitions = service.getAllowedTransitions(status);

            if (status == StatusEnum.CLOSED || status == StatusEnum.REJECTED) {
                assertTrue(transitions.isEmpty());
            } else {
                assertNotNull(transitions);
            }
        }
    }
}


