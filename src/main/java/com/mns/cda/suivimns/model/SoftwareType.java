package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.view.ClientSoftwareListView;
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
public class SoftwareType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idSoftwareType;

    @Column(nullable = false, length = 127)
    @NotBlank
    @Length(min = 3, max = 127)
    @JsonView({ClientSoftwareListView.class, SoftwareView.class})
    protected String designation;

}
