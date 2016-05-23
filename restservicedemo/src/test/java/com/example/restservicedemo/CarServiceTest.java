package com.example.restservicedemo;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import org.junit.BeforeClass;
import org.junit.Test;

import com.example.restservicedemo.domain.Car;
import com.jayway.restassured.RestAssured;

public class CarServiceTest {
	
	@BeforeClass
	public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api";
	}
	
	@Test
	public void getCar(){
		get("/car/0").then().assertThat().body("model", equalTo("Corsa"));
		
		Car aCar = get("/car/0").as(Car.class);
		assertThat(aCar.getMake(), equalToIgnoringCase("Opel"));
	}
	
	@Test
	public void addCar(){
		
		Car aCar = new Car(2, "Ford", "Fiesta", 2011, 1);
		given().
		       contentType("application/json").
		       body(aCar).
		when().	     
		post("/car/").then().assertThat().statusCode(201).body(containsString("Car saved:"));
	}
	

}
