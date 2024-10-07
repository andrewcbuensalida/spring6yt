package com.telusko.ecomproj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.telusko.ecomproj.model.Album;
import com.telusko.ecomproj.model.Employee;
import com.telusko.ecomproj.model.EmployeeWithAlbum;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    // // try catch could be in the service layer (more granular) or controller
    // // layer(more simple) or repository layer(for database operations)
    // Employee[] employees = restTemplate.exchange(url, HttpMethod.GET, null,
    // Employee[].class).getBody();
    // return Arrays.asList(employees);

    // alternative to Employee[] above. Since we can't do List<Employee>.class, we
    // use ParameterizedTypeReference
    ParameterizedTypeReference<List<Employee>> parameterizedTypeReference = new ParameterizedTypeReference<List<Employee>>() {
    };
    return restTemplate.exchange(url, HttpMethod.GET, null, parameterizedTypeReference).getBody();

  }

  // Sync version
  public List<Employee> getAllEmployeesWebClient() {
    return webClient.get()
        .uri(url)
        .retrieve()
        .bodyToFlux(Employee.class)
        .collectList()
        .block();
  }

  // Async version, making two calls. The first call is to get the albums, and the
  // second call is to get the employees. Then nest the albums into the employees.
  public List<EmployeeWithAlbum> getAllEmployeesWithAlbums() {
    ParameterizedTypeReference<List<Album>> parameterizedTypeReferenceAlbum = new ParameterizedTypeReference<List<Album>>() {
    };
    Mono<List<Album>> albums = webClient.get()
        .uri("https://jsonplaceholder.typicode.com/albums")
        .retrieve()
        .bodyToMono(parameterizedTypeReferenceAlbum);

    ParameterizedTypeReference<List<Employee>> parameterizedTypeReferenceEmployee = new ParameterizedTypeReference<List<Employee>>() {
    };
    Mono<List<Employee>> employees = webClient.get()
        .uri(url)
        .retrieve()
        .bodyToMono(parameterizedTypeReferenceEmployee);

    List<EmployeeWithAlbum> employeesWithAlbums = employees.zipWith(albums, (empList, albList) -> {
      return empList.stream().map(emp -> {
        List<Album> empAlbums = albList.stream()
            .filter(album -> album.getUserId() == emp.getId())
            .collect(Collectors.toList());
        return new EmployeeWithAlbum(emp.getId(), emp.getName(), emp.getUsername(), emp.getEmail(), empAlbums);
      }).collect(Collectors.toList());
    }).block();

    return employeesWithAlbums;
  }

  public Employee createEmployeeRestTemplate(Employee employee) {
    return restTemplate.postForObject(url, employee, Employee.class); // could use postForObject instead of exchange
  }

}
