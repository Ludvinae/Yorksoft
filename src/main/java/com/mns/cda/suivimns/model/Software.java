package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.view.ClientSoftwareListView;
import com.mns.cda.suivimns.view.SoftwareVersionListView;
import com.mns.cda.suivimns.view.SoftwareView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import java.util.List;

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
    @JsonView({ClientSoftwareListView.class, SoftwareView.class})
    protected String description;

    @ManyToOne
    @JoinColumn(name = "id_software_type")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected SoftwareType type;

    // Doit rester nullable, on crée d'abord un software avant de créer ses versions
    @OneToMany(mappedBy = "software")
    protected List<Version> versionList;
}
