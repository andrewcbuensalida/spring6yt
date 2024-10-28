package com.telusko.ecomproj.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // This allows the application to refresh the properties from the cloud config server without restarting the application
public class TestController {

  @Value("${myColor}")
  private String myColor;

  @Value("${mySize}")
  private String mySize;

  @Value("${myShape}")
  private String myShape;

  // ace of spades is fallback if there is no secretCard in application.properties. Since this is coming from cloud config server. aka part 39 which is coming from the git uri, if they update, this won't update unless you restart the app. So you don't have to restart the app, you have to make a post request to this app http://localhost:8079/actuator/refresh, and this controller has to have the @RefreshScope
  @Value("${secretCard:ace of spades}")
  private String secretCard;

  @GetMapping("/")
  public String test() {
    return String.format("Hello, World! Color: %s, Size: %s, Shape: %s, Secret Card: %s", myColor, mySize, myShape,
        secretCard);

  }
}
