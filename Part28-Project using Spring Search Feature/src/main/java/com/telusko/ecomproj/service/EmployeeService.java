package com.telusko.ecomproj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;
import java.time.Duration;

import com.telusko.ecomproj.exceptions.EmployeeNotFoundException;
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

  // could also return Optional<List<Employee>>
  public List<Employee> getAllEmployeesRestTemplate() {
    // // try catch could be in the service layer (more granular) or controller
    // // layer(more simple) or repository layer(for database operations)
    // // could have custom exceptions with @ControllerAdvice and @ExceptionHandler
    // like in pokemon-review-springboot project
    // // or could throw a ResponseStatusException

    // Employee[] employees = restTemplate.exchange(url, HttpMethod.GET, null,
    // Employee[].class).getBody();
    // return Arrays.asList(employees);

    // alternative to Employee[] above. Since we can't do List<Employee>.class, we
    // use ParameterizedTypeReference so we don't have to cast it with Arrays.asList
    ParameterizedTypeReference<List<Employee>> parameterizedTypeReference = new ParameterizedTypeReference<List<Employee>>() {
    };
    return restTemplate.exchange(url, HttpMethod.GET, null, parameterizedTypeReference).getBody();

  }

  // Sync webClient version just has block() at the end
  public List<Employee> getAllEmployeesWebClient() {
    return webClient.get()
        .uri(url)
        .retrieve()
        .bodyToFlux(Employee.class)
        .collectList()
        .block();
  }

  // Async webClient version, making two calls. The first call is to get the
  // albums, and the
  // second call is to get the employees. Then nest the albums into the employees.
  public List<EmployeeWithAlbum> getAllEmployeesWithAlbums() {
    // // could do Mono, but Mono is usually just for getting one object
    // ParameterizedTypeReference<List<Album>> parameterizedTypeReferenceAlbum = new
    // ParameterizedTypeReference<List<Album>>() {
    // };
    // Mono<List<Album>> albums = webClient.get()
    // .uri("https://jsonplaceholder.typicode.com/albums")
    // .retrieve()
    // .bodyToMono(parameterizedTypeReferenceAlbum);


    // instead of Mono, we could use Flux
    Flux<Album> albums = webClient.get()
        .uri("https://jsonplaceholder.typicode.com/albums")
        .retrieve()
        .bodyToFlux(Album.class)// do bodyToMono if getting just one object
        .delaySequence(Duration.ofSeconds(5)); // Adding delay of 5 second just to see the benefit of async

    Flux<Employee> employees = webClient.get()
        .uri(url)
        .retrieve()
        .bodyToFlux(Employee.class)// do bodyToMono if getting just one object
        .delaySequence(Duration.ofSeconds(5)); // Adding delay of 5 second just to see the benefit of async

    // // This is if we did the Mono method. Combining albums to employees
    // List<EmployeeWithAlbum> employeesWithAlbums = employees.zipWith(albums,
    // (empList, albList) -> {
    // return empList.stream().map(emp -> {
    // List<Album> empAlbums = albList.stream()
    // .filter(album -> album.getUserId() == emp.getId())
    // .collect(Collectors.toList());
    // return new EmployeeWithAlbum(emp.getId(), emp.getName(), emp.getUsername(),
    // emp.getEmail(), empAlbums);
    // }).collect(Collectors.toList());
    // }).block();

    // Combining albums to employees using Flux
    List<EmployeeWithAlbum> employeesWithAlbums = employees.collectList()
        .zipWith(albums.collectList(), (empList, albList) -> {
          return empList.stream().map(emp -> {
            List<Album> empAlbums = albList.stream()
                .filter(album -> album.getUserId() == emp.getId())
                .collect(Collectors.toList());
            return new EmployeeWithAlbum(emp.getId(), emp.getName(), emp.getUsername(), emp.getEmail(), empAlbums);
          }).collect(Collectors.toList());
        }).block();

    return employeesWithAlbums;
  }

  public Employee getEmployeeById(Long id) {
    try {
      return webClient.get()
          .uri(url + "/" + id)
          .retrieve()
          .bodyToMono(Employee.class)
          .block();
    } catch (Exception e) {
      throw new EmployeeNotFoundException("!!!!Employee not found with id: " + id); // could do the throw here, or in the controller with service.getEmployeeById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id)); but have to return an Optional<Employee> in here
    }

  }

  public Employee createEmployeeRestTemplate(Employee employee) {
    return restTemplate.postForObject(url, employee, Employee.class); // could use postForObject instead of exchange
  }

}
