package com.example.restservicedemo;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;
import com.example.restservicedemo.domain.Purchase;
import com.jayway.restassured.RestAssured;

public class CarServiceTest {
	
	static Person person;
	static Car car, car2, car3;
	
	@BeforeClass
	public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api";
		
		person = new Person("Jasiu", 1976);
		car = new Car("Ford", "Fiesta", 2011);
		car2 = new Car("Ford", "Fiesta", 2011);
		car3 = new Car("Fiat", "Punto", 2011);
	}
	
	@Test
	public void addCar(){
		given().
	       	contentType("application/json").
	       	body(car).	     
		post("/car/").then().assertThat().statusCode(201);
		
		Car rCar = get("/car/1").as(Car.class);
		
		assertThat("Fiesta", equalToIgnoringCase(rCar.getModel()));
	}
	
	@Test
	public void getAllCars(){
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car).
	    when().	     
	    post("/car/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car2).
	    when().	     
	    post("/car/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car3).
	    when().	     
	    post("/car/").then().assertThat().statusCode(201);
				
		Car rCar = get("/car/3").as(Car.class);
		assertThat("Fiat", equalToIgnoringCase(rCar.getMake()));
		
		List<Car> rCars = Arrays.asList(get("/car/").as(Car[].class));
		assertEquals(3, rCars.size());
	}
	
	@Test
	public void sellCar() {
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car).
	    when().	     
	    post("/car/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person).
	    when().	     
	    post("/person/").then().assertThat().statusCode(201);
		
		Car rCar = get("/car/1").as(Car.class);
		Person rPerson = get("/person/1").as(Person.class);
		
		Purchase purchase = new Purchase(rPerson, rCar);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(purchase).
	    when().	     
	    post("/car/sell").then().assertThat().statusCode(201);
		
		Car rSoldCar = get("/car/1").as(Car.class);
		
		assertEquals(rPerson.getId(), rSoldCar.getOwner().getId());
	}
	
	@Test
	public void getAllCarsByOwner() {
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person).
	    when().	     
	    post("/person/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car2).
	    when().	     
	    post("/car/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car3).
	    when().	     
	    post("/car/").then().assertThat().statusCode(201);
		
		Car rCar = get("/car/1").as(Car.class);
		Car rCar2 = get("/car/2").as(Car.class);
		Person rPerson = get("/person/1").as(Person.class);
		Purchase purchase = new Purchase(rPerson, rCar);
		Purchase purchase2 = new Purchase(rPerson, rCar2);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(purchase).
	    when().	     
	    post("/car/sell").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(purchase2).
	    when().	     
	    post("/car/sell").then().assertThat().statusCode(201);
		
		List<Car> rCars = Arrays.asList(get("/car/owner/1").as(Car[].class));
		assertEquals(2, rCars.size());
	}
	
	@Test
	public void deleteAllCars() {
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car).
	    when().	     
	    post("/car/").then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(car2).
	    when().	     
	    post("/car/").then().assertThat().statusCode(201);
		
		delete("/car/").then().assertThat().statusCode(200);
		List<Car> rCars = Arrays.asList(get("/car/").as(Car[].class));
		assertEquals(0, rCars.size());
	}

	@After
	public void cleanUp() {
		delete("/car/drop").then().assertThat().statusCode(200);
		delete("/person/drop").then().assertThat().statusCode(200);
	}
}
