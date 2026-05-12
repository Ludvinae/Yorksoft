package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dao.*;
import com.mns.cda.suivimns.dto.entity.TicketDto;
import com.mns.cda.suivimns.dto.search.TicketListDto;
import com.mns.cda.suivimns.dto.workflow.TicketCreationDto;
import com.mns.cda.suivimns.model.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TicketMapper {

    @Autowired
    protected VersionDao versionDao;

    @Autowired
    protected ClientDao clientDao;

    @Autowired
    protected ImpactDao impactDao;

    @Autowired
    protected UrgencyDao urgencyDao;

    @Autowired
    protected CommentDao commentDao;

    @Autowired
    protected AssignmentDao assignmentDao;

    @Autowired
    protected ThemeDao themeDao;

    @Autowired
    protected HistoryDao historyDao;

    @Mapping(target = "idVersion", source = "version")
    @Mapping(target = "idClient", source = "client")
    @Mapping(target = "idImpact", source = "impact")
    @Mapping(target = "idUrgency", source = "urgency")
    @Mapping(target = "commentIds", source = "commentList")
    @Mapping(target = "assignmentIds", source = "assignmentList")
    @Mapping(target = "themeIds", source = "classificationList")
    @Mapping(target = "historyIds", source = "historyList")
    public abstract TicketDto toDto(Ticket ticket);

    //@Mapping(target = "idTicketType", source = "ticketType")
    public abstract List<TicketDto> toDtoList(List<Ticket> ticket);


    @Mapping(target = "version", source = "idVersion")
    @Mapping(target = "client", source = "idClient")
    @Mapping(target = "impact", source = "idImpact")
    @Mapping(target = "urgency", source = "idUrgency")
    @Mapping(target = "commentList", source = "commentIds")
    @Mapping(target = "assignmentList", source = "assignmentIds")
    @Mapping(target = "classificationList", ignore = true)
    @Mapping(target = "historyList", source = "historyIds")
    public abstract Ticket toEntity(TicketDto dto);


    //Ticket creation
    @Mapping(target = "version", source = "idVersion")
    @Mapping(target = "client", source = "idClient")
    @Mapping(target = "impact", source = "idImpact")
    @Mapping(target = "urgency", source = "idUrgency")
    public abstract Ticket creationToEntity(TicketCreationDto dto);


    // Pagination and filters
    @Mapping(source="client.idAppUser", target="idClient")
    @Mapping(source="currentTechnician.idAppUser", target="idTechnician")
    public abstract TicketListDto toListDto(Ticket ticket);


    // Method helper pour ID vers ENTITE
    protected Version mapIdToVersion(Integer id) {
        return versionDao.getReferenceById(id);
    }

    protected Client mapIdToClient(Integer id) {
        return clientDao.getReferenceById(id);
    }

    protected Impact mapIdToImpact(Integer id) {
        return impactDao.getReferenceById(id);
    }

    protected Urgency mapIdTourgency(Integer id) {
        return urgencyDao.getReferenceById(id);
    }

    protected List<Comment> mapIdsToComments(List<Integer> ids) {
        if (ids == null) return null;

        return ids.stream()
                .map(commentDao::getReferenceById)
                .toList();
    }

    protected List<Assignment> mapIdsToAssignments(List<Integer> ids) {
        if (ids == null) return null;

        return ids.stream()
                .map(assignmentDao::getReferenceById)
                .toList();
    }

    protected List<Integer> mapClassificationsToThemeIds(List<Classification> classifications) {
        if (classifications == null) return List.of();

        return classifications.stream()
                .map(c -> c.getTheme().getIdTheme())
                .toList();
    }

    protected List<History> mapIdsToHistories(List<Integer> ids) {
        if (ids == null) return null;

        return ids.stream()
                .map(historyDao::getReferenceById)
                .toList();
    }

    // Method helper pour ENTITE vers ID
    protected Integer mapVersionToId(Version version) {
        return version != null ? version.getIdVersion() : null;
    }

    protected Integer mapClientToId(Client client) {
        return client != null ? client.getIdAppUser() : null;
    }

    protected Integer mapImpactToId(Impact impact) {
        return impact != null ? impact.getIdImpact() : null;
    }

    protected Integer mapUrgencyToId(Urgency urgency) {
        return urgency != null ? urgency.getIdUrgency() : null;
    }

    protected List<Integer> mapCommentsToIds(List<Comment> comments) {
        if (comments == null) return null;

        return comments.stream()
                .map(Comment::getIdComment)
                .toList();
    }

    protected List<Integer> mapAssignmentsToIds(List<Assignment> comments) {
        if (comments == null) return null;

        return comments.stream()
                .map(Assignment::getIdAssignment)
                .toList();
    }

    @AfterMapping
    protected void linkClassifications(TicketDto dto, @MappingTarget Ticket ticket) {
        if (dto.themeIds() == null || dto.themeIds().isEmpty()) {
            ticket.setClassificationList(List.of());
            return;
        }

        List<Classification> list = dto.themeIds().stream()
                .map(id -> {
                    Classification c = new Classification();
                    c.setTicket(ticket);
                    c.setTheme(themeDao.getReferenceById(id));
                    return c;
                })
                .toList();

        ticket.setClassificationList(list);
    }

    protected List<Integer> mapHistoriesToIds(List<History> histories) {
        if (histories == null) return null;

        return histories.stream()
                .map(History::getIdHistory)
                .toList();
    }

    // Method helper pour Update
    @Mapping(target = "idTicket", ignore = true)
    @Mapping(target = "version", source = "idVersion")
    @Mapping(target = "client", source = "idClient")
    @Mapping(target = "impact", source = "idImpact")
    @Mapping(target = "urgency", source = "idUrgency")
    @Mapping(target = "historyList", ignore = true)
    @Mapping(target = "classificationList", ignore = true)
    @Mapping(target = "commentList", ignore = true)
    @Mapping(target = "assignmentList", ignore = true)
    public abstract void updateEntityFromDto(TicketDto dto, @MappingTarget Ticket entity);
}
