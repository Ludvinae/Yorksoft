package com.mns.cda.suivimns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.view.ClientSoftwareListView;
import com.mns.cda.suivimns.view.ClientView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ClientSoftwareListView.class, ClientView.class})
    protected Integer idClient;

    @Column(nullable = false, length = 127)
    @NotBlank(groups = {OnCreate.class})
    @Length(max = 127)
    @JsonView(ClientView.class)
    protected String firstName;

    @Column(nullable = false, length = 127)
    @NotBlank(groups = {OnCreate.class})
    @Length(max = 127)
    @JsonView(ClientView.class)
    protected String lastName;

    @Column(length = 127,nullable = false, unique = true )
    @NotBlank(groups = {OnCreate.class})
    @Email(groups = {OnCreate.class})
    @Length(max = 127)
    @JsonView(ClientView.class)
    protected String email;

    @Column(length = 31)
    @Length(max = 31)
    @JsonView(ClientView.class)
    protected String phoneNumber;

    @Column(nullable = false, length = 127)
    @NotBlank(groups = {OnCreate.class})
    @Length(max = 127)
    protected String password;

    @JsonView(ClientView.class)
    protected Byte importance;

    @ManyToOne
    @JoinColumn(name = "id_organisation")
    @JsonView(ClientSoftwareListView.class)
    protected Organisation organisation;


    @OneToMany(mappedBy = "client")
    @JsonView(ClientSoftwareListView.class)
    protected List<License> licenseList;


}
