package com.pthoma.personService.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pthoma.personService.ResponseMsg;
import com.pthoma.personService.database.PersonRepository;
import com.pthoma.personService.entity.Person;

@Component
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	private final Logger logger = LoggerFactory.getLogger(PersonService.class);

	// Checks if the database has a Person with the given ID, returning it if it is
	// present
	public ResponseEntity<ResponseMsg> getById(String id) {
		Optional<Person> optPerson = personRepository.findById(id);
		if (optPerson.isPresent()) {
			return createResponse(HttpStatus.OK, optPerson.get());
		}
		String errorMsg = "Could not find Person with id " + id;
		logger.error(errorMsg);
		return createResponse(HttpStatus.BAD_REQUEST, "Could not find Person with id " + id);
	}

	// Checks if the given Person is in the database, deleting it
	public ResponseEntity<ResponseMsg> deletePerson(Person person) {
		if (personRepository.exists(Example.of(person))) {
			try {
				personRepository.delete(person);
			} catch (IllegalStateException e) {
				logger.error(e.getMessage());
				return createResponse(HttpStatus.BAD_REQUEST, person, e.getMessage());
			}
			return createResponse(HttpStatus.OK, person);
		}

		return personNotFound(person);
	}

	// Checks if the given Person is in the database, updating it
	public ResponseEntity<ResponseMsg> updatePerson(Person person) {
		Optional<Person> optPerson = personRepository.findById(person.getId());
		if (optPerson.isPresent()) {
			return createResponse(HttpStatus.OK, personRepository.save(person));
		}

		return personNotFound(person);
	}

	// Creating a new Person, returning an error if the constraints are not matched
	public ResponseEntity<ResponseMsg> createPerson(Person person) {
		try {
			return createResponse(HttpStatus.CREATED, personRepository.insert(person));
		} catch (ConstraintViolationException e) {
			logger.error(e.getMessage());
			return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	private ResponseEntity<ResponseMsg> personNotFound(Person person) {
		String errorMsg = "Could not find Person with id " + person.getId();
		logger.error(errorMsg);
		return createResponse(HttpStatus.BAD_REQUEST, person, errorMsg);
	}

	public ResponseEntity<ResponseMsg> getAllPersons() {
		return createResponse(HttpStatus.OK, personRepository.findAll());
	}

	private ResponseEntity<ResponseMsg> createResponse(HttpStatus status, List<Person> list) {
		return new ResponseEntity<ResponseMsg>(new ResponseMsg(new ArrayList<Object>(list), ""), status);
	}

	private ResponseEntity<ResponseMsg> createResponse(HttpStatus status, Person person) {
		return new ResponseEntity<ResponseMsg>(new ResponseMsg(Arrays.asList(person), ""), status);
	}

	private ResponseEntity<ResponseMsg> createResponse(HttpStatus status, Person person, String errorMessage) {
		return new ResponseEntity<ResponseMsg>(new ResponseMsg(Arrays.asList(person), errorMessage), status);
	}

	private ResponseEntity<ResponseMsg> createResponse(HttpStatus status, String errorMessage) {
		return new ResponseEntity<ResponseMsg>(new ResponseMsg(new ArrayList<Object>(), errorMessage), status);
	}

}
