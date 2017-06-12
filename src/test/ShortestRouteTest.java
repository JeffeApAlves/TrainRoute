package test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import domain.Route;
import domain.ShortestRoute;
import utilities.DataFile;

public class ShortestRouteTest {
	
	ShortestRoute sr; 

	@Before
	public void setUp() throws Exception {
		
		sr = new ShortestRoute();
		
		DataFile.setFileName(DataFile.FILE_TESTS_CASES);
	}

	@Test
	public void testGetShortestRoute() {
		
		Route[] trip =  sr.getShortestRoute(new Route("DA"));
		assertNull(trip);
		
		trip = sr.getShortestRoute(new Route("AC"));
		assertEquals(1,trip.length);
		assertEquals(9,trip[0].getDistance(),0.001);
		
		trip = sr.getShortestRoute(new Route("BB"));
		assertEquals(1,trip.length);
		assertEquals(9,trip[0].getDistance(),0.001);
	}

	@Test
	public void testGetShortestRouteRoute() {
		
		Map<String, Route[]> trips = sr.getShortestRoute();
		
		Route[] routes = trips.get("AC");
		
		assertEquals(1,routes.length);
		assertEquals(9,routes[0].getDistance(),0.001);		
		

		routes = trips.get("BB");
		
		assertEquals(1,routes.length);
		assertEquals(9,routes[0].getDistance(),0.001);
		
		assertEquals(2,trips.size());		
	}

}
