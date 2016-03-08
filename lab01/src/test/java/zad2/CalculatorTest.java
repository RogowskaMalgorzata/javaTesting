package zad2;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculatorTest {

	// System Under Test
	Calculator calc = new Calculator();
	
	@Test
	public void checkSub(){
		 assertEquals(2.2, calc.sub(3.3, 1.1), 0.05);
	}
	
	@Test
	public void checkAdd() {
		assertEquals(2.2, calc.add(1.1256, 1.1111), 0.05);
	}
	
	@Test
	public void checkDiv() {
		assertEquals(2, calc.div(4.4, 2), 0.5);
	}
	
	@Test
	public void checkMulti() {
		assertEquals(6.6, calc.multi(2.2, 3), 0.05);
	}
	
	@Test
	public void checkGreater() {
		assertFalse(calc.greater(3.4, 5));
	}
 } 
