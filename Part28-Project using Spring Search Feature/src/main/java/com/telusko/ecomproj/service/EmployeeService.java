package com.telusko.ecomproj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import com.telusko.ecomproj.model.Employee;

@Service
public class EmployeeService {

  @Autowired
  private WebClient webClient;

  private final RestTemplate restTemplate;
  private final String url = "https://jsonplaceholder.typicode.com/users";

  public EmployeeService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<Employee> getAllEmployeesRestTemplate() {
    // try catch could be in the service layer (more granular) or controller
    // layer(more simple) or repository layer(for database operations)
    Employee[] employees = restTemplate.exchange(url, HttpMethod.GET, null, Employee[].class).getBody();
    return Arrays.asList(employees);
  }

  public List<Employee> getAllEmployeesWebClient() {
    return webClient.get()
        .uri(url)
        .retrieve()
        .bodyToFlux(Employee.class)
        .collectList()
        .block();
  }

  public Employee createEmployeeRestTemplate(Employee employee) {
    return restTemplate.postForObject(url, employee, Employee.class); // could use postForObject instead of exchange
  }

}
