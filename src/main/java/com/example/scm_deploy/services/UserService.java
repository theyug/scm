package com.scm.services;

import com.scm.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserByid(String id);

    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String userid);
    boolean isUserExistbyEmail(String email);
    List<User> getallusers();
    User getUserByEmail(String email);

}
