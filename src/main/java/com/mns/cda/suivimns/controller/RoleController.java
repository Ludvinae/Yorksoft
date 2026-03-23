package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.RoleDao;
import com.mns.cda.suivimns.model.Role;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {

    protected RoleDao roleDao;

    @Autowired
    public RoleController(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @GetMapping("/role/list")
    public List<Role> getAll() {
        return roleDao.findAll();
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> getById(@PathVariable int id) {

        Optional<Role> role = roleDao.findById(id);
        if (role.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(role.get(), HttpStatus.OK);
    }

    @PostMapping("/role")
    public ResponseEntity<Role> create(@RequestBody @Validated(OnCreate.class) Role role) {
        role.setIdRole(null);
        roleDao.save(role);

        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Role> delete(@PathVariable int id) {
        Optional<Role> role = roleDao.findById(id);
        if (role.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roleDao.delete(role.get());
        return new ResponseEntity<>(role.get(), HttpStatus.OK);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<Role> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Role roleToUpdate) {
        Optional<Role> role = roleDao.findById(id);

        if (role.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roleToUpdate.setIdRole(role.get().getIdRole());
        roleDao.save(roleToUpdate);

        return new ResponseEntity<>(role.get(), HttpStatus.OK);
    }
}
