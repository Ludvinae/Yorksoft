package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.IncoherentHistoryTimeException;
import com.mns.cda.suivimns.model.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActiveTimeService {

    private final HistoryDao historyDao;
    private final Clock clock;

    public Long getActiveTimeInSeconds(Integer idTicket, List<StatusEnum> statuses) {
        List<History> historyList= historyDao.findAllActiveByIdTicket(idTicket, statuses);
        long activeTimeInSeconds = 0;

        for (History history:historyList){
            LocalDateTime start = history.getStartDate();
            LocalDateTime end = history.getEndDate() != null
                    ? history.getEndDate()
                    : LocalDateTime.now(clock);

            if (end.isBefore(start)) {throw new IncoherentHistoryTimeException();}

            activeTimeInSeconds += Duration.between(start, end).getSeconds();
        }
        return activeTimeInSeconds;
    }

}
