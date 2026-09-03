package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.model.keys.ClassificationKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Classification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idClassification;

    @CreatedDate
    protected LocalDateTime affectationDate;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "id_ticket")
    @OnDelete(action= OnDeleteAction.CASCADE)
    protected Ticket ticket;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "id_theme")
    @OnDelete(action= OnDeleteAction.CASCADE)
    protected Theme theme;


}
