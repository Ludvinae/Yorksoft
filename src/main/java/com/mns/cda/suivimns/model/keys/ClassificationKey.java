package com.mns.cda.suivimns.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ClassificationKey implements Serializable {

    @Column(name = "id_ticket")
    Integer id_ticket;

    @Column(name = "id_theme")
    Integer id_theme;
}
