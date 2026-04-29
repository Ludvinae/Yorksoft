package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.dto.ImpactDto;
import com.mns.cda.suivimns.mapper.ImpactMapper;
import com.mns.cda.suivimns.model.Impact;
import com.mns.cda.suivimns.model.Impact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImpactService  {

    public static class ImpactNotFoundException extends Exception {
    }

    protected final ImpactDao impactDao;
    protected final ImpactMapper impactMapper;

    public List<ImpactDto> findAll() {
        return impactMapper.toDtoList(impactDao.findAll());
    }

    public ImpactDto findById(int id) throws ImpactService.ImpactNotFoundException {
        Impact impact = impactDao.findById(id)
                .orElseThrow(ImpactService.ImpactNotFoundException::new);

        return impactMapper.toDto(impact);
    }

    public ImpactDto save(ImpactDto dto) {
        Impact impact = impactMapper.toEntity(dto);
        impact.setIdImpact(null);
        Impact saved = impactDao.save(impact);

        return impactMapper.toDto(saved);
    }

    public void delete(int id) throws ImpactService.ImpactNotFoundException {
        Impact impact = impactDao.findById(id)
                .orElseThrow(ImpactService.ImpactNotFoundException::new);

        impactDao.delete(impact);
    }

    public ImpactDto update(int id, ImpactDto impactToUpdate) throws ImpactService.ImpactNotFoundException {

        Impact currentImpact = impactDao.findById(id)
                .orElseThrow(ImpactService.ImpactNotFoundException::new);

        impactMapper.updateEntityFromDto(impactToUpdate, currentImpact);

        return impactMapper.toDto(impactDao.save(currentImpact));
    }
}
