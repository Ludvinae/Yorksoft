package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.dto.entity.StatusDto;
import com.mns.cda.suivimns.exception.StatusNotFoundException;
import com.mns.cda.suivimns.mapper.entity.StatusMapper;
import com.mns.cda.suivimns.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {

    protected final StatusDao statusDao;
    protected final StatusMapper statusMapper;

    public List<StatusDto> findAll() {
        return statusMapper.toDtoList(statusDao.findAll());
    }

    public StatusDto findById(int id) throws StatusNotFoundException {
        Status status = statusDao.findById(id)
                .orElseThrow(StatusNotFoundException::new);

        return statusMapper.toDto(status);
    }

    public StatusDto save(StatusDto dto) {
        Status status = statusMapper.toEntity(dto);
        status.setIdStatus(null);
        Status saved = statusDao.save(status);

        return statusMapper.toDto(saved);
    }

    public void delete(int id) throws StatusNotFoundException {
        Status status = statusDao.findById(id)
                .orElseThrow(StatusNotFoundException::new);

        statusDao.delete(status);
    }

    public StatusDto update(int id, StatusDto statusToUpdate) throws StatusNotFoundException {

        Status currentStatus = statusDao.findById(id)
                .orElseThrow(StatusNotFoundException::new);

        statusMapper.updateEntityFromDto(statusToUpdate, currentStatus);

        return statusMapper.toDto(statusDao.save(currentStatus));
    }
}
