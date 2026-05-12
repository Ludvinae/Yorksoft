package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.enumerate.ThemeEnum;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.ThemeService;
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
        theme.setCode(ThemeEnum.BUG);

        when(themeDao.findByCode(ThemeEnum.BUG))
                .thenReturn(Optional.of(theme));

        service.classify(ticket, ThemeEnum.BUG);

        ArgumentCaptor<Classification> captor =
                ArgumentCaptor.forClass(Classification.class);

        verify(classificationDao).save(captor.capture());

        Classification savedClassification = captor.getValue();

        assertEquals(ticket, savedClassification.getTicket());
        assertEquals(theme, savedClassification.getTheme());

        assertEquals(
                ThemeEnum.BUG,
                ticket.getCurrentTheme()
        );
    }

    @Test
    void classify_shouldDoNothingIfThemeAlreadyAssigned() {

        Ticket ticket = new Ticket();
        ticket.setCurrentTheme(ThemeEnum.BUG);

        Theme theme = new Theme();
        theme.setCode(ThemeEnum.BUG);

        when(themeDao.findByCode(ThemeEnum.BUG))
                .thenReturn(Optional.of(theme));

        service.classify(ticket, ThemeEnum.BUG);

        verify(classificationDao, never()).save(any());
    }

    @Test
    void classify_shouldThrowIfThemeDoesNotExist() {

        Ticket ticket = new Ticket();

        when(themeDao.findByCode(ThemeEnum.BUG))
                .thenReturn(Optional.empty());

        assertThrows(
                ThemeService.ThemeNotFoundException.class,
                () -> service.classify(ticket, ThemeEnum.BUG)
        );

        verify(classificationDao, never()).save(any());
    }

    @Test
    void classify_shouldUpdateCurrentTheme() {

        Ticket ticket = new Ticket();
        ticket.setCurrentTheme(ThemeEnum.SYSTEM_ERROR);

        Theme theme = new Theme();
        theme.setCode(ThemeEnum.BUG);

        when(themeDao.findByCode(ThemeEnum.BUG))
                .thenReturn(Optional.of(theme));

        service.classify(ticket, ThemeEnum.BUG);

        assertEquals(
                ThemeEnum.BUG,
                ticket.getCurrentTheme()
        );
    }

    @Test
    void classify_shouldSaveOnlyOneClassification() {

        Ticket ticket = new Ticket();

        Theme theme = new Theme();
        theme.setCode(ThemeEnum.BUG);

        when(themeDao.findByCode(ThemeEnum.BUG))
                .thenReturn(Optional.of(theme));

        service.classify(ticket, ThemeEnum.BUG);

        verify(classificationDao, times(1))
                .save(any(Classification.class));
    }
}
