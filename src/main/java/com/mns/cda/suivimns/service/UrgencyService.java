package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.UrgencyDao;
import com.mns.cda.suivimns.dto.UrgencyDto;
import com.mns.cda.suivimns.mapper.UrgencyMapper;
import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.model.Urgency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrgencyService  {

    public static class UrgencyNotFoundException extends Exception {
    }

    protected final UrgencyDao urgencyDao;
    protected final UrgencyMapper urgencyMapper;

    public List<UrgencyDto> findAll() {
        return urgencyMapper.toDtoList(urgencyDao.findAll());
    }

    public UrgencyDto findById(int id) throws UrgencyNotFoundException {
        Urgency urgency = urgencyDao.findById(id)
                .orElseThrow(UrgencyService.UrgencyNotFoundException::new);

        return urgencyMapper.toDto(urgency);
    }

    public UrgencyDto save(UrgencyDto dto) {
        Urgency urgency = urgencyMapper.toEntity(dto);
        urgency.setIdUrgency(null);
        Urgency saved = urgencyDao.save(urgency);

        return urgencyMapper.toDto(saved);
    }

    public void delete(int id) throws UrgencyNotFoundException {
        Urgency urgency = urgencyDao.findById(id)
                .orElseThrow(UrgencyService.UrgencyNotFoundException::new);

        urgencyDao.delete(urgency);
    }

    public UrgencyDto update(int id, UrgencyDto urgencyToUpdate) throws UrgencyNotFoundException {
        Urgency currentUrgency = urgencyDao.findById(id)
                .orElseThrow(UrgencyService.UrgencyNotFoundException::new);

        urgencyMapper.updateEntityFromDto(urgencyToUpdate, currentUrgency);

        return urgencyMapper.toDto(urgencyDao.save(currentUrgency));
    }
}
