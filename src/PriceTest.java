

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PriceTest {
	private Price price;

	@Before
	public void setUp() throws Exception 
	{
		price = new Price();	
	}

	@After
	public void tearDown() throws Exception 
	{
		price = null;
		
	}	

	@Test
	public void testGetTotalPrice() 
	{
		price.setBasePrice(400);
		price.setMultiplier(2.0);
		assertEquals(960.0,price.getTotalPrice(),0.1);
	}
	
	@Test
	public void testGetTotalBeforeVAT() 
	{
		price.setBasePrice(400);
		price.setMultiplier(2.0);
		assertEquals(800.0,price.getTotalBeforeVAT(),0.1);
	}

	@Test
	public void testGetBasePrice() 
	{
		assertEquals(price.getBasePrice(),0.0,0.1);
	}

	@Test
	public void testSetBasePrice() 
	{
		price.setBasePrice(300);
		assertEquals(300.0,price.getBasePrice(),0.1);
	}

	@Test
	public void testGetMultiplier() 
	{
		assertEquals(price.getMultiplier(),0.0,0.1);
	}

	@Test
	public void testSetMultiplier() 
	{
		price.setMultiplier(4);
		assertEquals(4,price.getMultiplier(),0.1);
	}

}
