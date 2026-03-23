package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idAssignment;

    @CreationTimestamp
    //@Column(nullable = false)
    //@NotBlank(groups = {OnCreate.class})
    protected LocalDateTime assigmentDate;

    protected LocalDateTime endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ticket")
    protected Ticket ticket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_manager")
    protected Employee manager;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_technician")
    protected Employee technician;
}
