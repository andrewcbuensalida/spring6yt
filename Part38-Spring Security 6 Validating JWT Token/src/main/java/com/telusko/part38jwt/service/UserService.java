package com.telusko.part38jwt.service;

import com.telusko.part38jwt.model.Users;
import com.telusko.part38jwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private JWTService jwtService;

  @Autowired
  AuthenticationManager authManager;

  @Autowired
  private UserRepo repo;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  public Users register(Users user) {
    // Check if user already exists
    if (repo.findByUsername(user.getUsername()) != null) {
      throw new IllegalArgumentException("User already exists");
    }
    user.setPassword(encoder.encode(user.getPassword()));
    repo.save(user);
    return user;
  }

  // not being used because POST /login is useless
  public String verify(Users user) {
    // bcrypt decodes the password and checks if it matches the one in the database
    Authentication authentication = authManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    if (authentication.isAuthenticated()) {
      return jwtService.generateToken(user.getUsername());
    } else {
      return "fail";
    }
  }
}
