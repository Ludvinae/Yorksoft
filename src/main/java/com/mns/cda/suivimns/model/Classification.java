package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(Classification.class)
public class Classification {

    @Id
    protected Integer idTicket;

    @Id
    protected Integer idTheme;

    @CreatedDate
    protected LocalDateTime affectation_date;

    @ManyToOne
    @MapsId("idTicket")
    @JoinColumn(name = "id_ticket")
    protected Ticket ticket;

    @ManyToOne
    @MapsId("idTheme")
    @JoinColumn(name = "id_theme")
    protected Theme theme;


}
