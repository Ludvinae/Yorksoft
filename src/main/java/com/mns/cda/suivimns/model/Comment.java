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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idComment;

    @Column(nullable = false, columnDefinition = "TEXT")
    protected String content;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime dateSent;

    @LastModifiedDate
    protected LocalDateTime lastModification;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Ticket ticket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_app_user")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    protected AppUser author;

}
