package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.view.AssignmentView;
import com.mns.cda.suivimns.view.EmployeeTicketListView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({AssignmentView.class, EmployeeTicketListView.class})
    protected Integer idAssignment;

    @CreatedDate
    @JsonView({AssignmentView.class, EmployeeTicketListView.class})
    protected LocalDateTime assignmentDate;

    @JsonView({AssignmentView.class, EmployeeTicketListView.class})
    protected LocalDateTime endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ticket")
    @JsonView({AssignmentView.class, EmployeeTicketListView.class})
    protected Ticket ticket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_manager")
    @JsonView(AssignmentView.class)
    protected AppUser manager;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_technician")
    @JsonView(AssignmentView.class)
    protected AppUser technician;
}
