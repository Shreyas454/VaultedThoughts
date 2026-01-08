package com.edigestjournal.journalApp.service;


import com.edigestjournal.journalApp.entity.User;
import com.edigestjournal.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger; //slf4j is a logging abstraction framework
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//controller --> service --> repository
@Service
@Slf4j
public class UserService {


    // changed userrespository to userRepository. use CamelCase.
    // use Constructor-based Dependency Injection for good practice.
    @Autowired
    private UserRepository userRepository;

    //every logger is associated with a class
//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // Services

    // Changed void -> User : Always return the newly created Object in DB -> Good Practice
    public User saveEntry(User user){
        return userRepository.save(user);
    }

    public User saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("ROLES_USER"));
            userRepository.save(user);
            return user;
        }catch (Exception e){
            log.info("hahahahahha" + e);//we use log instance when using slf4j
            log.warn("hahahhahaaha");
            log.error("hahhhahahhahha");
            log.debug("hahhhhahahhaaaha");
            log.trace("hhahahhahhhahahaha");
//            logger.info("hahahahahha" + e);
//            logger.warn("hahahhahaaha");
//            logger.error("hahhhahahhahha");
//            logger.debug("hahhhhahahhaaaha");
//            logger.trace("hhahahhahhhahahaha");
            return null;
        }

    }

    public User saveNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN","ROLES_USER"));
        userRepository.save(user);
        return user;
    }
    // Changed ArrayList<User> to List<User> to avoid unnecessary data type conversion
    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User setRoles(User user){
        User existingUser = userRepository.findByUsername(user.getUsername());
        existingUser.setRoles(user.getRoles());
        userRepository.save(existingUser);
        return user;


    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    // changed deletebyid to deleteById. Use CamelCase
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
