package com.telusko.part38jwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

  @GetMapping("/")
  public String greet(HttpServletRequest request) {
    String sessionId = request.getSession().getId();
    logger.info("Session ID: {}", sessionId);
    logger.error("This is an errorrrrrrr");
    // default log level is info, so debug or trace won't be printed. Can set logging. level.root=debug in application.properties
    logger.debug("This is a debug message");
    logger.trace("This is a trace message");
    return "Welcome to Telusko " + sessionId;
  }
}