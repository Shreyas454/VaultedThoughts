package com.edigestjournal.journalApp.repository;


import com.edigestjournal.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;




public interface UserRepository extends MongoRepository<User, ObjectId> {

 //query method dsl
 User findByUsername(String username);
// Optional<User> findById(ObjectId Id);
}
