package com.pthoma.personService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pthoma.personService.ResponseMsg;
import com.pthoma.personService.entity.Person;
import com.pthoma.personService.service.PersonService;

@RestController
@RequestMapping("/personService")
public class PersonServiceController {

	@Autowired
	private PersonService personService;

	// Get a single Person with an given ID
	@RequestMapping(method = RequestMethod.GET, path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMsg> getPerson(@PathVariable String id) {
		return personService.getById(id);
	}

	// Get all available Persons from the Database, returning them as an PersonArray
	@RequestMapping(method = RequestMethod.GET, path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMsg> getAllPersons() {
		return personService.getAllPersons();
	}

	// Update a Person
	@RequestMapping(method = RequestMethod.PUT, path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMsg> updatePerson(@RequestBody Person person) {
		return personService.updatePerson(person);
	}

	// Delete a Person
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMsg> deletePerson(@RequestBody Person person) {
		return personService.deletePerson(person);
	}

	// Create a new person with a name
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMsg> createPerson(@RequestBody Person person) {
		return personService.createPerson(person);
	}

}
