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
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idVersion;

    @Column(nullable = false, length = 63)
    protected String versionNumber;


    protected LocalDate publicationDate;

    @ManyToOne
    @JoinColumn(name = "id_version_type")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected VersionType versionType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_software", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Software software;
}
