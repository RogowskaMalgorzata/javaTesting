package com.example.restservicedemo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;
import com.example.restservicedemo.domain.Purchase;
import com.example.restservicedemo.service.CarManager;
import com.example.restservicedemo.service.PersonManager;

public class DataBaseTest {
	
	//systems under test
	static PersonManager pm = new PersonManager();
	static CarManager cm = new CarManager();
	static Person p1, p2, p3;
	static Car c1, c2, c3;
	
	@BeforeClass
	public static void setUp() {
		cm.deleteCarTable();
		pm.deletePersonTable();
		pm.createTable();
		cm.createTable();
		
		p1 = new Person("Jan", 1980);
		p2 = new Person("Zenek", 1990);
		p3 = new Person("Jan", 1985);
		
		c1 = new Car("Fiat", "Panda", 2000);
		c2 = new Car("Fiat", "Panda", 2001);
		c3 = new Car("Toyota", "Yaris", 2005);
	}
	
	@Test
	public void checkAdding() {
		assertEquals(1, pm.addPerson(p1));
		assertEquals(1, cm.addCar(c1));
	}
	
	@Test
	public void checkDeleting() {
		pm.addPerson(p1);
		pm.addPerson(p2);
		pm.addPerson(p3);
		cm.addCar(c1);
		cm.addCar(c2);
		
		assertFalse(cm.getAllCars().size() == 0);
		cm.clearCars();
		assertTrue(cm.getAllCars().size() == 0);
		
		assertFalse(pm.getAllPersons().size() == 0);
		pm.clearPersons();
		assertTrue(pm.getAllPersons().size() == 0);
		
	}
	
	@Test
	public void checkGettingById() {
		pm.addPerson(p2);
		cm.addCar(c2);
		
		assertEquals("Zenek", pm.getPerson(1L).getFirstName());
		assertEquals(2001, cm.getCar(1L).getYop());
	}
	
	@Test
	public void checkGettingAll() {
		pm.addPerson(p1);
		pm.addPerson(p2);
		pm.addPerson(p3);
		cm.addCar(c1);
		cm.addCar(c2);
		cm.addCar(c3);
		Purchase p = new Purchase(p1, c3);
		cm.sellCar(p);
		
		assertTrue(pm.getAllPersons().size() == 3);
		assertTrue(cm.getAllCars().size() > 2);
		assertNotNull(cm.getAllCarsByOwner(1L));
	}
	
	@Test
	public void checkSellingCar() {
		assertEquals(1, pm.addPerson(p1));
		assertEquals(1, cm.addCar(c1));
		
		List<Car> cars = cm.getAllCars();
		assertTrue(cars.size() > 0);
		
		List<Person> persons = pm.getAllPersons();
		assertTrue(persons.size() > 0);
		
		Purchase purchase = new Purchase(persons.get(1), cars.get(1));

		assertEquals(1, cm.sellCar(purchase));
	}
	
	@AfterClass
	public static void cleanUp() {
		cm.deleteCarTable();
		pm.deletePersonTable();
	}
}
