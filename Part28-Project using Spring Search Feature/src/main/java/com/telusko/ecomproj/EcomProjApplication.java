package com.telusko.ecomproj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.telusko.ecomproj.config.TrialApplicationProp;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class EcomProjApplication {

  private TrialApplicationProp trial;

  public EcomProjApplication(TrialApplicationProp trial){
    this.trial = trial;
  }

  public static void main(String[] args) {
    SpringApplication.run(EcomProjApplication.class, args);
    // Cant print trial in here because it has to be a static variable, or else you'll get: Cannot make a static reference to the non-static field trial. If it is a static variable, it'll be null.
  }

  @PostConstruct
  public void printTrial() {
    System.out.println("This is trial: " + trial);
  }
}
