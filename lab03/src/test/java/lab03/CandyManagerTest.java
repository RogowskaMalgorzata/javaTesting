package lab03;

import static org.junit.Assert.assertEquals;

import static org.easymock.EasyMock.*;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;


public class CandyManagerTest {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);
	
	@Mock
	private IMyList mock;
	
	@TestSubject
	private CandyManager cm = new CandyManager(mock);
	
	@Test
	public void checkAdd() {
		Candy candy = new Candy("Chocolate", "Milka", 2.5);
		Candy candy2 = new Candy("Cookie", "Oreo", 5);
		Candy candy3 = new Candy("Jelly", "Haribo", 4.5);
		
		mock.add(candy);
		mock.add(candy2);
		mock.add(candy3);
		
		expectLastCall();
		expect(mock.size()).andReturn(3);
		expect(mock.getAll()).andReturn(mock);
		expect(mock.get(1)).andReturn(candy2);
		
		replay(mock);
		
		cm.add(candy);
		cm.add(candy2);
		cm.add(candy3);
		
		assertEquals(3, cm.getAll().size());
		assertEquals(candy2, cm.get(1));
		
		verify(mock);
		
		
	}
	
}
