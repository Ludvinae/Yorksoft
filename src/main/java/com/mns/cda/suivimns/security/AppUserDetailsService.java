package com.mns.cda.suivimns.security;

import com.mns.cda.suivimns.dao.*;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.service.entity.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    protected final ClientDao clientDao;
    protected final TechnicianDao technicianDao;
    protected final ManagerDao managerDao;
    protected final DirectorDao directorDao;

    public AppUserDetailsService(ClientDao clientDao, TechnicianDao technicianDao,
                                 ManagerDao managerDao, DirectorDao directorDao) {
        this.clientDao = clientDao;
        this.technicianDao = technicianDao;
        this.managerDao = managerDao;
        this.directorDao = directorDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> client = clientDao.findByEmail(email);
        Optional<Technician> technician = technicianDao.findByEmail(email);
        Optional<Manager> manager = managerDao.findByEmail(email);
        Optional<Director> director = directorDao.findByEmail(email);

        if (client.isPresent()) {
            return new AppUserDetails(client.get());
        } else if (technician.isPresent()) {
            return new AppUserDetails(technician.get());
        } else if (manager.isPresent()) {
            return new AppUserDetails(manager.get());
        } else if (director.isPresent()) {
            return new AppUserDetails(director.get());
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
