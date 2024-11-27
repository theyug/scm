package com.scm.services.impl;

import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;

@Service
public class UserserviceImpl  implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());


    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
   logger.info(user.getProvider().toString());
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserByid(String id) {
        return Optional.empty();
    }


    @Override
    public Optional<User> updateUser(User user) {
       User user2 = userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("resource not found"));
       user2.setName(user.getName());
       user2.setEmail(user.getEmail());
       user2.setPassword(user.getPassword());
       user2.setAbout(user.getAbout());
       user2.setPhoneNumber(user.getPhoneNumber());
//       user2.setProfilepic(user.getProfilepic());
//       user2.setProfilepic(user.getProfilepic());
       user2.setEnabled(user.isEnabled());
//       user2.setEmailverify(user.isEmailverify());
//       user2.setPhoneverify(user.isPhoneverify());
       user2.setProvider(user.getProvider());
       user2.setProviderUserId(user.getProviderUserId());
       User save = userRepo.save(user2);
       return Optional.ofNullable(save);

    }

    @Override
    public void deleteUser(String id) {
    User user2 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userid) {
         User user2 = userRepo.findById(userid).orElse(null);
         return user2 != null? true:false;
    }

    @Override
    public boolean isUserExistbyEmail(String email) {
       User user = userRepo.findByemail(email).orElse(null);
        return user  != null? true:false;
    }


    @Override
    public List<User> getallusers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return  userRepo.findByemail(email).orElseThrow(null);
    }
}
