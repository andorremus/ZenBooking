import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CountryTest {
	private City country;

	@Before
	public void setUp() throws Exception 
	{
		country = new City();
	}

	@After
	public void tearDown() throws Exception 
	{
		country = null;
	}

	@Test
	public void testGetName() 
	{
		assertNull(country.getName());
	}

	@Test
	public void testSetName() {
		country.setName("Croatia");
		assertEquals(country.getName(),"Croatia");
	}
}
