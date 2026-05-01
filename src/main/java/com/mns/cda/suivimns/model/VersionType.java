package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class VersionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idVersionType;

    @Column(nullable = false, length = 127)
    protected String designation;

    protected Byte urgencyMalus;
}
