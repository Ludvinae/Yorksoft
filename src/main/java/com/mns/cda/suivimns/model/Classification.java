package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import com.mns.cda.suivimns.view.TicketView;
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
//@IdClass(Classification.class)
public class Classification {


    @EmbeddedId
    @JsonView(TicketView.class)
    protected ClassificationKey id;

    @CreatedDate
    @JsonView(TicketView.class)
    protected LocalDateTime affectation_date;

    @ManyToOne
    @MapsId("idTicket")
    @JoinColumn(name = "id_ticket")
    protected Ticket ticket;

    @ManyToOne
    @MapsId("idTheme")
    @JoinColumn(name = "id_theme")
    @JsonView(TicketView.class)
    protected Theme theme;


}
