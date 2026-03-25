package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.view.TicketStatusListView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TicketStatusListView.class)
    protected Integer idStatus;

    @Column(nullable = false, length = 63, unique = true)
    @NotBlank(groups = {OnCreate.class})
    @Length(min = 3, max = 63)
    @JsonView(TicketStatusListView.class)
    protected String designation;

    protected Byte displayOrder;
}
