package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.dto.ClassificationDto;
import com.mns.cda.suivimns.mapper.ClassificationMapper;
import com.mns.cda.suivimns.mapper.ClassificationMapper;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassificationService {


    public static class ClassificationNotFoundException extends Exception {
    }

    // NEED REWORKING TO ACCOUNT FOR ID FOR BOTH LINKED TABLES

    protected final ClassificationDao classificationDao;
    protected final ClassificationMapper classificationMapper;

    public List<ClassificationDto> findAll() {
        return classificationMapper.toDtoList(classificationDao.findAll());
    }

    public ClassificationDto findById(int idTicket, int idTheme) throws ClassificationService.ClassificationNotFoundException {
        ClassificationKey key = new ClassificationKey(idTicket, idTheme);

        Classification classification = classificationDao.findById(key)
                .orElseThrow(ClassificationNotFoundException::new);

        return classificationMapper.toDto(classification);
    }

    /*
    public ClassificationDto save(ClassificationDto dto) {
        Classification classification = classificationMapper.toEntity(dto);
        classification.setIdClassification(null);
        Classification saved = classificationDao.save(classification);

        return classificationMapper.toDto(saved);
    }

     */

    public Theme getTheme(Integer ticketId) {
        Optional<Classification> classification = classificationDao.findLatestByTicket(ticketId);
        if (classification.isEmpty()) {
            return null;
        }
        return classification.get().getTheme();
    }

}
