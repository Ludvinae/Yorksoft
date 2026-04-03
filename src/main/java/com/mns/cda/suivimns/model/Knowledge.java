package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Knowledge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idKnowledge;

    @Column(nullable = false)
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Size(max = 255)
    protected String subject;

    @ManyToOne
    @JoinColumn(name = "id_theme")
    protected Theme theme;

    @ManyToMany
    @JoinTable(name = "knowledge_versions",
            joinColumns = @JoinColumn(name = "id_knowledge"),
            inverseJoinColumns = @JoinColumn(name = "id_version"))
    protected List<Version> versionList;
}
