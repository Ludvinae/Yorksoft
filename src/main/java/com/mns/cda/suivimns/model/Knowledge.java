package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    protected String subject;

    protected String description;

    protected String resolution;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_theme", nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    protected Theme theme;

    @ManyToMany
    @JoinTable(name = "knowledge_versions",
            joinColumns = @JoinColumn(name = "id_knowledge"),
            inverseJoinColumns = @JoinColumn(name = "id_version"))
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected List<Version> versionList;

    @OneToOne(mappedBy = "knowledge")
    protected Procedure procedure;

    /*
    @OneToMany(mappedBy = "knowledge")
    protected List<Ticket> ticketList;

     */
}
