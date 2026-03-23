package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.view.AssignmentView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AssignmentView.class)
    protected Integer idAssignment;

    @CreationTimestamp
    //@Column(nullable = false)
    //@NotBlank(groups = {OnCreate.class})
    @JsonView(AssignmentView.class)
    protected LocalDateTime assigmentDate;

    @JsonView(AssignmentView.class)
    protected LocalDateTime endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ticket")
    @JsonView(AssignmentView.class)
    protected Ticket ticket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_manager")
    @JsonView(AssignmentView.class)
    protected Employee manager;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_technician")
    @JsonView(AssignmentView.class)
    protected Employee technician;
}
