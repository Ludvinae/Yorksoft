package com.mns.cda.suivimns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idProcedure;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime creationDate;

    @LastModifiedDate
    protected LocalDateTime modificationDate;


    @Column(nullable = false, columnDefinition = "TEXT")
    protected String content;

    @Column(nullable = false)
    protected String title;

    @OneToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_knowledge", nullable = false)
    protected Knowledge knowledge;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_technician")
    protected Technician technician;
}
