package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.model.keys.LicenseKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(LicenseKey.class)
public class License {

    @Id
    protected Integer id_software;

    @Id
    protected Integer id_organisation;

    @ManyToOne
    @MapsId("id_software")
    @JoinColumn(name = "id_software")
    protected Software software;

    @ManyToOne
    @MapsId("id_organisation")
    @JoinColumn(name = "id_organisation")
    protected Organisation organisation;


}
