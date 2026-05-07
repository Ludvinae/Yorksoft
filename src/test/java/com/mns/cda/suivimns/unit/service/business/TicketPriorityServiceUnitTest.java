package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.enumerate.PriorityEnum;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.service.business.PriorityCalculator;
import com.mns.cda.suivimns.service.business.TicketPriorityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketPriorityServiceUnitTest {

    @Mock
    private PriorityCalculator calculator;

    @InjectMocks
    private TicketPriorityService service;

    /*
    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        Impact impact = new Impact(1, "Test impact", (byte) 2,
                "Test description");
        Urgency urgency = new Urgency(1, "Test urgency", (byte) 1,
                "Test description");
        Client client = new Client((byte) 1);
        Version version = new Version(1, "Test number", LocalDate.now(),
                new VersionType(1, "Test type", (byte) 0), new Software());
        ticket = new Ticket(1, "Test title", LocalDateTime.now(), null, LocalDateTime.now(),
                "Test description", null, null, null,
                version, urgency, impact, client, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
    }

     */

    private Ticket mockTicket() {
        Ticket ticket = mock(Ticket.class);
        Impact impact = mock(Impact.class);
        Urgency urgency = mock(Urgency.class);
        Client client = mock(Client.class);
        Version version = mock(Version.class);
        VersionType versionType = mock(VersionType.class);

        when(ticket.getImpact()).thenReturn(impact);
        when(ticket.getUrgency()).thenReturn(urgency);
        when(ticket.getClient()).thenReturn(client);
        when(ticket.getVersion()).thenReturn(version);

        when(version.getVersionType()).thenReturn(versionType);

        when(impact.getPriorityFactor()).thenReturn((byte) 2);
        when(urgency.getPriorityFactor()).thenReturn((byte) 1);
        when(client.getImportance()).thenReturn((byte) 1);
        when(versionType.getUrgencyMalus()).thenReturn((byte) 0);

        return ticket;
    }


    @Test
    void initializePriority_shouldSetInitialAndCurrentPriority() {

        Ticket ticket = mockTicket();

        when(calculator.computePriority(2, 1, 1, 0))
                .thenReturn(PriorityEnum.MEDIUM);

        service.initializePriority(ticket);

        verify(ticket).setInitialPriority(PriorityEnum.MEDIUM);
        verify(ticket).setCurrentPriority(PriorityEnum.MEDIUM);
    }

    @Test
    void initializePriority_shouldThrowException_ifAlreadyInitialized() {
        Ticket ticket = mock(Ticket.class);

        when(ticket.getInitialPriority()).thenReturn(PriorityEnum.LOW);

        assertThrows(IllegalStateException.class, () ->
                service.initializePriority(ticket)
        );
    }

    @Test
    void recalculateCurrentPriority_shouldUpdateOnlyCurrentPriority() {

        Ticket ticket = mockTicket();

        when(calculator.computePriority(2, 1, 1, 0))
                .thenReturn(PriorityEnum.HIGH);

        service.recalculateCurrentPriority(ticket);

        verify(ticket).setCurrentPriority(PriorityEnum.HIGH);
        verify(ticket, never()).setInitialPriority(any());
    }


}
