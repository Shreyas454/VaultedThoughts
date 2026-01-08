package com.edigestjournal.journalApp.Scheduller;


import com.edigestjournal.journalApp.entity.JournalEntry;
import com.edigestjournal.journalApp.entity.User;
import com.edigestjournal.journalApp.repository.CriteriaUserRepository;
import com.edigestjournal.journalApp.service.EmailService;
import com.edigestjournal.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private CriteriaUserRepository criteriaUserRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

//    @Scheduled(cron = "*/10 * * * * ? ") just for testing once every 10 secs
    @Scheduled(cron = "0 5 18 ? * THU  ")
    public void fetchUsersAndSendSAMails(){

       List<User> users = criteriaUserRepository.retract();

       for(User user : users){
           List<JournalEntry> journalEntries = user.getJournalEntryList();
           List<String> filteredContent =   journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());
           String entry = String.join(" ",filteredContent);
           String sentiment = sentimentAnalysisService.getSentiment(entry);
//           System.out.println("Hiiii");
           emailService.sendEmail(user.getEmail(),sentiment,"Here is Your weekly Report");
       }
    }
}
