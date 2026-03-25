package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.view.NewTicketSoftware;
import com.mns.cda.suivimns.view.SoftwareView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VersionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({NewTicketSoftware.class, SoftwareView.class})
    protected Integer idVersionType;

    @Column(nullable = false, length = 127)
    @NotBlank
    @Length(max = 127)
    @JsonView({NewTicketSoftware.class, SoftwareView.class})
    protected String designation;
}
