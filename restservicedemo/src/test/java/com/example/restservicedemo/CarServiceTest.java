package com.example.restservicedemo;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;
import com.example.restservicedemo.service.CarManager;
import com.jayway.restassured.RestAssured;

public class CarServiceTest {
	
	private static final String PERSON_FIRST_NAME = "Jasiu";
	
	@BeforeClass
	public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api";
	}
	
	@Test
	public void addCar(){
		delete("/person/").then().assertThat().statusCode(200);
		delete("/car/").then().assertThat().statusCode(200);
		
		Person p = new Person(1L, PERSON_FIRST_NAME, 1976);
		Car car = new Car(1L, "Ford", "Fiesta", 2011, p);
		
		given().
	       	contentType("application/json").
	       	body(p).	     
        post("/person/").then().assertThat().statusCode(201);
		
		given().
	       	contentType("application/json").
	       	body(car).	     
		post("/car/").then().assertThat().statusCode(201);
		
		Car rCar = get("/car/1").as(Car.class);
		
		assertThat("Fiesta", equalToIgnoringCase(rCar.getModel()));
	}
	
	@Test
	public void getAllCars(){
		CarManager cm = new CarManager();
		
		delete("/person/").then().assertThat().statusCode(200);
		delete("/car/").then().assertThat().statusCode(200);
		
		Person p = new Person(1L, PERSON_FIRST_NAME, 1976);
		
		Car car = new Car(1L, "Ford", "Fiesta", 2011, p);
		Car car2 = new Car(2L, "Ford", "Fiesta", 2011, p);
		Car car3 = new Car(3L, "Fiat", "Punto", 2011, p);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(p).
	    when().	     
	    post("/person/").then().assertThat().statusCode(201);
				
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car).
	    when().	     
	    post("/car/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car2).
	    when().	     
	    post("/car2/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car3).
	    when().	     
	    post("/car3/").then().assertThat().statusCode(201);
				
		Car rCar = get("/car/3").as(Car.class);
		
		assertEquals("Fiat", rCar.getMake());
		assertEquals(3, cm.getAllCars().size());	
	}

}
