package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
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
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idHistory;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime startDate;

    protected LocalDateTime endDate;

    protected String statusReason;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status", nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    protected Status status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    protected Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_app_user")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected AppUser actor;
}
