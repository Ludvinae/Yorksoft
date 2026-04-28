package com.mns.cda.suivimns.unit.service;

import com.mns.cda.suivimns.dao.VersionTypeDao;
import com.mns.cda.suivimns.dto.VersionTypeDto;
import com.mns.cda.suivimns.mapper.VersionTypeMapper;
import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.service.VersionTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VersionTypeServiceUnitTest {

    @Mock
    private VersionTypeDao versionTypeDao;

    @Mock
    private VersionTypeMapper versionTypeMapper;

    @InjectMocks
    private VersionTypeService versionTypeService;

    @Test
    void shouldReturnAll() {

        List<VersionType> entities = List.of(new VersionType());
        List<VersionTypeDto> dtos = List.of(new VersionTypeDto(1, "Test", (byte) 1));

        when(versionTypeDao.findAll()).thenReturn(entities);
        when(versionTypeMapper.toDtoList(entities)).thenReturn(dtos);

        List<VersionTypeDto> result = versionTypeService.findAll();

        assertEquals(1, result.size());
        verify(versionTypeDao).findAll();
        verify(versionTypeMapper).toDtoList(entities);
    }

    @Test
    void shouldReturnById() throws Exception {

        VersionType entity = new VersionType();
        VersionTypeDto dto = new VersionTypeDto(1, "Test", (byte) 1);

        when(versionTypeDao.findById(1)).thenReturn(Optional.of(entity));
        when(versionTypeMapper.toDto(entity)).thenReturn(dto);

        VersionTypeDto result = versionTypeService.findById(1);

        assertEquals("Test", result.designation());
    }

    @Test
    void shouldThrowWhenNotFound() {

        when(versionTypeDao.findById(1)).thenReturn(Optional.empty());

        assertThrows(VersionTypeService.VersionTypeNotFoundException.class,
                () -> versionTypeService.findById(1));
    }

    @Test
    void shouldSave() {

        VersionTypeDto createDto = new VersionTypeDto(null,"Test", (byte) 1);
        VersionType entity = new VersionType();
        VersionType saved = new VersionType();
        VersionTypeDto responseDto = new VersionTypeDto(1, "Test", (byte) 1);

        when(versionTypeMapper.toEntity(createDto)).thenReturn(entity);
        when(versionTypeDao.save(entity)).thenReturn(saved);
        when(versionTypeMapper.toDto(saved)).thenReturn(responseDto);

        VersionTypeDto result = versionTypeService.save(createDto);

        assertEquals(1, result.idVersionType());
        verify(versionTypeDao).save(entity);
    }

    @Test
    void shouldDelete() throws Exception {

        VersionType entity = new VersionType();

        when(versionTypeDao.findById(1)).thenReturn(Optional.of(entity));

        versionTypeService.delete(1);

        verify(versionTypeDao).delete(entity);
    }

    @Test
    void shouldThrowWhenDeleteNotFound() {

        when(versionTypeDao.findById(1)).thenReturn(Optional.empty());

        assertThrows(VersionTypeService.VersionTypeNotFoundException.class,
                () -> versionTypeService.delete(1));
    }

}
