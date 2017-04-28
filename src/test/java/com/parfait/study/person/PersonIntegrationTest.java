package com.parfait.study.person;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomListOf;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parfait.study.person.repository.PersonRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonIntegrationTest {
	@LocalServerPort
	int port;
	Person person;
	List<Person> people;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	PersonRepository personRepository;

	@Before
	public void setUp() throws Exception {
		RestAssured.port = port;
		person = random(Person.class, "seq");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void create() throws Exception {
		Person willBeSaved = person;

		RestAssured.
				given().
				contentType(ContentType.JSON).
				body(willBeSaved).

				when().
				post("/api/people").

				then().
				statusCode(is(HttpStatus.CREATED.value())).
				body("name", is(willBeSaved.name)).
				body("age", is(willBeSaved.age));
	}

	@Test
	public void readOne() throws Exception {
		savePerson(person);

		RestAssured.
				when().
				get("/api/people/{seq}", person.seq).

				then().
				statusCode(is(HttpStatus.OK.value())).
				body("name", is(person.name)).
				body("age", is(person.age));
	}

	private void savePerson(Person person) {
		personRepository.save(person);
	}

	@Test
	public void readList() throws Exception {
		int size = 10;
		savePeople(size);
		RestAssured.
				when().
				get("/api/people").

				then().
				statusCode(is(HttpStatus.OK.value())).
				body("_embedded.people", Matchers.hasSize(size));
	}

	private void savePeople(int size) {
		List<Person> people = randomListOf(size, Person.class, "seq");
		people = personRepository.save(people);
	}

	@Test
	public void update() throws Exception {
		savePerson(person);

		Person willBeUpdated = new Person();
		willBeUpdated.seq = person.seq;
		willBeUpdated.name = "updated";

		RestAssured.
				given().
				contentType(ContentType.JSON).
				body(willBeUpdated).

				when().
				put()
		// given
		// when

		//then
	}
}
