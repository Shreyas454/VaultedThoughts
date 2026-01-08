package com.edigestjournal.journalApp.controller;



import com.edigestjournal.journalApp.api.response.GitHubResponse;
import com.edigestjournal.journalApp.api.response.WeatherResponse;
import com.edigestjournal.journalApp.entity.User;
import com.edigestjournal.journalApp.service.GitHubService;
import com.edigestjournal.journalApp.service.UserService;
import com.edigestjournal.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")//now parent will be the main path of any getmapping in this class
public class UserController {

    // Use Constructor based dependency injection for good practice.
    @Autowired
    private UserService userService;
    @Autowired
    private WeatherService weatherService;


    private final GitHubService gitHubService = new GitHubService();

    // REST APIs

    // do better naming of function. wtf is "giveAll"
//    @GetMapping    //commented here because only Admin must have acess to data of all users
//    public List<User> giveAll(){
//        return userService.getAll();
//    }

    // changing void -> ResponseEntity. The post request wouldn't return anything,
    @PostMapping
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


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           System.out.print(authentication);
       String username =  authentication.getName();
      User DBuser = userService.findByUsername(username);
          if(DBuser == null){
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
          DBuser.setUsername(user.getUsername());
          DBuser.setPassword(user.getPassword());
          userService.saveNewUser(DBuser);
          return new ResponseEntity<>(HttpStatus.OK);


    }

    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greetings = " ";
        if(weatherResponse != null){
            int feelsLike = weatherResponse.getCurrent().getFeelslike();
            greetings = " Weather is rated to be  " + feelsLike ;
        }
        System.out.println("API Response: " + weatherResponse);

        return new ResponseEntity<>("Hi " + authentication.getName() + " " + greetings, HttpStatus.OK); // the bug was related to the restTemplte instance not being created and thus not being inserted in the service while execution

//        GitHubResponse gitHubResponse = gitHubService.getInfo();
//        if(gitHubResponse != null){
//            return new ResponseEntity<>("Hi " + gitHubResponse.login, HttpStatus.OK);
//        }else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }
}
