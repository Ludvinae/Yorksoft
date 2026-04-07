package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {

    public static class HistoryNotFoundException extends Exception {}

    protected final HistoryDao historyDao;

    public List<History> findAll() {
        return historyDao.findAll();
    }

    public Optional<History> findById(int id) {
        return historyDao.findById(id);
    }

    public void save(History history) {
        history.setIdHistory(null);
        historyDao.save(history);
    }

    public void delete(History history) {
        historyDao.delete(history);
    }

    public void update(History historyToUpdate, int id) throws HistoryService.HistoryNotFoundException {
        Optional<History> history = historyDao.findById(id);

        if (history.isEmpty()) {
            throw new HistoryService.HistoryNotFoundException();
        }

        historyToUpdate.setIdHistory(history.get().getIdHistory());

        historyDao.save(historyToUpdate);
    }
}
