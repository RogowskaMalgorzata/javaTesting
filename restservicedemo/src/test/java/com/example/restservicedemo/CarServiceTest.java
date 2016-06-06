package com.example.restservicedemo;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;
import com.example.restservicedemo.domain.Purchase;
import com.example.restservicedemo.service.CarManager;
import com.example.restservicedemo.service.PersonManager;
import com.jayway.restassured.RestAssured;

public class CarServiceTest {
	
	private static IDatabaseConnection connection ;
	private static IDatabaseTester databaseTester;
	
	static PersonManager pm = new PersonManager();
	static CarManager cm = new CarManager(); 
	
	public void cleanAndInsert(String source) throws Exception {
		cm.deleteCarTable();
		pm.deletePersonTable();
		pm.createTable();
		cm.createTable();
		
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(
				new FileInputStream(new File(source)));
		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
		databaseTester.setDataSet(dataSet);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.onSetup();
	}
	
	@BeforeClass
	public static void setUp() throws Exception{
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api";
		
		Connection jdbcConnection;
		jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
		connection = new DatabaseConnection(jdbcConnection);
	}
	
	@Before
	public void insertSampleData() throws Exception {
		cleanAndInsert("src/test/resources/fullDataWithCar.xml");
	}
	
	@Test
	public void addCar() throws Exception{
		Car car = new Car("Ford", "Fiesta", 2011);
		given().
	       	contentType("application/json").
	       	body(car).	     
		post("/car/").then().assertThat().statusCode(201);
		
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("CAR");
		ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"C_ID", "OWNER_ID"});
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resources/carData.xml"));
		ITable expectedTable = expectedDataSet.getTable("CAR");
		
		Assertion.assertEquals(expectedTable, filteredTable);
	}
	
	@Test
	public void getAllCars(){		
		List<Car> rCars = Arrays.asList(get("/car/").as(Car[].class));
		assertEquals(3, rCars.size());
	}
	
	@Test
	public void sellCar() {
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
	public void getAllCarsByOwner() throws Exception {
		cleanAndInsert("src/test/resources/carDataWithOwner.xml");
		
		List<Car> rCars = Arrays.asList(get("/car/owner/1").as(Car[].class));
		assertEquals(2, rCars.size());
	}
	
	@Test
	public void deleteAllCars() {
		delete("/car/").then().assertThat().statusCode(200);
		List<Car> rCars = Arrays.asList(get("/car/").as(Car[].class));
		assertEquals(0, rCars.size());
	}

	@AfterClass
	public static void tearDown() throws Exception{
		cm.deleteCarTable();
		pm.deletePersonTable();
		databaseTester.onTearDown();
	}
}
