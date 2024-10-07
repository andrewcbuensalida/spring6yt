package com.telusko.ecomproj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.ecomproj.model.Employee;
import com.telusko.ecomproj.service.EmployeeService;
import java.util.List;

@RequestMapping("/api")
@CrossOrigin
@RestController
public class EmployeeController {
  @Autowired
  private EmployeeService service;

  @GetMapping("/employee-resttemplate")
  public ResponseEntity<List<Employee>> getAllEmployeesRestTemplate() {
    return new ResponseEntity<>(service.getAllEmployeesRestTemplate(), HttpStatus.OK);
  }
}