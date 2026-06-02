package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.ProcedureDao;
import com.mns.cda.suivimns.dto.entity.ProcedureDto;
import com.mns.cda.suivimns.enumerate.ActivityType;
import com.mns.cda.suivimns.exception.AppUserNotFoundException;
import com.mns.cda.suivimns.exception.ProcedureNotFoundException;
import com.mns.cda.suivimns.exception.ProcedureNotOwnedException;
import com.mns.cda.suivimns.exception.UnauthorizedTechnicianException;
import com.mns.cda.suivimns.mapper.entity.ProcedureMapper;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Procedure;
import com.mns.cda.suivimns.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProcedureService {

    protected final ProcedureDao procedureDao;
    protected final ProcedureMapper procedureMapper;

    protected final AppUserDao appUserDao;
    protected final ActivityService activityService;

    public List<ProcedureDto> findAll() {
        return procedureMapper.toDtoList(procedureDao.findAll());
    }

    public ProcedureDto findById(int id) {
        Procedure procedure = procedureDao.findById(id)
                .orElseThrow(ProcedureNotFoundException::new);

        return procedureMapper.toDto(procedure);
    }

    public ProcedureDto save(ProcedureDto dto, AppUserDetails user) {
        if (user.getTechnician() == null) {
            throw new UnauthorizedTechnicianException();
        }

        Procedure procedure = procedureMapper.toEntity(dto);
        procedure.setIdProcedure(null);
        procedure.setTechnician(user.getTechnician());
        Procedure saved = procedureDao.save(procedure);

        AppUser author = appUserDao.findById(user.getId()).orElseThrow(AppUserNotFoundException::new);
        activityService.log(author,
                "A écrit un procedure à propos de la connaissance #" + dto.idKnowledge(),
                ActivityType.PROCEDURE);

        return procedureMapper.toDto(saved);
    }

    public void delete(int id, AppUserDetails user) {
        Procedure procedure = procedureDao.findById(id)
                .orElseThrow(ProcedureNotFoundException::new);

        AppUser author = appUserDao.findById(user.getId()).orElseThrow(AppUserNotFoundException::new);
        activityService.log(author, "A effacé la procedure #" + id, ActivityType.PROCEDURE);

        procedureDao.delete(procedure);
    }

    public ProcedureDto update(int id, ProcedureDto procedureToUpdate, AppUserDetails userDetails) {

        Procedure currentProcedure = procedureDao.findById(id)
                .orElseThrow(ProcedureNotFoundException::new);

        // On verifie si l'utilisateur est admin ou s'il est le proprietaire de la ressource
        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                !currentProcedure.getTechnician().getIdAppUser().equals(userDetails.getId())) {
            throw new ProcedureNotOwnedException();
        }

        procedureMapper.updateEntityFromDto(procedureToUpdate, currentProcedure);

        AppUser author = appUserDao.findById(userDetails.getId()).orElseThrow(AppUserNotFoundException::new);
        activityService.log(author, "A édité la procedure #" + id, ActivityType.PROCEDURE);

        return procedureMapper.toDto(procedureDao.save(currentProcedure));
    }
}
