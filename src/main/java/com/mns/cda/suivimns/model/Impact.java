package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Impact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idImpact;

    @Column(nullable = false, length = 63, unique = true)
    protected String designation;

    @Column(nullable = false)
    protected Byte priorityFactor;

    @Column(columnDefinition = "TEXT")
    protected String description;

}