package com.telusko.ecomproj.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @Value("${myColor}")
  private String myColor;

  @Value("${mySize}")
  private String mySize;

  @Value("${myShape}")
  private String myShape;

  @GetMapping("/")
  public String test() {
    return String.format("Hello, World! Color: %s, Size: %s, Shape: %s ", myColor, mySize, myShape);

  }
}
