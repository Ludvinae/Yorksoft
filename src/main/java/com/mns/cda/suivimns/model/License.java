package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idLicense;

    @Column(nullable = false, unique = true)
    @NotBlank
    protected String licenseNumber;

    protected LocalDate expirationDate;

    @Column(nullable = false)
    @NotBlank
    protected Integer userCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_software")
    protected Software software;

    @ManyToOne
    @JoinColumn(name = "id_organisation")
    protected Organisation organisation;

    @ManyToOne
    @JoinColumn(name = "id_client")
    protected Client client;
}
