package com.edigestjournal.journalApp.repository;

import com.edigestjournal.journalApp.entity.ConfigJournalApp;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Config_JournalAppRespository extends MongoRepository<ConfigJournalApp, ObjectId> {
   ConfigJournalApp findByKey(String key);
}
