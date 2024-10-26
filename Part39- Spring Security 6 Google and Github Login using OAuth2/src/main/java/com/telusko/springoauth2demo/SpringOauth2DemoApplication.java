package com.telusko.springoauth2demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringOauth2DemoApplication {
//	method 1 to get the value from application.properties
	@Autowired
	private Environment environ;

//	method 2 to get the value from application.properties. This is recommended instead of method 1 because of testaibility
	@Value("${GOOGLE_CLIENT_ID_TEST}")
	private String googleClientId;
	public static void main(String[] args) {
//		can't use environ or googleClientId here because it's static
		SpringApplication.run(SpringOauth2DemoApplication.class, args);
	}
	@PostConstruct
	public void printGoogleClientId() {
		System.out.println("Method 1: " + environ.getProperty("GOOGLE_CLIENT_ID_TEST"));
		System.out.println("Method 2: " + googleClientId);
	}
}
