package com.mns.cda.suivimns.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Technician extends AppUser{

    @Column(nullable = false)
    @NotNull
    protected Byte rank;

    @OneToMany(mappedBy = "technician")
    protected List<Assignment> assignmentList;

    @OneToMany(mappedBy = "technician")
    protected List<Procedure> procedureList;
}
