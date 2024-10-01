package com.telusko.part38jwt.repo;

import com.telusko.part38jwt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

//    since the name of the method is findByUsername, Spring Data JPA will automatically generate the query for us
    Users findByUsername(String username);
}
