package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.view.ClientSoftwareListView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ClientSoftwareListView.class)
    protected Integer idOrganisation;

    @Column(nullable = false, length = 127)
    @NotBlank(groups = {OnCreate.class})
    @Length(max = 127)
    protected String name;

    @Column(length = 127)
    @Length(max = 127)
    protected String domain;

    @Length(max = 255)
    protected String siretNumber;

    @ManyToOne
    @JoinColumn(name = "id_organisation_type")
    @JsonView(ClientSoftwareListView.class)
    protected OrganisationType type;

    @OneToMany(mappedBy = "organisation")
    @JsonView(ClientSoftwareListView.class)
    protected List<License> licenseList;
}
