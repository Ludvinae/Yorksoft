package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(max = 63)
    protected String title;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime openDate;

    protected LocalDateTime closeDate;

    @LastModifiedDate
    protected LocalDateTime modificationDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(groups = {OnCreate.class})
    protected String description;

    protected Integer callDuration;

    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Empeche le changement de cette valeur meme avec le setter
    protected Integer initialPriority;

    @Column(nullable = false)
    protected Integer finalPriority;

    @ManyToOne
    @JoinColumn(name = "id_version")
    protected Version version;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_urgency", nullable = false)
    @NotNull(groups = {OnCreate.class})
    protected Urgency urgency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_impact", nullable = false)
    @NotNull(groups = {OnCreate.class})
    protected Impact impact;

    @ManyToOne
    @JoinColumn(name = "id_client")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @NotNull(groups = {OnCreate.class})
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
