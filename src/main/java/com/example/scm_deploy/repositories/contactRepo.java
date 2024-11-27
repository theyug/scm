package com.example.scm_deploy.repositories;

import com.example.scm_deploy.entities.Contact;
import com.example.scm_deploy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface contactRepo extends JpaRepository<Contact , String> {
    List<Contact> findByUser(User user);
    //custom query method
    @Query("Select c from Contact c where c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId")String userId);
}
