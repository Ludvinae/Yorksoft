package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.dto.entity.HistoryDto;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.mapper.entity.HistoryMapper;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {


    public static class HistoryNotFoundException extends RuntimeException {
    }

    protected final HistoryDao historyDao;
    protected final StatusDao statusDao;
    protected final AppUserDao appUserDao;
    protected final HistoryMapper historyMapper;

    public List<HistoryDto> findAll() {
        return historyMapper.toDtoList(historyDao.findAll());
    }

    public HistoryDto findById(int id) throws HistoryService.HistoryNotFoundException {
        History history = historyDao.findById(id)
                .orElseThrow(HistoryService.HistoryNotFoundException::new);

        return historyMapper.toDto(history);
    }


    // METHODS
    public void addHistory(Ticket ticket, AppUser user, StatusEnum newStatus, String reason)
            throws StatusService.StatusNotFoundException {

        History history = new History();

        history.setTicket(ticket);
        history.setActor(user);
        history.setStatusReason(reason);
        history.setStatus(statusDao.findByCode(newStatus)
                .orElseThrow(StatusService.StatusNotFoundException::new));

        historyDao.save(history);
    }

    public void updateHistory(Ticket ticket, Integer actorId, String nextStatus) {

        Status status = statusDao.findByDesignation(nextStatus)
                .orElseThrow(() -> new RuntimeException("Statut introuvable"));

        AppUser actor;

        if (actorId == null) {
            actor = ticket.getClient();
        }
        else {
            actor = appUserDao.findById(actorId)
                    .orElseThrow(() -> new RuntimeException("User introuvable"));
        }

        if (ticket.getHistoryList() == null) {
            ticket.setHistoryList(new ArrayList<>());
        }


        Optional<History> previousHistory = historyDao.findLatestByTicket(ticket.getIdTicket());
        previousHistory.ifPresent(history -> history.setEndDate(LocalDateTime.now()));

        History history = new History(null, LocalDateTime.now(), null, null, status, ticket, actor);
        historyDao.save(history);

    }

}
