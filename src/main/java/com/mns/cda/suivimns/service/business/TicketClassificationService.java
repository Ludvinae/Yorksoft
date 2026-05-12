package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.enumerate.ThemeEnum;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.ThemeService;
import com.mns.cda.suivimns.service.workflow.TicketClosingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mns.cda.suivimns.service.workflow.TicketClosingService.isNotEditable;

/**
 * Service used when changing the theme of a Ticket
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TicketClassificationService {

    private final ClassificationDao classificationDao;
    private final ThemeDao themeDao;

    public void classify(Ticket ticket, ThemeEnum themeEnum) {

        if (isNotEditable(ticket)) {
            throw new TicketClosingService.TicketNotEditableException();
        }

        Theme theme = themeDao.findByCode(themeEnum)
                .orElseThrow(ThemeService.ThemeNotFoundException::new);

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
