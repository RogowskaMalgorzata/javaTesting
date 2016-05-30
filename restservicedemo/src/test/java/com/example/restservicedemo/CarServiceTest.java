//package com.example.restservicedemo;
//
//import static com.jayway.restassured.RestAssured.delete;
//import static com.jayway.restassured.RestAssured.get;
//import static com.jayway.restassured.RestAssured.given;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalToIgnoringCase;
//
//import javax.ws.rs.core.MediaType;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.example.restservicedemo.domain.Car;
//import com.example.restservicedemo.domain.Person;
//import com.jayway.restassured.RestAssured;
//
//public class CarServiceTest {
//	
//	Person person;
//	Car car, car2, car3;
//	
//	@BeforeClass
//	public static void setUp(){
//		RestAssured.baseURI = "http://localhost";
//		RestAssured.port = 8080;
//		RestAssured.basePath = "/restservicedemo/api";
//	}
//	
//	@Before
//	public void setPersonsAndCars() {
//		person = new Person("Jasiu", 1976);
//		car = new Car("Ford", "Fiesta", 2011);
//		car2 = new Car("Ford", "Fiesta", 2011);
//		car3 = new Car("Fiat", "Punto", 2011);
//	}
//	
//	@Test
//	public void addCar(){
//		given().
//	       	contentType("application/json").
//	       	body(car).	     
//		post("/car/").then().assertThat().statusCode(201);
//		
//		Car rCar = get("/car/1").as(Car.class);
//		
//		assertThat("Fiesta", equalToIgnoringCase(rCar.getModel()));
//	}
//	
//	@Test
//	public void getAllCars(){
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(car).
//	    when().	     
//	    post("/car/").then().assertThat().statusCode(201);
//		
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(car2).
//	    when().	     
//	    post("/car/").then().assertThat().statusCode(201);
//		
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(car3).
//	    when().	     
//	    post("/car/").then().assertThat().statusCode(201);
//				
//		Car rCar = get("/car/3").as(Car.class);
//		assertThat("Fiat", equalToIgnoringCase(rCar.getMake()));
//		
//		Car[] rCars =  get("/car/all").as(Car[].class);
//		assertEquals(3, rCars.length);
//	}
//	
//	@Test
//	public void sellCar() {
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(car).
//	    when().	     
//	    post("/car/").then().assertThat().statusCode(201);
//		
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(person).
//	    when().	     
//	    post("/person/").then().assertThat().statusCode(201);
//		
//		Car rCar = get("/car/1").as(Car.class);
//		Person rPerson = get("/person/1").as(Person.class);
//		
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(rCar).
//	    given().
//	    	contentType(MediaType.APPLICATION_JSON).
//	    	body(rPerson).
//	    when().	     
//	    post("/car/sell").then().assertThat().statusCode(201);
//		
//		Car rSoldCar = get("/car/1").as(Car.class);
//		
//		assertEquals(rPerson.getId(), rSoldCar.getOwner().getId());
//	}
//	
//	@Test
//	public void getAllCarsByOwner() {
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(car2).
//	    when().	     
//	    post("/car/").then().assertThat().statusCode(201);
//		
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(car3).
//	    when().	     
//	    post("/car/").then().assertThat().statusCode(201);
//		
//		Car rCar = get("/car/1").as(Car.class);
//		Car rCar2 = get("/car/2").as(Car.class);
//		Person rPerson = get("/person/1").as(Person.class);
//		
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(rCar).
//	    given().
//	    	contentType(MediaType.APPLICATION_JSON).
//	    	body(rPerson).
//	    when().	     
//	    post("/car/sell").then().assertThat().statusCode(201);
//		
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(rCar2).
//	    given().
//	    	contentType(MediaType.APPLICATION_JSON).
//	    	body(rPerson).
//	    when().	     
//	    post("/car/sell").then().assertThat().statusCode(201);
//		
//		Car[] rCars = get("/car/owner/1").as(Car[].class);
//		assertEquals(2, rCars.length);
//	}
//	
//	@Test
//	public void deleteAllCars() {
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(car).
//	    when().	     
//	    post("/car/").then().assertThat().statusCode(201);
//		
//		given().
//	       contentType(MediaType.APPLICATION_JSON).
//	       body(car2).
//	    when().	     
//	    post("/car/").then().assertThat().statusCode(201);
//		
//		delete("/car/").then().assertThat().statusCode(200);
//		Car[] rCars =  get("/car/all").as(Car[].class);
//		assertEquals(0, rCars.length);
//	}
//
//	@After
//	public void cleanUp() {
//		get("/car/drop").then().assertThat().statusCode(200);
//		get("/person/drop").then().assertThat().statusCode(200);
//	}
//}
