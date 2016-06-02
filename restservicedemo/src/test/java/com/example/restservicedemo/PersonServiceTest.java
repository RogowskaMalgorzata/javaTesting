package com.example.restservicedemo;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.restservicedemo.domain.Person;
import com.jayway.restassured.RestAssured;

public class PersonServiceTest {
	
	private static IDatabaseConnection connection ;
	private static IDatabaseTester databaseTester;
	
	private static final String PERSON_FIRST_NAME = "Jasiu";
	static Person person, person2, person3;
	
	@BeforeClass
    public static void setUp()  throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api";   
		
		Connection jdbcConnection;
		jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
		connection = new DatabaseConnection(jdbcConnection);
		
		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(
				new FileInputStream(new File("src/test/resources/fullData.xml")));
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
		
		person = new Person(PERSON_FIRST_NAME, 1976);
		person2 = new Person("Jan", 1976);
		person3 = new Person(PERSON_FIRST_NAME, 1980);
    }

	@Test
	public void addPerson() throws Exception{	
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person).
	    when().	     
	    post("/person/").then().assertThat().statusCode(201);
				
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("PERSON");
		ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"P_ID"});
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resources/personData.xml"));
		ITable expectedTable = expectedDataSet.getTable("PERSON");
		
		Assertion.assertEquals(expectedTable, filteredTable);
		
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
				
		List<Person> rpersons = Arrays.asList(get("/person/").as(Person[].class));
		assertEquals(3, rpersons.size());	
	}
	
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
		
		List<Person> persons = Arrays.asList(get("/person/").as(Person[].class));
		assertEquals(0, persons.size());
	}
	
	@After
	public void cleanUp() {
		delete("/person/drop").then().assertThat().statusCode(200);
	}
}
