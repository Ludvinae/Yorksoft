package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.exception.DemoUnavailableException;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.business.DemoAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemoAccountServiceUnitTest {

    @Mock private TechnicianDao technicianDao;
    @Mock private PasswordEncoder passwordEncoder;

    private final Clock clock = Clock.fixed(Instant.parse("2026-11-06T10:00:00Z"), ZoneOffset.UTC);
    private DemoAccountService service;

    @BeforeEach
    void setUp() {
        service = new DemoAccountService(technicianDao, passwordEncoder, clock);
        ReflectionTestUtils.setField(service, "ttlHours", 24);
        ReflectionTestUtils.setField(service, "maxActiveAccounts", 10);
    }

    @Test
    void shouldCreateRankZeroTechnicianExpiringAfterTtl() {
        when(technicianDao.countByDemoExpiresAtIsNotNull()).thenReturn(0L);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");
        when(technicianDao.save(any(Technician.class))).thenAnswer(inv -> {
            Technician technician = inv.getArgument(0);
            technician.setIdAppUser(42);
            return technician;
        });

        AppUserDetails details = service.createTrialTechnician();

        ArgumentCaptor<Technician> captor = ArgumentCaptor.forClass(Technician.class);
        verify(technicianDao).save(captor.capture());
        Technician created = captor.getValue();

        assertEquals((byte) 0, created.getRank());
        assertEquals(LocalDateTime.of(2026, 11, 7, 10, 0), created.getDemoExpiresAt());
        assertTrue(created.getEmail().endsWith("@demo.invalid"));
        assertEquals("hashed", created.getPassword());
        assertTrue(details.isTrialTechnician());
        assertEquals(42, details.getId());
    }

    @Test
    void shouldGiveEachTrialAccountItsOwnEmail() {
        when(technicianDao.countByDemoExpiresAtIsNotNull()).thenReturn(0L);
        when(technicianDao.save(any(Technician.class))).thenAnswer(inv -> {
            Technician technician = inv.getArgument(0);
            technician.setIdAppUser(1);
            return technician;
        });

        String first = service.createTrialTechnician().getEmail();
        String second = service.createTrialTechnician().getEmail();

        assertNotEquals(first, second);
    }

    @Test
    void shouldRefuseWhenTooManyTrialAccountsAreActive() {
        when(technicianDao.countByDemoExpiresAtIsNotNull()).thenReturn(10L);

        assertThrows(DemoUnavailableException.class, () -> service.createTrialTechnician());

        verify(technicianDao, never()).save(any());
    }

    @Test
    void purgeShouldDoNothingWhenNoAccountExpired() {
        when(technicianDao.findExpiredTrialAccountIds(any())).thenReturn(List.of());

        service.purgeExpiredAccounts();

        verify(technicianDao, never()).deleteAllById(any());
    }
}
