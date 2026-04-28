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
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idHistory;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime startDate;

    protected LocalDateTime endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status", nullable = false)
    @NotNull
    protected Status status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    @NotNull
    protected Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_app_user")
    protected AppUser actor;
}
