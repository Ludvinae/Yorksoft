package com.mns.cda.suivimns.model;

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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActivity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_app_user", nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private AppUser user;

    @CreatedDate
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String description;
}
