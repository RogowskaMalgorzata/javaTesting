package com.example.restservicedemo;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.example.restservicedemo.domain.Person;
import com.jayway.restassured.RestAssured;

public class PersonServiceTest {
	
	private static final String PERSON_FIRST_NAME = "Jasiu";
	Person person, person2, person3;
	
	@BeforeClass
    public static void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api";   	
    }
	
	@Before
	public void setPersons() {
		get("/person/drop").then().assertThat().statusCode(200);
		
		person = new Person(PERSON_FIRST_NAME, 1976);
		person2 = new Person("Jan", 1976);
		person3 = new Person(PERSON_FIRST_NAME, 1980);
	}
	
	@Test
	public void addPersons(){	
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person).
	    when().	     
	    post("/person/").then().assertThat().statusCode(201);
				
		Person rPerson = get("/person/1").as(Person.class);
		assertThat(PERSON_FIRST_NAME, equalToIgnoringCase(rPerson.getFirstName()));
		
	}
	
	@Test
	public void getAllPersons(){
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person).
	    when().	     
	    post("/person/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person2).
	    when().	     
	    post("/person/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person3).
	    when().	     
	    post("/person/").then().assertThat().statusCode(201);
				
		Person rPerson = get("/person/2").as(Person.class);
		assertThat("Jan", equalToIgnoringCase(rPerson.getFirstName()));
		
		Person[] rPersons = get("/person/all").as(Person[].class);
		List<Person> persons = Arrays.asList(rPersons);
		assertEquals(3, persons.size());	
	}
	
	@Ignore
	@Test
	public void deleteAllPersons() {
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person).
	    when().	     
	    post("/person/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person2).
	    when().	     
	    post("/person/").then().assertThat().statusCode(201);
		
		delete("/person/").then().assertThat().statusCode(200);
		
		Person[] rPersons = get("/person/all").as(Person[].class);
		List persons = new ArrayList<Person>(Arrays.asList(rPersons));
		assertEquals(0, persons.size());
	}
	
}
