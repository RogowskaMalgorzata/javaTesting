package com.example.springhibernatedemo.service;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.example.springhibernatedemo.domain.Candy;
import com.example.springhibernatedemo.domain.Person;
import com.example.springhibernatedemo.service.SellingManager;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
public class SellingManagerDBUnitTest {

	@Autowired
	SellingManager sellingManager;
	
	@Test
	@DatabaseSetup("/fullData.xml")
	@ExpectedDatabase(value = "/addPersonData.xml", 
	assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void addPersonCheck() {
        Person p = new Person();
        p.setpName("Kaziu");
        p.setPesel("12129022987");
        
        sellingManager.addPerson(p);  
	}
	
	@Test
	@DatabaseSetup("/fullData.xml")
	public void getPersonsCheck() {
		assertEquals(2, sellingManager.getAllPersons().size());
	}
	
	@Test
	@DatabaseSetup("/fullData.xml")
	@ExpectedDatabase(value = "/deletePersonData.xml", 
	assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void deletePersonCheck() {
		Person rPerson = sellingManager.getAllPersons().get(0);
		Person rPerson2 = sellingManager.getAllPersons().get(1);
		
		sellingManager.deletePerson(rPerson);
		sellingManager.deletePerson(rPerson2);
	}
	
	@Test
	@DatabaseSetup("/fullData.xml")
	public void findPersonByPeselCheck() {
		assertEquals("Jan", sellingManager.findPersonByPesel("01028900561").getpName());
	}
	
	@Test
	@DatabaseSetup("/fullData.xml")
	@ExpectedDatabase(value = "/addCandyData.xml", 
	assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void addCandyCheck() {
        Candy c = new Candy();
        c.setcName("cukierki");
        c.setPrice(1);
        c.setSold(false);
        
        Long newId = sellingManager.addCandy(c); 
        assertTrue(newId > 0);
	}
	
	@Test
	@DatabaseSetup("/fullData.xml")
	public void getAvailableCandiesCheck() {
		assertEquals(1, sellingManager.getAvailableCandies().size());
	}
	
	@Test
	@DatabaseSetup("/fullData.xml")
	public void buyCandy() {
		Candy c = sellingManager.getAvailableCandies().get(0);
		Person p = sellingManager.getAllPersons().get(0);
		
		assertEquals(1, sellingManager.getAvailableCandies().size());
		
		sellingManager.buyCandy(p.getpId(), c.getcId());
		assertEquals(0, sellingManager.getAvailableCandies().size());
	}
	
	@Test
	@DatabaseSetup("/fullData.xml")
	public void findCandyByIdCheck() {
		assertEquals("ciastka", sellingManager.findCandyById(2L).getcName());
	}
	
	@Test
	@DatabaseSetup("/fullData.xml")
	public void getCandiesByPersonCheck() {
		Person p = sellingManager.findPersonByPesel("01028900561");
		assertEquals(1, sellingManager.getCandiesByPerson(p).size());
	}
	
	@Test
	@DatabaseSetup("/fullData.xml")
//	@ExpectedDatabase(value = "/eatCandyData.xml", 
//	assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void eatCandy() {
		Person p = sellingManager.findPersonByPesel("01028900561");
		Candy c = p.getCandies().get(0);
		
		sellingManager.eatCandy(p, c);
		assertEquals(0,p.getCandies().size());
	}
}
