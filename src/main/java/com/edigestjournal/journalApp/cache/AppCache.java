package com.edigestjournal.journalApp.cache;

import com.edigestjournal.journalApp.entity.ConfigJournalApp;
import com.edigestjournal.journalApp.repository.Config_JournalAppRespository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component  //declared as a bean
public class AppCache {

    public Map<String,String> App_Cache = new HashMap<>();

    @Autowired
    private Config_JournalAppRespository configJournalAppRespository;

    @Scheduled(cron = "*/10 * * * * ? ")
    @PostConstruct      // will be run as soon as the bean is created
    public void init(){

        List<ConfigJournalApp> store = configJournalAppRespository.findAll();
        for(ConfigJournalApp configJournalApp : store){
            App_Cache.put(configJournalApp.getKey(),configJournalApp.getValue());
        }


    }
}
