package com.mns.cda.suivimns.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Technician extends AppUser{

    @Column(nullable = false)
    @NotBlank
    protected byte rank;
}
