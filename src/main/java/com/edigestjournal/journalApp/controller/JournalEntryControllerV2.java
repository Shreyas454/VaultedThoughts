package com.edigestjournal.journalApp.controller;


import java.util.*;
import java.util.stream.Collectors;

import com.edigestjournal.journalApp.entity.User;
import com.edigestjournal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigestjournal.journalApp.entity.JournalEntry;
import com.edigestjournal.journalApp.service.JournalEntryService;

@RestController
@RequestMapping("/journalV2")//now parent will be the main path of any getmapping in this class
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;
//   @GetMapping("/{username}")
//   public ResponseEntity<?> giveAllJournalEntriesofUser(@PathVariable String username){
//       User user =userService.findByUsername(username);
//       List<JournalEntry> all = user.getJournalEntryList();
////       if(all.isEmpty()  && all != null){
////           return new ResponseEntity<>(all, HttpStatus.OK);
////       }
////       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//       return new ResponseEntity<>(all, HttpStatus.OK);
//   }
@GetMapping
public ResponseEntity<?> giveAllJournalEntriesofUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    User user =userService.findByUsername(username);
    List<JournalEntry> all = user.getJournalEntryList();
//       if(all.isEmpty()  && all != null){
//           return new ResponseEntity<>(all, HttpStatus.OK);
//       }
//       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(all, HttpStatus.OK);
}


//    @PostMapping("/{username}")
//    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String username){ // localhost:8080/journal POST
//       try {
//
//             myEntry.setId(ObjectId.get());
//            journalEntryService.saveEntry(myEntry ,username);
//            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
//        } catch (Exception e) {
//           return new ResponseEntity<>(myEntry, HttpStatus.BAD_REQUEST);
//       }
//    }

    @PostMapping
    public ResponseEntity<?> createNewEntry(@RequestBody JournalEntry newEntry){
      try  {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//          System.out.print(authentication);

            String username = authentication.getName();
            User user = userService.findByUsername(username);
            newEntry.setId(ObjectId.get());
            journalEntryService.saveEntry(newEntry, username);
            return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
        } catch (Exception e) {
          return  new ResponseEntity<>(newEntry , HttpStatus.BAD_REQUEST);
      }
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJounalEntrybyId(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//          System.out.print(authentication);

        String username = authentication.getName();
        User user = userService.findByUsername(username);
       List<JournalEntry> matched =   user.getJournalEntryList().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if( !matched.isEmpty()){

            // MY method working but slightly in efficient
//           ObjectId req = matched.get(0).getId();
//            Optional<JournalEntry> ret =  journalEntryService.findById(req);
//            return new ResponseEntity<>(ret,HttpStatus.OK);

            //since the List matched is not empty it id must match with myId so just use myID
            Optional<JournalEntry> ret = journalEntryService.findById(myId);
            return new ResponseEntity<>(ret,HttpStatus.OK);
        }

//       Optional<JournalEntry> JournalEntry =  journalEntryService.findById(myId);
//        if(JournalEntry.isPresent()){
//            return new ResponseEntity<>(JournalEntry.get(), HttpStatus.OK);
//        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJounalEntrybyId(@PathVariable ObjectId myId){ //<?> indicates Object can be any type
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     String username = authentication.getName();
         journalEntryService.deleteById(myId,username);
        return new ResponseEntity<>(HttpStatus.ACCEPTED) ;
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> putJounalEntrybyId(@PathVariable ObjectId myId , @RequestBody JournalEntry entry){
//        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> matched =   user.getJournalEntryList().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if( !matched.isEmpty()){

            // MY method working but slightly in efficient
//           ObjectId req = matched.get(0).getId();
//            Optional<JournalEntry> ret =  journalEntryService.findById(req);
//            return new ResponseEntity<>(ret,HttpStatus.OK);

            JournalEntry old = matched.get(0);
            old.setTitle(entry.getTitle() != null && !entry.getTitle().equals("")? entry.getTitle():old.getTitle());
            old.setContent(entry.getContent() != null && !entry.getContent().equals("")?entry.getContent(): old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
//        if(old != null){
//           old.setTitle(entry.getTitle() != null && !entry.getTitle().equals("")? entry.getTitle():old.getTitle());
//           old.setContent(entry.getContent() != null && !entry.getContent().equals("")?entry.getContent(): old.getContent());
//            journalEntryService.saveEntry(old);
//            return new ResponseEntity<>(old,HttpStatus.OK);
//        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


//2. Jackson's Deserialization Process:
//Jackson examines the JSON keys (id, title, content) and tries to match them with the fields in the JournalEntry class.
//Since the fields in JournalEntry are private, Jackson cannot directly access them. Instead, it looks for:
//Setters: Methods named setId, setTitle, and setContent.
//Constructors: A constructor with parameters matching the fields.
//Jackson uses reflection to call these setters or constructors to populate the fields.
    //What is Jackson?
    //Jackson is a high-performance Java library for processing JSON data. It is widely used in Spring Boot to handle serialization and deserialization of JSON.
    //
    //Serialization: Converts Java objects to JSON (e.g., sending objects in HTTP responses).
    //Deserialization: Converts JSON to Java objects (e.g., parsing incoming JSON payloads in HTTP requests).
    //In the context of Spring Boot, Jackson is typically included as part of the Spring Web dependency (spring-boot-starter-web) and automatically handles JSON processing for @RestController endpoints.
}
