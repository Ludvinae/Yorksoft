package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActiveTime {

    private final HistoryDao historyDao;

    public Long getActiveTimeInSeconds(Integer idTicket, List<StatusEnum> statuses) {

        List<History> historyList= historyDao.findAllActiveByIdTicket(idTicket, statuses);

        long activeTimeInSeconds = 0;

        for (History history:historyList){
            LocalDateTime start = history.getStartDate();
            LocalDateTime end = history.getEndDate() != null
                    ? history.getEndDate()
                    : LocalDateTime.now();

            activeTimeInSeconds += Duration.between(start, end).getSeconds();
        }
        return activeTimeInSeconds;
    }

}
