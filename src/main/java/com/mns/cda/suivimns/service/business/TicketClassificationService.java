package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Ticket;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service used when changing the theme of a Ticket
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TicketClassificationService {

    private final ClassificationDao classificationDao;

    public void classify(Ticket ticket, Theme theme) {

        if (theme.getCode().equals(ticket.getCurrentTheme())) {
            return;
        }

        Classification classification = new Classification();

        classification.setTicket(ticket);
        classification.setTheme(theme);

        classificationDao.save(classification);

        ticket.setCurrentTheme(theme.getCode());
    }

}
