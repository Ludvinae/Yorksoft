package com.mns.cda.suivimns.model;

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
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idActivityType;

    @Column(nullable = false, length = 63)
    protected String designation;

    @Column(length = 127)
    protected String name;

    @Column(length = 255)
    protected String iconAddress;

}
