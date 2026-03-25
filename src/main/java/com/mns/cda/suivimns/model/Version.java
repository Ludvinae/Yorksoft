package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.view.SoftwareVersionListView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(SoftwareVersionListView.class)
    protected Integer idVersion;

    @Column(nullable = false, length = 63)
    @NotBlank
    @Length(min = 1, max = 63)
    @JsonView(SoftwareVersionListView.class)
    protected String versionNumber;


    protected LocalDateTime publicationDate;

    @ManyToOne
    @JoinColumn(name = "id_version_type")
    @JsonView(SoftwareVersionListView.class)
    protected VersionType versionType;

    @ManyToOne
    @JoinColumn(name = "id_software")
    protected Software software;
}
