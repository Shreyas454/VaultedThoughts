package com.edigestjournal.journalApp.controller;

import com.edigestjournal.journalApp.entity.User;
import com.edigestjournal.journalApp.repository.CriteriaUserRepository;
import com.edigestjournal.journalApp.service.EmailService;
import com.edigestjournal.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
   private UserService userService;

    @Autowired
    private CriteriaUserRepository criteriaUserRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<?> getAllusers(){
        try{
            List<User> ret = userService.getAll();

            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("bad req",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/willing")
    public ResponseEntity<?> getEmailUsers(){
        try{
            List<User> ret = criteriaUserRepository.retract();

            return new ResponseEntity<>(ret,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went Wrong",HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/mail")
//    public  ResponseEntity<?> sendMail(){
//        try{
//            emailService.sendEmail();
//
//            return new ResponseEntity<>("Success",HttpStatus.OK);
//
//        } catch (Exception e)  {
//            return new ResponseEntity<>("something went wrong",HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody User user){
       try {
            userService.saveNewAdmin(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
       }
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> authorizeUser(@PathVariable String username){
        User user = userService.findByUsername(username);

        user.setRoles(Arrays.asList("ADMIN","ROLES_USER"));
        userService.setRoles(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/deauth/{username}")
    public ResponseEntity<?> deauthorizeUser(@PathVariable String username){
        User user = userService.findByUsername(username);
        List<String> roles = user.getRoles();
        roles.remove("ADMIN");

        userService.setRoles(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
//@PreAuthorize("hasRole('ADMIN')") // Restrict to admins only   usefull annotation that can be used for role based authorization directly in controllerapplication.properties