package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.view.EmployeeTicketListView;
import com.mns.cda.suivimns.view.TicketStatusListView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @JsonView({EmployeeTicketListView.class, TicketStatusListView.class})
    protected Integer idTicket;

    @CreationTimestamp
    protected LocalDateTime openDate;

    protected LocalDateTime closeDate;

    @UpdateTimestamp
    protected LocalDateTime modificationDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(groups = {OnCreate.class})
    protected String description;

    protected Integer callDuration;

    @Column(nullable = false)
    @NotBlank(groups = {OnCreate.class})
    protected Integer initialPriority;

    protected Integer finalPriority;

    @ManyToOne
    @JoinColumn(name = "id_communication_canal")
    protected CommunicationCanal canal;

    @ManyToOne
    @JoinColumn(name = "id_version")
    protected Version version;

    @ManyToOne
    @JoinColumn(name = "id_urgency")
    protected Urgency urgency;

    @ManyToOne
    @JoinColumn(name = "id_impact")
    protected Impact impact;

    @ManyToOne
    @JoinColumn(name = "id_client")
    protected Client client;

    @OneToMany(mappedBy = "ticket")
    @JsonView(TicketStatusListView.class)
    protected List<History> history;

}
