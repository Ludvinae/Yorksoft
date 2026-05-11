package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StatusTransition {

    public static class IllegalStatusTransitionException extends RuntimeException {}

    public static boolean canTransition(StatusEnum current, StatusEnum next) {
        return switch (current) {

            case OPEN ->
                    Set.of(StatusEnum.ASSIGNED, StatusEnum.REJECTED).contains(next);

            case ASSIGNED ->
                    Set.of(StatusEnum.IN_PROGRESS, StatusEnum.REJECTED, StatusEnum.ASSIGNED).contains(next);

            case IN_PROGRESS ->
                    Set.of(StatusEnum.WAITING_CLIENT, StatusEnum.WAITING_THIRD_PARTY,
                            StatusEnum.SOLVED, StatusEnum.REJECTED, StatusEnum.ASSIGNED).contains(next);

            case WAITING_CLIENT, WAITING_THIRD_PARTY ->
                    Set.of(StatusEnum.IN_PROGRESS, StatusEnum.SOLVED).contains(next);

            case SOLVED ->
                    Set.of(StatusEnum.CLOSED, StatusEnum.IN_PROGRESS).contains(next);

            case CLOSED, REJECTED ->
                    false;
        };
    }

    public Set<StatusEnum> getAllowedTransitions(StatusEnum current) {
        Set<StatusEnum> allowedTransitions = new HashSet<>();

        for (StatusEnum status : StatusEnum.values()) {
            if (canTransition(current, status)) {
                allowedTransitions.add(status);
            }
        }
        return allowedTransitions;
    }
}
