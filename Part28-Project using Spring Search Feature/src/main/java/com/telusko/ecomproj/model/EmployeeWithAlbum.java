package com.telusko.ecomproj.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithAlbum {
  private Long id;
  private String name;
  private String username;
  private String email;
  private List<Album> albums;
}