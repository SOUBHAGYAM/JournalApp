package com.Ilearn.journalApp.Repository;

import com.Ilearn.journalApp.Entity.ConfigJournalAppEntity;
import com.Ilearn.journalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {


}
