package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.dto.entity.TechnicianDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.dto.flat.TechnicianWorkloadDetailedDto;
import com.mns.cda.suivimns.exception.AccountNotOwnedException;
import com.mns.cda.suivimns.exception.BadPasswordException;
import com.mns.cda.suivimns.exception.EmailAlreadyUsedException;
import com.mns.cda.suivimns.exception.TechnicianNotFoundException;
import com.mns.cda.suivimns.mapper.entity.TechnicianMapper;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TechnicianService  {



    protected final TechnicianDao technicianDao;
    protected final TechnicianMapper technicianMapper;
    protected final AppUserDao appUserDao;
    protected final PasswordEncoder encoder;

    public List<TechnicianDto> findAll() {
        return technicianMapper.toDtoList(technicianDao.findAll());
    }

    public TechnicianDto findById(int id) {
        Technician technician = technicianDao.findById(id)
                .orElseThrow(TechnicianNotFoundException::new);

        return technicianMapper.toDto(technician);
    }

    public TechnicianDto save(TechnicianDto dto) {
        if (appUserDao.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        Technician technician = technicianMapper.toEntity(dto);
        technician.setIdAppUser(null);
        technician.setPhoneNumber(technician.getPhoneNumber().trim());

        Technician saved = technicianDao.save(technician);

        return technicianMapper.toDto(saved);
    }

    public void insert(Technician technician) {
        if (appUserDao.existsByEmail(technician.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        technician.setIdAppUser(null);
        technician.setPhoneNumber(technician.getPhoneNumber().trim());

        // Encodage du password avant de l'inserer en base de données
        technician.setPassword(encoder.encode(technician.getPassword()));

        appUserDao.save(technician);
    }

    public void delete(int id, AppUserDetails userDetails) {
        Technician technician = technicianDao.findById(id)
                .orElseThrow(TechnicianNotFoundException::new);

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        technicianDao.delete(technician);
    }

    public TechnicianDto update(int id, TechnicianDto dto, AppUserDetails userDetails) {

        if (appUserDao.existsByEmail(dto.email()) && !userDetails.getEmail().equals(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        Technician currentTechnician = technicianDao.findById(id)
                .orElseThrow(TechnicianNotFoundException::new);

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        technicianMapper.updateEntityFromDto(dto, currentTechnician);
        currentTechnician.setPhoneNumber(currentTechnician.getPhoneNumber().trim());

        return technicianMapper.toDto(technicianDao.save(currentTechnician));
    }

    public void updatePassword(int id, PasswordDto dto, AppUserDetails userDetails) {

        Technician user = technicianDao.findById(id)
                .orElseThrow(TechnicianNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new BadPasswordException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        user.setPassword(dto.newPassword());

        technicianDao.save(user);
    }

    public List<TechnicianWorkloadDetailedDto> getAllWorkload() {

        return technicianDao.getTechnicianWorkload();
    }
}
