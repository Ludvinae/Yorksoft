package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActiveTime {

    private final HistoryDao historyDao;

    public Integer getActiveTimeInSeconds(Integer idTicket) {

        List<History> historyList= historyDao.findAllByTicketIdTicket(idTicket);

        int activeTimeInSeconds = 0;

        for (History history:historyList){
            if (history.getEndDate() != null){
                activeTimeInSeconds += history.getEndDate().compareTo(history.getStartDate());
            }

        }
        return activeTimeInSeconds;
    }

}
