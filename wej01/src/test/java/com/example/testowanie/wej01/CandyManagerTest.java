package com.example.testowanie.wej01;

import static org.junit.Assert.*;


import org.junit.Test;


public class CandyManagerTest {
	CandyManager cm = new CandyManager();
	
	// System Under Test
	Candy candy = new Candy("Chocolate", "Milka", 2.5);
	Candy candy2 = new Candy("Cookie", "Oreo", 2.5);
	Candy candy3 = new Candy("Cake", "Milka", 2.5);
	
	@Test
	public void checkAdd() {
		cm.add(candy);
		cm.add(candy2);
		assertEquals(cm.getAll().get(0).getName(), "Chocolate");
		assertEquals(cm.getAll().get(1).getBrand(), "Oreo");
	}
	
	@Test
	public void checkGetAll() {
		cm.add(candy);
		cm.add(candy2);
		cm.add(candy3);
		assertEquals(cm.getAll().get(0).getName(), "Chocolate");
		assertEquals(cm.getAll().size(), 3);
		
	}

}
