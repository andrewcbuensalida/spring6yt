package com.telusko.springoauth2demo;

import jakarta.annotation.PostConstruct;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableConfigServer // now another spring boot app can be the client and set their
                    // spring.cloud.config.uri to
                    // http://localhost:8080 to get their properties from
                    // spring.cloud.config.server.git.uri. If the file name in the git uri is the
                    // same as the application name of the client, that's the file that will be
                    // chosen.
public class SpringOauth2DemoApplication {
  // method 1 to get the value from application.properties
  @Autowired
  private Environment environ;

  // method 2 to get the value from application.properties. This is recommended
  // instead of method 1 because of testability. Can type cast it into a list or a
  // map if it's like that in the file.
  @Value("${GOOGLE_CLIENT_ID_TEST}")
  private String googleClientId;

  public static void main(String[] args) {
    // can't use environ or googleClientId here because it's static
    SpringApplication.run(SpringOauth2DemoApplication.class, args);
  }

  // could also use ApplicationRunner. This runs before runner
  @PostConstruct
  public void printGoogleClientId() {
    System.out.println("Method 1:========================================= " + environ.getProperty("GOOGLE_CLIENT_ID_TEST"));
    System.out.println("Method 2:========================================= " + googleClientId);
  }

  // alternative to @PostConstruct. This runs after app has started
  @Bean
  ApplicationRunner runner() {
    return args -> { // if you ran this app with args like myArg1=111 myArg2=222, it'll be an array
                     // of strings ["myArg1=111", "myArg2=222"]
      System.out.println(
          "Method 3 in runner::::::::::::::::::::::::::::::::: " + environ.getProperty("GOOGLE_CLIENT_ID_TEST"));
      System.out.println("Method 4 in runner::::::::::::::::::::::::::::::::: " + googleClientId);
      System.out.println("These are args: " + Arrays.toString(args.getSourceArgs()));
    };
  }
}
