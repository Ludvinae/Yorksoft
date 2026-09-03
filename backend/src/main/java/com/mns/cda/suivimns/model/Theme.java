package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idTheme;

    @Column(nullable = false, length = 127)
    protected String designation;

    @Column(nullable = false, length = 127, unique = true)
    protected String code;

    @Column(columnDefinition = "TEXT")
    protected String description;

    @OneToMany(mappedBy = "theme")
    protected List<Classification> classificationList;

}
