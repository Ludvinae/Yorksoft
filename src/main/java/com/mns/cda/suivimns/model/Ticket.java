package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.view.EmployeeTicketListView;
import com.mns.cda.suivimns.view.TicketStatusListView;
import com.mns.cda.suivimns.view.TicketView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({EmployeeTicketListView.class, TicketStatusListView.class, TicketView.class})
    protected Integer idTicket;

    @Column(nullable = false, length = 63)
    @NotBlank
    @Size(max = 63)
    protected String title;

    @CreationTimestamp
    @JsonView(TicketView.class)
    protected LocalDateTime openDate;

    @JsonView(TicketView.class)
    protected LocalDateTime closeDate;

    @UpdateTimestamp
    @JsonView(TicketView.class)
    protected LocalDateTime modificationDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(groups = {OnCreate.class})
    @JsonView(TicketView.class)
    protected String description;

    @JsonView(TicketView.class)
    protected Integer callDuration;

    @Column(nullable = false)
    @NotBlank(groups = {OnCreate.class})
    @JsonView(TicketView.class)
    protected Integer initialPriority;

    @JsonView(TicketView.class)
    protected Integer finalPriority;


    @ManyToOne
    @JoinColumn(name = "id_version")
    @JsonView(TicketView.class)
    protected Version version;

    @ManyToOne
    @JoinColumn(name = "id_urgency")
    @JsonView(TicketView.class)
    protected Urgency urgency;

    @ManyToOne
    @JoinColumn(name = "id_impact")
    @JsonView(TicketView.class)
    protected Impact impact;

    @ManyToOne
    @JoinColumn(name = "id_client")
    @JsonView(TicketView.class)
    protected Client client;

    @OneToMany(mappedBy = "ticket")
    @JsonView({TicketStatusListView.class, TicketView.class})
    protected List<History> historyList;

    @OneToMany(mappedBy = "ticket")
    @JsonView(TicketView.class)
    protected List<Classification> classificationList;

    @OneToMany(mappedBy = "ticket")
    @JsonView(TicketView.class)
    protected List<Comment> commentList;

    @OneToMany(mappedBy = "ticket")
    protected List<Assignment> assignmentList;
}
