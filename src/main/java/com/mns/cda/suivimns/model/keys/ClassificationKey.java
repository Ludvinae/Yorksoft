package com.mns.cda.suivimns.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationKey implements Serializable {

    @Column(name = "id_ticket")
    Integer idTicket;
    
    @Column(name = "id_theme")
    Integer idTheme;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClassificationKey that = (ClassificationKey) o;
        return Objects.equals(idTicket, that.idTicket) && Objects.equals(idTheme, that.idTheme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTicket, idTheme);
    }

}
