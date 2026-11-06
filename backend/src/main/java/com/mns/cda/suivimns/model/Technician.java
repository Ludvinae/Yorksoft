package com.mns.cda.suivimns.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Technician extends AppUser{

    @Column(nullable = false)
    @NotNull
    protected Byte rank;

    // Set only on trial accounts created from the public demo; the account and everything
    // it created are purged once this date is past (see DemoAccountService).
    protected LocalDateTime demoExpiresAt;

    @OneToMany(mappedBy = "technician")
    protected List<Assignment> assignmentList;

    @OneToMany(mappedBy = "technician")
    protected List<Procedure> procedureList;
}
