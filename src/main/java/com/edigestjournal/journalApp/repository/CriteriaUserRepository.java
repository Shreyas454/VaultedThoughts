package com.edigestjournal.journalApp.repository;

import com.edigestjournal.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CriteriaUserRepository {

    public List<User> withEmail;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<User> retract(){

        withEmail = new ArrayList<>();
        Query query = new Query();

//        query.addCriteria(Criteria.where("email").exists(true));
        Criteria criteria2 = Criteria.where("HasOpted").is(true); // this is how we create a criteria object instance , we can add only one condition while declarartion/initiation


        Criteria criteria1 = new Criteria();
        query.addCriteria(criteria1.andOperator(
                Criteria.where("email").regex("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$", "i"),
                criteria2
        ));


       List<User> response =  mongoTemplate.find(query,User.class);//here the mongotemlete figures out which class to query from the collection mention in the user entity

       for(User resp : response){
           withEmail.add(resp);
       }

       return withEmail;
    }
}
// Criteria is a class from Spring Data MongoDB used to define query conditions.
// Criteria.where("field") is a static method used to start a new condition.
// It returns a Criteria object instance on which instance methods like .is(), .exists(), .and() can be chained.
// Static methods are called on the class (e.g., Criteria.where()), while instance methods are called on objects.
// Example: Criteria.where("username").is("Shreyas").and("email").exists(true);
