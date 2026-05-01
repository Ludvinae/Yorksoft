package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idTheme;

    @Column(nullable = false, length = 127, unique = true)
    protected String designation;

    @Column(columnDefinition = "TEXT")
    protected String description;

}
