package com.telusko.part38jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtFilter jwtFilter;

  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // customizer.disable() is used to disable csrf token so you don't have to
    // attach it in the headers in Postman.
    return http.csrf(customizer -> customizer.disable()).authorizeHttpRequests(request -> request
        .requestMatchers("login", "register").permitAll() // this makes login and register urls accessible even without
                                                          // authentication
        .anyRequest().authenticated()) // any other url path needs authentication
        // httpBasic is used to authenticate the user from Postman. In Postman, we have
        // to select Basic Auth and enter the username and password. For the browser it
        // would be formLogin, which is a built-in login screen, but can't do formLogin
        // because when you submit the login, it'll be a new request, which will be
        // another session token, so have to do sessionManagement below. This will
        // create a login popup.
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // this makes the jwtFilter first. If
                                                                                // jwtFilter passes authentication, it
                                                                                // won't need to do
                                                                                // UsernamePasswordAuthenticationFilter
        .build();

  }

  // instead of using the default spring.security.user.name and
  // spring.security.user.password, we can use the below code to create our own
  // users.
  // @Bean
  // public UserDetailsService userDetailsService() {
  //
  // UserDetails user1 = User
  // .withDefaultPasswordEncoder() is deprecated.
  // .withDefaultPasswordEncoder()
  // .username("kiran")
  // .password("k@123")
  // .roles("USER")
  // .build();
  //
  // UserDetails user2 = User
  // .withDefaultPasswordEncoder()
  // .username("harsh")
  // .password("h@123")
  // .roles("ADMIN")
  // .build();

  // return new InMemoryUserDetailsManager(user1, user2);
  // }

  // when logging in, this will convert the password to the hash
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    provider.setUserDetailsService(userDetailsService);

    return provider;
  }

  // AuthenticationManager calls AuthenticationProvider above. This is for custom login.
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();

  }

}
