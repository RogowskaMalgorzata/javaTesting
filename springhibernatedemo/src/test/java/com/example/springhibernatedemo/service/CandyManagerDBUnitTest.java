package com.example.springhibernatedemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
@DatabaseSetup("/fullData.xml")
public class CandyManagerDBUnitTest {

	@Autowired
	CandyManager candyManager;
	
	@Test
	@ExpectedDatabase(value = "/addPersonData.xml", table = "PERSON")
	public void addPersonCheck() {
        Person p = new Person();
        p.setpName("Kaziu");
        p.setPesel("12129022987");
        
        candyManager.addPerson(p);  
	}
	
	@Test
	public void getPersonsCheck() {
		assertEquals(2, candyManager.getAllPersons().size());
	}
	
	@Test
	@ExpectedDatabase(value = "/deletePersonData.xml", table = "PERSON")
	public void deletePersonCheck() {
		Person rPerson = candyManager.findPersonByPesel("10048580561");
		
		candyManager.deletePerson(rPerson);
	}
	
	@Test
	public void findPersonByPeselCheck() {
		assertEquals("Jan", candyManager.findPersonByPesel("01028900561").getpName());
	}
	
	@Test
	@ExpectedDatabase(value = "/addCandyData.xml", table = "CANDY",
	assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void addCandyCheck() {
        Candy c = new Candy();
        c.setcName("cukierki");
        c.setPrice(1);
        c.setSold(false);
        
        Long newId = candyManager.addCandy(c); 
        assertTrue(newId > 0);
	}
	
	@Test
	public void getAvailableCandiesCheck() {
		assertEquals(1, candyManager.getAvailableCandies().size());
	}
	
	@Test
	public void buyCandy() {
		Candy c = candyManager.getAvailableCandies().get(0);
		Person p = candyManager.getAllPersons().get(0);
		
		assertEquals(1, candyManager.getAvailableCandies().size());
		
		candyManager.buyCandy(p.getpId(), c.getcId());
		assertEquals(0, candyManager.getAvailableCandies().size());
	}
	
	@Test
	public void findCandyByIdCheck() {
		assertEquals("ciastka", candyManager.findCandyById(2L).getcName());
	}
	
	@Test
	public void getCandiesByPersonCheck() {
		Person p = candyManager.findPersonByPesel("01028900561");
		assertEquals(1, candyManager.getCandiesByPerson(p).size());
	}
	
	@Test
	@ExpectedDatabase(value = "/eatCandyData.xml", table="CANDY",
	assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void eatCandy() {
		Person p = candyManager.findPersonByPesel("01028900561");
		Candy c = candyManager.findCandyById(2L);
		
		candyManager.eatCandy(p, c);
		assertEquals(0,p.getCandies().size());
	}
}
