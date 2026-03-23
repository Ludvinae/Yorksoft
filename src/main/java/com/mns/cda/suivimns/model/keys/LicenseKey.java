package com.mns.cda.suivimns.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class LicenseKey implements Serializable {

    @Column(name = "id_software")
    Integer id_software;

    @Column(name = "id_organisation")
    Integer id_organisation;
}
