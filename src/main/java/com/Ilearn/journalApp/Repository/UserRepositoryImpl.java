package com.Ilearn.journalApp.Repository;

import com.Ilearn.journalApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl {


    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getusersforSA(){
        Query query = new Query();
        //query.addCriteria(Criteria.where("username").is("Sou"));
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
        query.addCriteria(Criteria.where("SentimentAnalysis").is(true));
        List<User> users= mongoTemplate.find(query, User.class);
        return users;
    }
}
