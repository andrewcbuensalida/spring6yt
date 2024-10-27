package com.telusko.ecomproj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.telusko.ecomproj.model.Employee;
import com.telusko.ecomproj.model.EmployeeWithAlbum;
import com.telusko.ecomproj.service.EmployeeService;
import java.util.List;

@RequestMapping("/api")
@CrossOrigin
@RestController // This annotation is used to create RESTful web services using Spring MVC.
  // It combines @Controller and @ResponseBody, which means that the class is a controller and the return values of its methods will be serialized to JSON and sent in the HTTP response body.
public class EmployeeController {
  @Autowired
  private EmployeeService service;
  
  @GetMapping("/employee-resttemplate")
  public ResponseEntity<List<Employee>> getAllEmployeesRestTemplate() {
    try {
      return new ResponseEntity<>(service.getAllEmployeesRestTemplate(), HttpStatus.OK);
    } catch (Exception e) {
      // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // The client will get a 500 error but with no body

      // or could throw a ResponseStatusException
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while fetching employees", e);
    }
  }
  
  @GetMapping("/employee-webclient")
  public ResponseEntity<List<Employee>> getAllEmployeesWebClient() {
    return new ResponseEntity<>(service.getAllEmployeesWebClient(), HttpStatus.OK);
  }

  @GetMapping("/employees-with-albums")
  public ResponseEntity<List<EmployeeWithAlbum>> getAllEmployeesWithAlbums() {
    return new ResponseEntity<>(service.getAllEmployeesWithAlbums(), HttpStatus.OK);
  }

  @GetMapping("/employee/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    return new ResponseEntity<>(service.getEmployeeById(id), HttpStatus.OK);
  }
  
  @PostMapping("/employee-resttemplate")
  public ResponseEntity<?> createEmployeeRestTemplate(@RequestBody Employee employee) {
    // without try catch and it throws an exception, it will return a 500 error, but if the client sends a valid request the next time, it will work. In other words, throwing an exception doesn't stop the server completely.
    try {
      Employee createdEmployee = service.createEmployeeRestTemplate(employee);
      return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}