package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idAppUser;

    @Column(length = 127)
    protected String firstName;

    @Column(length = 127)
    protected String lastName;

    @Column(length = 127, unique = true, nullable = false)
    protected String email;

    @Column(length = 31)
    protected String phoneNumber;

    // A rendre nullable pour clients ?
    @Column(nullable = false, length = 127)
    protected String password;

}
