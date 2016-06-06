package com.example.restservicedemo;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
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
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.restservicedemo.domain.Person;
import com.example.restservicedemo.service.CarManager;
import com.example.restservicedemo.service.PersonManager;
import com.jayway.restassured.RestAssured;

public class PersonServiceTest {
	
	private static IDatabaseConnection connection ;
	private static IDatabaseTester databaseTester;
	
	private static final String PERSON_FIRST_NAME = "Mariusz";
	static PersonManager pm = new PersonManager();
	static CarManager cm = new CarManager(); 
	
	@BeforeClass
    public static void setUp()  throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api";
		
		Connection jdbcConnection;
		jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
		connection = new DatabaseConnection(jdbcConnection);
    }
	
	@Before
	public void insertSampleData() throws Exception {
		cm.deleteCarTable();
		pm.deletePersonTable();
		pm.createTable();
		cm.createTable();
		
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(
				new FileInputStream(new File("src/test/resources/fullData.xml")));
		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
		databaseTester.setDataSet(dataSet);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.onSetup();
	}

	@Test
	public void addPerson() throws Exception{
		Person person = new Person(PERSON_FIRST_NAME, 1990);
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
	}
	
	@Test
	public void getAllPersons() {
		List<Person> rPersons = Arrays.asList(get("/person/").as(Person[].class));
		assertEquals(3, rPersons.size());
	}
	
	@Test
	public void deleteAllPersons() {
		delete("/person/").then().assertThat().statusCode(200);
		
		List<Person> persons = Arrays.asList(get("/person/").as(Person[].class));
		assertEquals(0, persons.size());
	}
	
	@AfterClass
	public static void tearDown() throws Exception{
		cm.deleteCarTable();
		pm.deletePersonTable();
				
		databaseTester.onTearDown();
	}
}
