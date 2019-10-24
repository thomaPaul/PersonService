package com.pthoma.personService.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pthoma.personService.entity.Person;

public interface PersonRepository extends MongoRepository<Person, String> {

}
