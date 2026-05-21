package com.mns.cda.suivimns.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client extends AppUser {

    protected Byte importance;

    @OneToMany(mappedBy = "client")
    protected List<License> licenseList;

    @OneToMany(mappedBy = "client")
    private List<Ticket> ticketList;

}
