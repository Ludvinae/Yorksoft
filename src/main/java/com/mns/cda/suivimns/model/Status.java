package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.enumerate.StatusEnum;
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
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idStatus;

    @Column(nullable = false, length = 63)
    protected String designation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 31, unique = true)
    protected StatusEnum code;

    protected Byte displayOrder;
}
