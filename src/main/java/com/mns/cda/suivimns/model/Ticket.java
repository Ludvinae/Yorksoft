package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.enumerate.StatusEnum;
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

    @Column(columnDefinition = "TEXT")
    protected String solution;

    protected Integer callDuration;

    // Champs calculés
    @Column(nullable = false, updatable = false)
    protected Integer initialPriority;

    @Column(nullable = false)
    protected Integer currentPriority;

    @Enumerated(EnumType.STRING)
    @Column
    protected StatusEnum currentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_current_theme")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    protected Theme currentTheme;

    @ManyToOne()
    @JoinColumn(name = "id_current_technician")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    protected Technician currentTechnician;

    @ManyToOne()
    @JoinColumn(name = "id_current_manager")
    protected Manager currentManager;

    protected Long activeTimeInSeconds;

    protected LocalDateTime slaDeadline;

    @Column(nullable = false)
    protected boolean overdue = false;

    // Jointures
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_version")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected Version version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_urgency")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected Urgency urgency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_impact")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    protected Impact impact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    protected Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_knowledge")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    protected Knowledge knowledge;


    // Review later to see if it's still relevant
    @OneToMany(mappedBy = "ticket")
    protected List<History> historyList;

    @OneToMany(mappedBy = "ticket")
    protected List<Classification> classificationList;

    @OneToMany(mappedBy = "ticket")
    protected List<Comment> commentList;

    @OneToMany(mappedBy = "ticket")
    protected List<Assignment> assignmentList;
}
