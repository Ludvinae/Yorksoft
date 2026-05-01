package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    protected String designation;

    @Column(nullable = false)
    protected Byte priorityFactor;

    @Column(columnDefinition = "TEXT")
    protected String description;
}
