package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.Town;

public class TownTest {	
	
	Town	town;

	@Before
	public void setUp() throws Exception {
		
		town = new Town("A2BC ,C998!£$^&*()");
	}

	@Test
	public void testTown() {
		
		assertEquals("A",town.getKey());
	}

	@Test
	public void testGetId() {
		
		town.setKey("A2BC ,C998!£$^&*()");		

		assertEquals("A",town.getKey());
	}

	@Test
	public void testSetId() {

		town.setKey("A2BC ,C998!£$^&*()");
		
		assertEquals("A",town.getKey());
		
		
		town.setKey("BC50");
		
		assertEquals("B",town.getKey());

		town.setKey(null);
		
		assertNull(town.getKey());
		
		town.setKey("1");
		
		assertNull(town.getKey());
	}

	@Test
	public void testFormatKey() {
		
		assertEquals("A",Town.formatKey("A2BC ,C998!£$^&*()"));
		
		assertNull(Town.formatKey(null));		
	}
}
