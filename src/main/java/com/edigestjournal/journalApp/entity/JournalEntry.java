package com.edigestjournal.journalApp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

 @Document(collection = "journal_entries")//tells that this entity is used to map  an entity in mongo and each of its instance is like a row

 @Data
 @NoArgsConstructor//equivalent  to getter setter Value ToString RequiredArgsConstuctor etc
 public class JournalEntry {
     @Id
     @JsonSerialize(using = ToStringSerializer.class) // @JsonSerialize(using = ToStringSerializer.class)    // Refer Stackoverflow issue: https://stackoverflow.com/questions/64723249/java-mongodb-getting-id-as-a-timestamp-but-need-the-hexadecimal-string
     private ObjectId id;
      @NonNull
     private String title;

     private String content;
     private LocalDateTime date;

     public void setDate(LocalDateTime date) {
         this.date = date;
     }

 }
