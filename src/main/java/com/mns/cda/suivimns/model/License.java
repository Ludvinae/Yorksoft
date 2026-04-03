package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.view.ClientSoftwareListView;
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
    @JsonView(ClientSoftwareListView.class)
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
    @JsonView(ClientSoftwareListView.class)
    protected Software software;

    @ManyToOne
    @JoinColumn(name = "id_client")
    protected Client client;
}
