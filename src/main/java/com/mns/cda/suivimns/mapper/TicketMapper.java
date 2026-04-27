package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.TicketDto;
import com.mns.cda.suivimns.model.Ticket;

import java.util.List;

public interface TicketMapper {
    TicketDto toDto(Ticket ticket);

    List<TicketDto> toDtoList(List<Ticket> ticketList);

    Ticket toEntity(TicketDto dto);
}
