package com.mns.cda.suivimns.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationKey implements Serializable {

    @Column(name = "id_ticket")
    Integer idTicket;

    @Column(name = "id_theme")
    Integer idTheme;
}
