package com.edigestjournal.journalApp.controller;

import com.edigestjournal.journalApp.entity.User;
import com.edigestjournal.journalApp.service.CustomUserDetailServiceimpl;
import com.edigestjournal.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.edigestjournal.journalApp.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController // what ever will be returned will be converted into a json
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailServiceimpl customUserDetailServiceimpl;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // do at least BASIC exception handling
        try {
            User newUser = userService.saveNewUser(user);

            Map<String, Object> response = new HashMap<>(){{
                put("status","success");
                put("message", "User created successfully");
                put("user", newUser);
            }};
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>(){{
                put("status","success");
                put("message", e.getMessage());
            }};
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
       try{
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
          UserDetails userDetails =  customUserDetailServiceimpl.loadUserByUsername(user.getUsername());
          String jwt = jwtUtil.generateToken(userDetails.getUsername());
          return  new ResponseEntity<>(jwt,HttpStatus.OK);
       }catch (Exception e){
          return new ResponseEntity<>("Wrong Username or password",HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/healthcheck")
    public String healthcheck(){
        return "Ok";
    }

    @GetMapping("/redistest")
    public ResponseEntity<?> check(){
        redisTemplate.opsForValue().set("email","shreyasnagarkar454@gmail.com");

        Object ret = redisTemplate.opsForValue().get("email");

        return new ResponseEntity<>(ret,HttpStatus.OK);
    }
}
