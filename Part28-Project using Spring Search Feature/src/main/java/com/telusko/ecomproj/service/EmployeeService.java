package com.telusko.ecomproj.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import com.telusko.ecomproj.model.Employee;

@Service
public class EmployeeService {

  private final RestTemplate restTemplate;

  public EmployeeService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<Employee> getAllEmployeesRestTemplate() {
    String url = "https://jsonplaceholder.typicode.com/users";
    Employee[] employees = restTemplate.getForObject(url, Employee[].class);
    return Arrays.asList(employees);
  }

}
