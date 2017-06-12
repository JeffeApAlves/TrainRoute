package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.Route;

public class RouteTest {
	
	Route route;

	@Before
	public void setUp() throws Exception {
		
		route = new Route();
	}

	@Test
	public void testRoute() {

		route = new Route();
		
		assertTrue(route.isAvailable());		
	}

	@Test
	public void testRouteStringDouble() {

		route	= new Route("A-B",3.0);
		
		assertEquals(3.0,route.getDistance(),01);
		assertEquals("AB",route.getKey());
		assertTrue(route.isAvailable());		
	}

	@Test
	public void testRouteString() {
		
		Route r1 = new Route("A-B3,5");
		
		assertEquals(3.5,r1.getDistance(),01);
		assertEquals("AB",r1.getKey());		
		assertTrue(r1.isAvailable());		

		Route r2 = new Route("D-C3.5");
		
		assertEquals(3.5,r2.getDistance(),01);
		assertEquals("DC",r2.getKey());
		assertTrue(r2.isAvailable());		
	}

	@Test
	public void testGetStartingTown() {
		
		route.setKey("A2BC ,C998!£$^&*()");
		assertEquals("ABCC".substring(0, 1),route.getStartingTown());		
		
		route.setRoute("ABD3");
		assertEquals("A",route.getStartingTown());
	}

	@Test
	public void testGetDistance() {
		
		route.setTotalDistance("3.55");
		assertEquals(3.55,route.getDistance(),01);
		
		route.setTotalDistance(4.5);
		assertEquals(4.5,route.getDistance(),01);
	}

	@Test
	public void testGetNumberOfNodes() {
		route.setRoute("-A-BC3 ");
		assertEquals(3,route.getNumberOfNodes());
	}

	@Test
	public void testGetNumberOfStops() {
		
		route.setRoute("-A-B3 ");
		
		assertEquals(2,route.getNumberOfNodes());
	}

	@Test
	public void testGetIdOfStop() {
		
		route.setRoute("AXTF");
		
		assertEquals("TF",route.getKeyOfStop(2));
		
		assertEquals("",route.getKeyOfStop(3));		
	}

	@Test
	public void testSetRoute() {
		
		route.setRoute("A-F350");
		assertEquals("AF",route.getKey());
		assertEquals(350.0,route.getDistance(),01);
	}

	@Test
	public void testSetId() {
		
		route.setKey("A2BC ,C998!£$^&*()");
		assertEquals("ABC",route.getKey());
	}

	@Test
	public void testSetTotalDistanceString() {
		
		route.setTotalDistance("3.55");
		assertEquals(3.55,route.getDistance(),01);
	}

	@Test
	public void testSetTotalDistanceDouble() {
		
		route.setTotalDistance(4.5);
		assertEquals(4.5,route.getDistance(),01);

	}

	@Test
	public void testGetId() {
		
		route.setKey("A2BC ,C998!£$^&*()");
		assertEquals("ABC",route.getKey());

		route.setRoute("AB 3");
		assertEquals("AB",route.getKey());
	}

	@Test
	public void testGetEndingTown() {
		
		route.setKey("A-B-C-D-");
		assertEquals("D",route.getEndingTown());		
		
		route.setRoute("ABFH3444");
		assertEquals("H",route.getEndingTown());
	}

	@Test
	public void testIsAvailable() {
		route.setAvailable(true);
		assertTrue(route.isAvailable());		
		
		route.setAvailable(false);
		assertEquals(false,route.isAvailable());		
	}

	@Test
	public void testSetAvailable() {
		route.setAvailable(true);
		assertTrue(route.isAvailable());		
		
		route.setAvailable(false);
		assertFalse(route.isAvailable());		
	}

	@Test
	public void testFormatKey() {
		
		assertEquals("HJSGHDJ",Route.formatKey("HJ ?SGHDJ350"));		
		assertNull(Route.formatKey(null));
		assertNull(Route.formatKey("A"));		
		assertEquals("HH",Route.formatKey("HH"));
		assertEquals("HJSGHDJ",Route.formatKey("HJ ?SGHDJJJ350"));
		assertEquals("HH",Route.formatKey("HHH"));		
	}

	@Test
	public void testTestCondition() {
		
		route.setRoute("AB20");
		
		assertFalse(route.testCondition(">=","STOPS",5));		
		assertTrue(route.testCondition(">=","STOPS",1));
		
		assertFalse(route.testCondition(">","STOPS",5));
		assertTrue(route.testCondition("<","STOPS",5));
		assertTrue(route.testCondition("<=","STOPS",5));		
		assertFalse(route.testCondition("==","STOPS",5));
		
		assertFalse(route.testCondition(">=","DISTANCE",55));		
		assertFalse(route.testCondition(">","DISTANCE",55));
		assertTrue(route.testCondition("<","DISTANCE",55));
		assertTrue(route.testCondition("<=","DISTANCE",55));		
		assertFalse(route.testCondition("==","DISTANCE",55));
	}
}
