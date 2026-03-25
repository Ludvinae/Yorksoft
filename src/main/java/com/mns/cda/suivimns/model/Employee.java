package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.view.AssignmentView;
import com.mns.cda.suivimns.view.EmployeeTicketListView;
import com.mns.cda.suivimns.view.EmployeeView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({EmployeeView.class, EmployeeTicketListView.class})
    protected Integer idEmployee;

    @Column(nullable = false, length = 127)
    @NotBlank(groups = {OnCreate.class})
    @Length(max = 127)
    @JsonView(EmployeeView.class)
    protected String firstName;

    @Column(nullable = false, length = 127)
    @NotBlank(groups = {OnCreate.class})
    @Length(max = 127)
    @JsonView(EmployeeView.class)
    protected String lastName;

    @Column(length = 127, nullable = false, unique = true)
    @NotBlank(groups = {OnCreate.class})
    @Email(groups = {OnCreate.class})
    @Length(max = 127)
    @JsonView(EmployeeView.class)
    protected String email;

    @Column(length = 31)
    @Length(max = 31)
    @JsonView(EmployeeView.class)
    protected String phoneNumber;

    @Column(nullable = false, length = 127)
    @NotBlank(groups = {OnCreate.class})
    @Length(max = 127)
    protected String password;

    @ManyToOne
    @JoinColumn(name = "id_role")
    @JsonView({EmployeeView.class, AssignmentView.class})
    protected Role role;

    @OneToMany(mappedBy = "manager")
    @JsonView(EmployeeTicketListView.class)
    protected List<Assignment> assignedBy;

    @OneToMany(mappedBy = "technician")
    @JsonView(EmployeeTicketListView.class)
    protected List<Assignment> assignedTO;
}
