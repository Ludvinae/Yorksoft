package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.enumerate.PriorityEnum;
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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idTicket;

    @Column(nullable = false, length = 63)
    protected String title;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime openDate;

    protected LocalDateTime closeDate;

    @LastModifiedDate
    protected LocalDateTime modificationDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    protected String description;

    protected Integer callDuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    protected PriorityEnum initialPriority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected PriorityEnum currentPriority;

    @ManyToOne
    @JoinColumn(name = "id_version")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected Version version;

    @ManyToOne
    @JoinColumn(name = "id_urgency")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected Urgency urgency;

    @ManyToOne
    @JoinColumn(name = "id_impact")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected Impact impact;

    @ManyToOne
    @JoinColumn(name = "id_client")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    protected Client client;

    @OneToMany(mappedBy = "ticket")
    protected List<History> historyList;

    @OneToMany(mappedBy = "ticket")
    protected List<Classification> classificationList;

    @OneToMany(mappedBy = "ticket")
    protected List<Comment> commentList;

    @OneToMany(mappedBy = "ticket")
    protected List<Assignment> assignmentList;
}
