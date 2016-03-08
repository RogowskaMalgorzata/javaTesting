package zad1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class CalculatorTest {
	
	// System Under Test
	Calculator calc = new Calculator();
	
	@Test
	public void checkAdd(){
		 assertEquals(10, calc.add(5, 5));
	}
	
	@Test
	public void checkSub(){
		 assertEquals(5, calc.sub(7, 2));
		 assertEquals(-5, calc.sub(2, 7));
	}
	
	@Test
	public void checkMulti(){
		 assertEquals(25, calc.multi(5, 5));
	}
	
	@Test
	public void checkDiv(){
		assertEquals(5, calc.div(10, 2));
	}
	
	@Test
	public void checkGreater(){
		assertFalse(calc.greater(8, 10));
	}
	
	//zadanie 1b
	@Test(expected = ArithmeticException.class) 
	public void checkDivZero() { 
	     calc.div(10, 0);
	}
		
}
