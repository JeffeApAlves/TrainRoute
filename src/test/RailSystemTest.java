package test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import controller.RailSystem;
import domain.Route;
import utilities.DataFile;

public class RailSystemTest {

	
	@Before
	public void setUp() throws Exception {
		
		DataFile.setFileName(DataFile.FILE_TESTS_CASES);
	}

	@Test
	public void testFilterByStops1() {
		
		Route[] routes = RailSystem.filterByStops1();
		
		assertEquals("CDC",routes[0].getKey());
		assertEquals("CEBC",routes[1].getKey());		
		
		assertEquals(2,routes.length);
	}
	
	@Test
	public void testFilterByStops2() {
		
		Route[] routes = RailSystem.filterByStops2();
		
		assertEquals("ABCDC",routes[0].getKey());
		assertEquals("ADCDC",routes[1].getKey());
		assertEquals("ADEBC",routes[2].getKey());		
		
		assertEquals(3,routes.length);
	}


	@Test
	public void testCalculateDistance() {
		
		
		Route[] routes = RailSystem.calculateDistance();
		
		assertEquals(9,routes[0].getDistance(),0.001);
		assertEquals(5,routes[1].getDistance(),0.001);
		assertEquals(13,routes[2].getDistance(),0.001);
		assertEquals(22,routes[3].getDistance(),0.001);
	
		assertEquals(5,routes.length);

	}

	@Test
	public void testShortestRoute() {
		
		Map<String, Route[]> trips = RailSystem.shortestRoute();
				
		Route[] routes = trips.get("AC");
		
		assertEquals(1,routes.length);
		assertEquals(9,routes[0].getDistance(),0.001);		
		

		routes = trips.get("BB");
		
		assertEquals(1,routes.length);
		assertEquals(9,routes[0].getDistance(),0.001);		
		assertEquals(2,trips.size());		
	}

}
