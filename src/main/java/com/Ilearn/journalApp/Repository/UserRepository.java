package com.Ilearn.journalApp.Repository;

import com.Ilearn.journalApp.Entity.JournalEntry;
import com.Ilearn.journalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

     User findByUsername(String username);

    void deleteByUsername(String name);
}
