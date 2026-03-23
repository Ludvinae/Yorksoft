package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.view.EmployeeView;
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
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idRole;

    @Column(nullable = false, length = 127)
    @Length(max = 127)
    @NotBlank
    @JsonView(EmployeeView.class)
    protected String designation;

    @Column(length = 15)
    @Length(max = 15)
    @JsonView(EmployeeView.class)
    protected String rank;
}
