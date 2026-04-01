package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.view.TicketView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TicketView.class)
    protected Integer idTheme;

    @Column(nullable = false, length = 127, unique = true)
    @NotBlank(groups = {OnCreate.class})
    @Length(min = 3, max = 127)
    @JsonView(TicketView.class)
    protected String designation;

    @Column(columnDefinition = "TEXT")
    protected String description;

    @JsonView(TicketView.class)
    protected Byte priorityFactor;

}
