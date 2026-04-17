package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.view.TicketView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TicketView.class)
    protected Integer idComment;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(groups = {OnCreate.class,  OnUpdate.class})
    @JsonView(TicketView.class)
    protected String content;

    @CreatedDate
    @Column(updatable = false)
    @JsonView(TicketView.class)
    protected LocalDateTime dateSent;

    @LastModifiedDate
    @JsonView(TicketView.class)
    protected LocalDateTime lastModification;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    protected Ticket ticket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_app_user")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    protected AppUser author;

}
