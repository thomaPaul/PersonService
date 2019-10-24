package com.pthoma.personService.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

public class Person extends Object {

	@Id
	private String id;

	@Length(min = 3, max = 20)
	private String name;

	public Person(String name) {
		this.name = name;
	}

	public Person() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
