package com.mns.cda.suivimns.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Technician extends AppUser{

    @Column(nullable = false)
    @NotNull
    protected Byte rank;
}
