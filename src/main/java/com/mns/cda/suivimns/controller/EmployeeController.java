package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dao.EmployeeDao;
import com.mns.cda.suivimns.model.Employee;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.view.EmployeeTicketListView;
import com.mns.cda.suivimns.view.EmployeeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    protected EmployeeDao employeeDao;

    @Autowired
    public EmployeeController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @GetMapping("/employee/list")
    @JsonView(EmployeeView.class)
    public List<Employee> getAll() {
        return employeeDao.findAll();
    }

    @GetMapping("/employee/{id}")
    @JsonView(EmployeeView.class)
    public ResponseEntity<Employee> getById(@PathVariable int id) {

        Optional<Employee> employee = employeeDao.findById(id);
        if (employee.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    @GetMapping("/employee/{id}/ticket/list")
    @JsonView(EmployeeTicketListView.class)
    public ResponseEntity<Employee> getEmployeeTicketList(@PathVariable int id) {

        Optional<Employee> employee = employeeDao.findById(id);
        if (employee.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> create(@RequestBody @Validated(OnCreate.class) Employee employee) {
        employee.setIdEmployee(null);
        employeeDao.save(employee);

        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> delete(@PathVariable int id) {
        Optional<Employee> employee = employeeDao.findById(id);
        if (employee.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        employeeDao.delete(employee.get());
        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    @PutMapping("/employee/{id}")
    @JsonView(EmployeeView.class)
    public ResponseEntity<Employee> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Employee employeeToUpdate) {
        Optional<Employee> employee = employeeDao.findById(id);

        if (employee.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        employeeToUpdate.setIdEmployee(employee.get().getIdEmployee());
        employeeToUpdate.setPassword(employee.get().getPassword());
        employeeDao.save(employeeToUpdate);
        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }
}
