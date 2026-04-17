package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import com.mns.cda.suivimns.view.TicketView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @ManyToOne(optional = false)
    @MapsId("idTicket")
    @JoinColumn(nullable = false, name = "id_ticket")
    @NotNull
    protected Ticket ticket;

    @ManyToOne(optional = false)
    @MapsId("idTheme")
    @JoinColumn(nullable = false, name = "id_theme")
    @JsonView(TicketView.class)
    @NotNull
    protected Theme theme;


}
