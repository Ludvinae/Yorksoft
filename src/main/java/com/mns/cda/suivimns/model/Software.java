package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(groups = {OnCreate.class})
    @Size(max = 127)
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
