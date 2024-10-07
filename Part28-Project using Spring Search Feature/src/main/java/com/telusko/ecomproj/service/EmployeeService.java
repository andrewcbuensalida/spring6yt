package com.telusko.ecomproj.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import com.telusko.ecomproj.model.Employee;

@Service
public class EmployeeService {

  private final RestTemplate restTemplate;
  private final String url = "https://jsonplaceholder.typicode.com/users";

  public EmployeeService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<Employee> getAllEmployeesRestTemplate() {
    Employee[] employees = restTemplate.getForObject(url, Employee[].class);
    return Arrays.asList(employees);
  }

  public Employee createEmployeeRestTemplate(Employee employee) {
    return restTemplate.postForObject(url, employee, Employee.class);
  }

}
