package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.dto.entity.ImpactDto;
import com.mns.cda.suivimns.exception.ImpactNotFoundException;
import com.mns.cda.suivimns.mapper.entity.ImpactMapper;
import com.mns.cda.suivimns.model.Impact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImpactService  {

    protected final ImpactDao impactDao;
    protected final ImpactMapper impactMapper;

    public List<ImpactDto> findAll() {
        return impactMapper.toDtoList(impactDao.findAll());
    }

    public ImpactDto findById(int id) {
        Impact impact = impactDao.findById(id)
                .orElseThrow(ImpactNotFoundException::new);

        return impactMapper.toDto(impact);
    }

    public ImpactDto save(ImpactDto dto) {
        Impact impact = impactMapper.toEntity(dto);
        impact.setIdImpact(null);
        Impact saved = impactDao.save(impact);

        return impactMapper.toDto(saved);
    }

    public void delete(int id) {
        Impact impact = impactDao.findById(id)
                .orElseThrow(ImpactNotFoundException::new);

        impactDao.delete(impact);
    }

    public ImpactDto update(int id, ImpactDto impactToUpdate) {

        Impact currentImpact = impactDao.findById(id)
                .orElseThrow(ImpactNotFoundException::new);

        impactMapper.updateEntityFromDto(impactToUpdate, currentImpact);

        return impactMapper.toDto(impactDao.save(currentImpact));
    }
}
