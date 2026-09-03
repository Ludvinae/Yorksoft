package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.exception.ThemeNotFoundException;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.business.TicketClassificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketClassificationServiceUnitTest {

    @Mock
    private ClassificationDao classificationDao;

    @Mock
    private ThemeDao themeDao;

    @InjectMocks
    private TicketClassificationService service;

    // =========================================================
    // classify
    // =========================================================

    @Test
    void classify_shouldCreateClassificationAndUpdateTicketTheme() {

        Ticket ticket = new Ticket();

        Theme theme = new Theme();
        theme.setCode("BUG");

        when(themeDao.findByCode("BUG"))
                .thenReturn(Optional.of(theme));

        service.classify(ticket, "BUG");

        ArgumentCaptor<Classification> captor =
                ArgumentCaptor.forClass(Classification.class);

        verify(classificationDao).save(captor.capture());

        Classification savedClassification = captor.getValue();

        assertEquals(ticket, savedClassification.getTicket());
        assertEquals(theme, savedClassification.getTheme());

        assertEquals(
                theme,
                ticket.getCurrentTheme()
        );
    }

    @Test
    void classify_shouldDoNothingIfThemeAlreadyAssigned() {

        Ticket ticket = new Ticket();
        Theme theme = new Theme();
        theme.setCode("BUG");
        theme.setIdTheme(1);
        ticket.setCurrentTheme(theme);

        when(themeDao.findByCode("BUG"))
                .thenReturn(Optional.of(theme));

        service.classify(ticket, "BUG");

        verify(classificationDao, never()).save(any());
    }

    @Test
    void classify_shouldThrowIfThemeDoesNotExist() {

        Ticket ticket = new Ticket();

        when(themeDao.findByCode("BUG"))
                .thenReturn(Optional.empty());

        assertThrows(
                ThemeNotFoundException.class,
                () -> service.classify(ticket, "BUG")
        );

        verify(classificationDao, never()).save(any());
    }

    @Test
    void classify_shouldUpdateCurrentTheme() {

        Ticket ticket = new Ticket();
        Theme theme = new Theme();
        theme.setIdTheme(1);
        theme.setCode("BUG");
        ticket.setCurrentTheme(theme);

        when(themeDao.findByCode("BUG"))
                .thenReturn(Optional.of(theme));

        service.classify(ticket, "BUG");

        assertEquals(
                "BUG",
                ticket.getCurrentTheme().getCode()
        );
    }

    @Test
    void classify_shouldSaveOnlyOneClassification() {

        Ticket ticket = new Ticket();

        Theme theme = new Theme();
        theme.setCode("BUG");

        when(themeDao.findByCode("BUG"))
                .thenReturn(Optional.of(theme));

        service.classify(ticket, "BUG");

        verify(classificationDao, times(1))
                .save(any(Classification.class));
    }
}
