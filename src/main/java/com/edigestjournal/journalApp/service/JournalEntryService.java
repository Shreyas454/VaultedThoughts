package com.edigestjournal.journalApp.service;


import com.edigestjournal.journalApp.entity.JournalEntry;
import com.edigestjournal.journalApp.entity.User;
import com.edigestjournal.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import java.util.Optional;

//controller --> service --> repository

@Service    // Refer: https://github.com/tirthraj07/Spring-Boot/tree/main/mongoDB
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;




    /*
        While it works, @Autowired is not preferred. Use constructor based dependency injection for good practice
        Refer: https://github.com/tirthraj07/Spring-Boot/tree/main/configuration_class
     */

    // Services

    // changed void -> JournalEntry. Always return the newly created JournalEntry
    @Transactional //either entire method works or everything is rolled back(no command executes)
    public void saveEntry(JournalEntry journalEntry, String username){
        User user = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry save = journalEntryRepository.save(journalEntry);
        user.getJournalEntryList().add(save);
        userService.saveEntry(user);
    }

    public void saveEntry(JournalEntry journalEntry){

        journalEntryRepository.save(journalEntry);
    }

    // Change ArrayList<JournalEntry> to List<JournalEntry> to avoid unnecessary data conversion
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    // changed deleteById to deleteById. use CamelCase.
    @Transactional
    public void deleteById(ObjectId id, String username){
        try{
            User user = userService.findByUsername(username);
            boolean present = user.getJournalEntryList().removeIf(journalEntry -> journalEntry.getId().equals(id));//Journal entry is a variable that can represent any journal entry in the list   delete if return a boolean
            userService.saveEntry(user);
            if (present == true) {
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
