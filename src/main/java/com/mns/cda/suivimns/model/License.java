package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(nullable = false, unique = true, length = 127)
    protected String licenseNumber;

    protected LocalDate expirationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_software", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Software software;


    @ManyToOne
    @JoinColumn(name = "id_app_user")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    protected Client client;
}
