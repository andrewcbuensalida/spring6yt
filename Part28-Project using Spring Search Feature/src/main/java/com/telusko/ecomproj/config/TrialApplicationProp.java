package com.telusko.ecomproj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// When you have a group of configuration properties in the application.properties file, it makes sense to use this @ConfigurationProperties. If it's a one off thing, use @Value.
@Configuration
@ConfigurationProperties(prefix = "trial")
public class TrialApplicationProp {
  private String name;
  private int age;

  // Getters and Setters
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
  @Override
  public String toString() {
    return "TrialApplicationProp{" +
        "name='" + name + '\'' +
        ", age=" + age +
        '}';
  }
}