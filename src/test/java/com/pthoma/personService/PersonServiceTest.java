package com.pthoma.personService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pthoma.personService.entity.Person;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MongoTemplate mongoTemplate;

	public void clearMongo() {
		mongoTemplate.getDb().drop();

	}

	@Test
	public void testCreatePerson() throws Exception {
		clearMongo();
		mockMvc.perform(MockMvcRequestBuilders.post("/personService/create").content(createPersonJson("Paul", ""))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody[0].name").value("Paul"));
	}

	@Test
	public void testCreatePersonNameShort() throws Exception {
		clearMongo();
		mockMvc.perform(MockMvcRequestBuilders.post("/personService/create").content(createPersonJson("P", ""))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
	}

	@Test
	public void testCreatePersonNameLong() throws Exception {
		clearMongo();
		mockMvc.perform(MockMvcRequestBuilders.post("/personService/create")
				.content(createPersonJson("PaulNikolaiJonathanThoma", "")).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
	}

	@Test
	public void testGetPerson() throws Exception {
		clearMongo();

		mockMvc.perform(MockMvcRequestBuilders.post("/personService/create").content(createPersonJson("Paul", ""))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		mockMvc.perform(MockMvcRequestBuilders.get("/personService/getAll").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody[0].name").value("Paul"));
	}

	@Test
	public void testGetPersonById() throws Exception {
		clearMongo();
		String id = "testGetPersonById";
		mockMvc.perform(MockMvcRequestBuilders.post("/personService/create").content(createPersonJson("Paul", id))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		mockMvc.perform(MockMvcRequestBuilders.get("/personService/get/" + id).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody[0].name").value("Paul"));
	}

	@Test
	public void testGetPersonByIdInvalid() throws Exception {
		clearMongo();

		mockMvc.perform(MockMvcRequestBuilders.get("/personService/get/" + "123").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
	}

	@Test
	public void testDeletePerson() throws Exception {
		clearMongo();
		String name = "Hans";
		String id = "TestId1234";
		mockMvc.perform(MockMvcRequestBuilders.post("/personService/create").content(createPersonJson(name, id))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		mockMvc.perform(MockMvcRequestBuilders.delete("/personService/delete").content(createPersonJson(name, id))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isEmpty());
	}

	@Test
	public void testDeleteInvalidPerson() throws Exception {
		clearMongo();

		mockMvc.perform(MockMvcRequestBuilders.delete("/personService/delete").content(createPersonJson("", ""))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
	}

	@Test
	public void testUpdatePerson() throws Exception {
		clearMongo();
		String name = "Hans";
		String secondName = "Holger";
		String id = "TestId1234";
		mockMvc.perform(MockMvcRequestBuilders.post("/personService/create").content(createPersonJson(name, id))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		mockMvc.perform(MockMvcRequestBuilders.put("/personService/update").content(createPersonJson(secondName, id))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseBody[0].name").value(secondName));
		System.out.println("test");
	}

	@Test
	public void testUpdateInvalidPerson() throws Exception {
		clearMongo();

		mockMvc.perform(MockMvcRequestBuilders.put("/personService/update").content(createPersonJson("Hans", "123"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
		System.out.println("test");
	}

	public String createPersonJson(String name, String id) throws JsonProcessingException {
		Person person = new Person(name);
		if (StringUtils.isNotEmpty(id)) {
			person.setId(id);
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(person);
	}

}
