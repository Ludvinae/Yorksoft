package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.dto.entity.TechnicianDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.mapper.entity.TechnicianMapper;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.Technician;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TechnicianService  {

    // Classe d'erreur
    public static class TechnicianNotFoundException extends AppUserService.AppUserNotFoundException {}

    public static class BadPasswordException extends Exception {}


    protected final TechnicianDao technicianDao;
    protected final TechnicianMapper technicianMapper;
    protected final AppUserDao appUserDao;
    protected final PasswordEncoder encoder;

    public List<TechnicianDto> findAll() {
        return technicianMapper.toDtoList(technicianDao.findAll());
    }

    public TechnicianDto findById(int id) throws TechnicianService.TechnicianNotFoundException {
        Technician technician = technicianDao.findById(id)
                .orElseThrow(TechnicianService.TechnicianNotFoundException::new);

        return technicianMapper.toDto(technician);
    }

    public TechnicianDto save(TechnicianDto dto) {
        Technician technician = technicianMapper.toEntity(dto);
        technician.setIdAppUser(null);
        Technician saved = technicianDao.save(technician);

        return technicianMapper.toDto(saved);
    }

    public void insert(Technician technician) {
        technician.setIdAppUser(null);

        // Encodage du password avant de l'inserer en base de données
        technician.setPassword(encoder.encode(technician.getPassword()));

        appUserDao.save(technician);
    }

    public void delete(int id) throws TechnicianService.TechnicianNotFoundException {
        Technician technician = technicianDao.findById(id)
                .orElseThrow(TechnicianService.TechnicianNotFoundException::new);

        technicianDao.delete(technician);
    }

    public TechnicianDto update(int id, TechnicianDto dto)
            throws TechnicianService.TechnicianNotFoundException, AppUserService.EmailAlreadyUsedException {

        if (appUserDao.existsByEmail(dto.email())) {
            throw new AppUserService.EmailAlreadyUsedException();
        }

        Technician currentTechnician = technicianDao.findById(id)
                .orElseThrow(TechnicianService.TechnicianNotFoundException::new);

        technicianMapper.updateEntityFromDto(dto, currentTechnician);

        return technicianMapper.toDto(technicianDao.save(currentTechnician));
    }

    public void updatePassword(int id, PasswordDto dto)
            throws TechnicianService.TechnicianNotFoundException, TechnicianService.BadPasswordException {

        Technician user = technicianDao.findById(id)
                .orElseThrow(TechnicianService.TechnicianNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new TechnicianService.BadPasswordException();
        }

        user.setPassword(dto.newPassword());

        technicianDao.save(user);
    }
}
