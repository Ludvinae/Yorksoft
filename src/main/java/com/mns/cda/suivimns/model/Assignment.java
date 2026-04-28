package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @NotNull
    protected Ticket ticket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_manager")
    @NotNull
    protected Manager manager;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_technician")
    @NotNull
    protected Technician technician;
}
