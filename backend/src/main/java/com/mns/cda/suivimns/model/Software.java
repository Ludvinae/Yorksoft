package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Software {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idSoftware;

    @Column(nullable = false, length = 127)
    protected String name;

    @Column(columnDefinition = "TEXT")
    protected String description;

    @ManyToOne
    @JoinColumn(name = "id_software_type")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected SoftwareType type;

    // Doit rester nullable, on crée d'abord un software avant de créer ses versions
    //@OneToMany(mappedBy = "software")
    //protected List<Version> versionList;
}
