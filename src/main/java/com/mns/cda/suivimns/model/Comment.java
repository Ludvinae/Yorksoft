package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.view.TicketView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Column(updatable = false)
    @JsonView(TicketView.class)
    protected LocalDateTime dateSent;

    @UpdateTimestamp
    @JsonView(TicketView.class)
    protected LocalDateTime lastModification;

    @ManyToOne
    @JoinColumn(name = "id_ticket")
    protected Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_app_user")
    protected AppUser author;

}
