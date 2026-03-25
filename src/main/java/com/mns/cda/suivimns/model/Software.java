package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.view.NewTicketSoftware;
import com.mns.cda.suivimns.view.NewTicketVersionView;
import com.mns.cda.suivimns.view.SoftwareView;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Software {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({NewTicketSoftware.class, SoftwareView.class})
    protected Integer idSoftware;

    @Column(nullable = false, length = 127)
    @NotBlank(groups = {OnCreate.class})
    @Length(min = 1, max = 127)
    @JsonView({NewTicketSoftware.class, SoftwareView.class})
    protected String name;

    @Column(columnDefinition = "TEXT")
    @JsonView({NewTicketSoftware.class, SoftwareView.class})
    protected String description;

    @ManyToOne
    @JoinColumn(name = "id_software_type")
    @JsonView({NewTicketSoftware.class, SoftwareView.class})
    protected SoftwareType type;

    @OneToMany
    @JsonView(NewTicketVersionView.class)
    protected List<Version> versionList;
}
