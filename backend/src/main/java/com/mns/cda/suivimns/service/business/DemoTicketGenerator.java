package com.mns.cda.suivimns.service.business;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Gives a new trial account a past of its own, so its technician dashboard isn't empty: tickets assigned to it
 * over the last weeks, most of them closed, a few still in progress for the visitor to work on.
 * <p>
 * Relies on the {@code demo_generate_tickets} SQL function created by {@code data-init.sql} (the same one that
 * generates the seeded technicians' history), so trial accounts need the demo dataset. The trial account opens
 * every generated ticket, which is what lets {@link DemoAccountService#purgeExpiredAccounts()} delete them.
 */
@Component
public class DemoTicketGenerator {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${app.demo.history-tickets:20}")
    private int ticketCount;

    @Value("${app.demo.history-days:45}")
    private int historyDays;

    /** Generates the history of a trial technician, returns the number of tickets created. */
    public int generateHistory(Integer technicianId) {
        Object created = entityManager.createNativeQuery("""
                        SELECT demo_generate_tickets(ARRAY[CAST(:id AS integer)], :count, :days, :salt,
                                                     CAST(:id AS integer))
                        """)
                .setParameter("id", technicianId)
                .setParameter("count", ticketCount)
                .setParameter("days", historyDays)
                .setParameter("salt", "trial-" + technicianId)
                .getSingleResult();
        return ((Number) created).intValue();
    }
}
