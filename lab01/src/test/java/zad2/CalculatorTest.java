package zad2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculatorTest {

	// System Under Test
	Calculator calc = new Calculator();
	
	@Test
	public void checkAdd(){
		 assertEquals(2.2, calc.add(1.1, 1.1), 0);
	}
	
	
} 
