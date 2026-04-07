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

    @Column(nullable = false)
    @NotNull
    protected Integer userCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_software")
    @JsonView(ClientSoftwareListView.class)
    protected Software software;


    @ManyToMany
    @JoinTable(name = "license_clients",
            joinColumns = @JoinColumn(name = "id_license"),
            inverseJoinColumns = @JoinColumn(name = "id_client"))
    protected List<Client> clientList;
}
