package com.edigestjournal.journalApp.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")//tells that this entity is used to map  an entity in mongo and each of its instance is like a row

@Builder
@Data   //equivalent  to getter setter Value ToString RequiredArgsConstuctor etc
public class User {

  @Id
  @JsonSerialize(using = ToStringSerializer.class)    // Refer Stackoverflow issue: https://stackoverflow.com/questions/64723249/java-mongodb-getting-id-as-a-timestamp-but-need-the-hexadecimal-string
  private ObjectId id;

  @Indexed(unique = true)
  @NonNull
  private String username;

@NonNull
  private String password;

  private String email;

  private Boolean HasOpted;
  @DBRef
  private List<JournalEntry> journalEntryList =new ArrayList<>();
 private List<String> roles;

  public List<JournalEntry> getJournalEntryList() {
    return this.journalEntryList;
  }

}
