package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.HistoryDto;
import com.mns.cda.suivimns.model.History;

import java.util.List;

public interface HistoryMapper {
    HistoryDto toDto(History history);

    List<HistoryDto> toDtoList(List<History> historyList);

    History toEntity(HistoryDto dto);
}
