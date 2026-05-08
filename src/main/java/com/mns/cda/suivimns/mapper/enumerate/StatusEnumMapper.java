package com.mns.cda.suivimns.mapper.enumerate;

import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusEnumMapper {

    private final StatusDao statusDao;

    public StatusEnum entityToEnum(Status status) {
        return status.getCode();
    }

    public Status enumToEntity(StatusEnum status) throws StatusService.StatusNotFoundException {

        return statusDao.findByCode(status).orElseThrow(StatusService.StatusNotFoundException::new);
    }
}
