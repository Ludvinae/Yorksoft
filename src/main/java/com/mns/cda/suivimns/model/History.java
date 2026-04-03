package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.view.TicketStatusListView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TicketStatusListView.class)
    protected Integer idHistory;

    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime startDate;

    @UpdateTimestamp
    protected LocalDateTime endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status")
    @JsonView(TicketStatusListView.class)
    protected Status status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ticket")
    protected Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_app_user")
    protected AppUser actor;
}
