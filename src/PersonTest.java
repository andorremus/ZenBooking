import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PersonTest {
	private Person person;

	@Before
	public void setUp() throws Exception 
	{
		person = new Person();
	}

	@After
	public void tearDown() throws Exception 
	{
		person = null;
	}

	@Test
	public void testGetName() 
	{
		assertNull(person.getName());
	}

	@Test
	public void testSetName()
	{
		person.setName("John");
		assertEquals("John",person.getName());
	}

	@Test
	public void testIsAdult() 
	{
		assertFalse(person.isAdult());		
	}

	@Test
	public void testSetAdult() 
	{
		person.setAdult(true);
		assertTrue(person.isAdult());
	}
	
	@Test
	public void testConstructor() 
	{
		Person newPerson = new Person();		
		assertSame(person, newPerson);
	}
	
	@Test
	public void testConstructor2() 
	{
		Person newPerson = new Person("Jack",true);		
		person = new Person("Jack",true);
		assertSame(person, newPerson);
	}

}
