package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.UrgencyDao;
import com.mns.cda.suivimns.dto.entity.UrgencyDto;
import com.mns.cda.suivimns.exception.UrgencyNotFoundException;
import com.mns.cda.suivimns.mapper.entity.UrgencyMapper;
import com.mns.cda.suivimns.model.Urgency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrgencyService  {

    protected final UrgencyDao urgencyDao;
    protected final UrgencyMapper urgencyMapper;

    public List<UrgencyDto> findAll() {
        return urgencyMapper.toDtoList(urgencyDao.findAll());
    }

    public UrgencyDto findById(int id) {
        Urgency urgency = urgencyDao.findById(id)
                .orElseThrow(UrgencyNotFoundException::new);

        return urgencyMapper.toDto(urgency);
    }

    public UrgencyDto save(UrgencyDto dto) {
        Urgency urgency = urgencyMapper.toEntity(dto);
        urgency.setIdUrgency(null);
        Urgency saved = urgencyDao.save(urgency);

        return urgencyMapper.toDto(saved);
    }

    public void delete(int id) {
        Urgency urgency = urgencyDao.findById(id)
                .orElseThrow(UrgencyNotFoundException::new);

        urgencyDao.delete(urgency);
    }

    public UrgencyDto update(int id, UrgencyDto urgencyToUpdate) {

        Urgency currentUrgency = urgencyDao.findById(id)
                .orElseThrow(UrgencyNotFoundException::new);

        urgencyMapper.updateEntityFromDto(urgencyToUpdate, currentUrgency);

        return urgencyMapper.toDto(urgencyDao.save(currentUrgency));
    }
}
