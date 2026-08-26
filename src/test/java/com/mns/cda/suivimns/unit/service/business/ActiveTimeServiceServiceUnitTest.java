package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.service.business.ActiveTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActiveTimeServiceServiceUnitTest {


    @Mock
    private HistoryDao historyDao;

    private Clock clock;

    private ActiveTimeService service;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(
                Instant.parse("2026-05-11T12:00:00Z"),
                ZoneId.systemDefault()
        );
        service = new ActiveTimeService(historyDao, clock);
    }

    @Test
    void shouldCalculateTotalActiveTime() {

        History h1 = new History();
        h1.setStartDate(LocalDateTime.of(2026,5,11,10,0));
        h1.setEndDate(LocalDateTime.of(2026,5,11,10,10));

        History h2 = new History();
        h2.setStartDate(LocalDateTime.of(2026,5,11,11,0));
        h2.setEndDate(LocalDateTime.of(2026,5,11,11,20));

        when(historyDao.findAllActiveByIdTicket(any(), any()))
                .thenReturn(List.of(h1, h2));

        long result = service.getActiveTimeInSeconds(
                1,
                List.of(StatusEnum.OPEN)
        );

        assertEquals(1800, result);
    }

    @Test
    void shouldReturnZeroWhenNoHistoryExists() {

        History h1 = new History();
        h1.setStartDate(LocalDateTime.of(2026,5,11,10,0));
        h1.setEndDate(LocalDateTime.of(2026,5,11,10,10));

        History h2 = new History();
        h2.setStartDate(LocalDateTime.of(2026,5,11,11,0));
        h2.setEndDate(LocalDateTime.of(2026,5,11,11,20));

        when(historyDao.findAllActiveByIdTicket(any(), any()))
                .thenReturn(List.of());

        long result = service.getActiveTimeInSeconds(
                1,
                List.of(StatusEnum.OPEN)
        );

        assertEquals(0, result);
    }

    @Test
    void shouldUseCurrentTimeWhenEndDateIsNull() {

        History h1 = new History();
        h1.setStartDate(LocalDateTime.of(2026,5,11,13,50));
        h1.setEndDate(null);

        when(historyDao.findAllActiveByIdTicket(any(), any()))
                .thenReturn(List.of(h1));

        long result = service.getActiveTimeInSeconds(
                1,
                List.of(StatusEnum.OPEN)
        );

        assertEquals(600, result);
    }

    @Test
    void shouldReturnZeroForInstantaneousHistory() {

        History h1 = new History();
        h1.setStartDate(LocalDateTime.of(2026,5,11,10,10));
        h1.setEndDate(LocalDateTime.of(2026,5,11,10,10));

        when(historyDao.findAllActiveByIdTicket(any(), any()))
                .thenReturn(List.of(h1));

        long result = service.getActiveTimeInSeconds(
                1,
                List.of(StatusEnum.OPEN)
        );

        assertEquals(0, result);
    }
}
