package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idAssignment;

    @CreatedDate
    protected LocalDateTime assignmentDate;

    protected LocalDateTime endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    protected Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_manager")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected Manager manager;

    @ManyToOne
    @JoinColumn(name = "id_technician")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected Technician technician;
}
