package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(Classification.class)
public class Classification {

    @Id
    protected Integer id_ticket;

    @Id
    protected Integer id_theme;

    @ManyToOne
    @MapsId("id_ticket")
    @JoinColumn(name = "id_ticket")
    protected Ticket ticket;

    @ManyToOne
    @MapsId("id_theme")
    @JoinColumn(name = "id_theme")
    protected Theme theme;


    protected LocalDateTime affectation_date;

}
