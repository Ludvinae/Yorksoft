package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.view.ClientSoftwareListView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

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

    @Column(nullable = false, unique = true, length = 127)
    @NotBlank
    @Size(max = 127)
    protected String licenseNumber;

    protected LocalDate expirationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_software", nullable = false)
    @NotNull
    @JsonView(ClientSoftwareListView.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Software software;


    @ManyToOne
    @JoinTable(name = "license_clients")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    protected Client client;
}
