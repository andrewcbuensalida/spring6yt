package com.telusko.springoauth2demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // So it won't do the default security
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth.requestMatchers("/","/application/default",
        "/ecom-proj/default").permitAll() // this makes these endpoints accessible without authentication. These endpoints are for cloud config
        .anyRequest().authenticated())
        .oauth2Login(Customizer.withDefaults());
    return http.build();

  }
}
