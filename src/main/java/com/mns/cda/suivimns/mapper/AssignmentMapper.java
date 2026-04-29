package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dao.ManagerDao;
import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.AssignmentDto;
import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AssignmentMapper {

    @Autowired
    protected TicketDao ticketDao;

    @Autowired
    protected ManagerDao managerDao;

    @Autowired
    protected TechnicianDao technicianDao;

    @Mapping(target = "idTicket", source = "ticket")
    @Mapping(target = "idManager", source = "manager")
    @Mapping(target = "idTechnician", source = "technician")
    public abstract AssignmentDto toDto(Assignment assignment);

    //@Mapping(target = "idAssignmentType", source = "assignmentType")
    public abstract List<AssignmentDto> toDtoList(List<Assignment> assignment);

    @Mapping(target="ticket", source="idTicket")
    @Mapping(target="manager", source="idManager")
    @Mapping(target="technician", source="idTechnician")
    public abstract Assignment toEntity(AssignmentDto dto);


    // Method helper pour ID vers ENTITE
    protected Ticket mapIdToTicket(Integer id) {
        return ticketDao.getReferenceById(id);
    }

    protected Manager mapIdToManager(Integer id) {
        return managerDao.getReferenceById(id);
    }

    protected Technician mapIdToTechnician(Integer id) {
        return technicianDao.getReferenceById(id);
    }

    // Method helper pour ENTITE vers ID
    protected Integer mapTicketToId(Ticket ticket) {
        return ticket != null ? ticket.getIdTicket() : null;
    }

    protected Integer mapManagerToId(Manager manager) {
        return manager != null ? manager.getIdAppUser() : null;
    }

    protected Integer mapTechnicianToId(Technician technician) {
        return technician != null ? technician.getIdAppUser() : null;
    }

}
