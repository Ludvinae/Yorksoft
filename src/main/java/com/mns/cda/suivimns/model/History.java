package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
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
    protected Integer idHistory;

    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime startDate;

    @UpdateTimestamp
    protected LocalDateTime endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status")
    protected Status status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ticket")
    protected Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_employee")
    protected Employee employee;
}
