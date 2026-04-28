package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.view.TicketView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Urgency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idUrgency;

    @Column(nullable = false, length = 63, unique = true)
    @NotBlank(groups = {OnCreate.class})
    @Size(max = 63)
    protected String designation;

    @Column(nullable = false)
    @NotNull
    protected Byte priorityFactor;

    @Column(columnDefinition = "TEXT")
    protected String description;
}
