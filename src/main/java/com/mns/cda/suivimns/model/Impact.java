package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.view.TicketView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Impact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TicketView.class)
    protected Integer idImpact;

    @Column(nullable = false, length = 63, unique = true)
    @NotBlank(groups = {OnCreate.class})
    @Size(max = 63)
    @JsonView(TicketView.class)
    protected String designation;

    @JsonView(TicketView.class)
    protected Byte priorityFactor;

    @Column(columnDefinition = "TEXT")
    protected String description;

}