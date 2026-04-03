package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idAppUser;

    @Column(length = 127)
    @Size(max = 127)
    protected String firstName;

    @Column(length = 127)
    @Size(max = 127)
    protected String lastName;

    @Column(length = 127, unique = true)
    @Email(groups = {OnCreate.class})
    @Size(max = 127)
    protected String email;

    @Column(length = 31)
    @Size(max = 31)
    protected String phoneNumber;

    @Column(nullable = false, length = 127)
    @NotBlank(groups = {OnCreate.class})
    @Size(max = 127)
    protected String password;

}
