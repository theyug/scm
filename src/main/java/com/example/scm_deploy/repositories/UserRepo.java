package com.example.scm_deploy.repositories;


import com.example.scm_deploy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public  interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByemail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}
