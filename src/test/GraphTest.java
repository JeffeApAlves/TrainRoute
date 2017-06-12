package test;

import static org.junit.Assert.*;
import java.util.Arrays;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.hasItems;
import org.junit.Before;
import org.junit.Test;

import domain.Graph;
import domain.Route;
import utilities.DataFile;

public class GraphTest {

	Graph graph;
	
	@Before
	public void setUp() throws Exception {
		
		graph = Graph.create();
		
		graph.clear();
	}

	@Test
	public void testGraph() {
		
		assertNotNull(Graph.create());		
	}

	@Test
	public void testGetAllTowns() {
		
		String[] routes		= {"AB5","FG3","QW2","AC1","MN90"};
		String[] expectation	= {"A", "B", "F", "G", "Q", "W", "C" , "M", "N"};
		
		graph.addRoute(routes);
		
		assertThat(Arrays.asList(graph.getAllTowns()),hasItems(expectation));
	}

	@Test
	public void testGetAllRoutes() {
		
		String[] routes		= {"AB5","FG3","QW2","AC1","MN90"};
		String[] expection	= {"AB", "FG", "QW", "AC", "MN"};		
		
		graph.addRoute(routes);		
		
		assertThat(Arrays.asList(graph.getAllRoutes()),hasItems(expection));
	}

	@Test
	public void testGetAllRoutePossible() {
		
		String[] routes	= {"A-B5"," BC4"," CD8"," DC8"," DE6"," AD5"," CE2"," EB3","AE7","BA1"};
		graph.addRoute(routes);
		
		String[] expection = {"ABC"};		
		assertThat(Arrays.asList(graph.getAllRoutePossible(new Route("AC"))),hasItems(expection));
		
		String[] expection2	= {"AB","ADCEB"};
		assertThat(Arrays.asList(graph.getAllRoutePossible(new Route("AB"))),hasItems(expection2));
		
		String[] expection3	= {"ABA"};
		assertThat(Arrays.asList(graph.getAllRoutePossible(new Route("AA"))),hasItems(expection3));
	}

	@Test
	public void testGetConnections() {
		
		String[] routes		= {"AB5","BW3","BA2","AC1","WM90","AM"};
		String[] expection	= {"AB", "AC","AM"};
		
		graph.addRoute(routes);
		
		assertThat(Arrays.asList(graph.getConnections("AM")),hasItems(expection));
	}

	@Test
	public void testAddRoute() {
		
		String[] expection	= {"AB", "CF", "DF"};
		
		graph.addRoute("A-B5");
		graph.addRoute("C F10");
		graph.addRoute("DF1");
		
		assertThat(Arrays.asList(graph.getAllRoutes()),hasItems(expection));
	}

	@Test
	public void testAddTown() {
		
		graph.addTown("A");
		graph.addTown("F");		
		
		assertTrue(graph.containsTown("A"));
		assertTrue(graph.containsTown("F"));		
		assertFalse(graph.containsTown("D"));		
		assertFalse(graph.containsTown("B"));		
	}

	@Test
	public void testGetRoute() {
		
		String[] routes = {"AB5","FG3","QW2","AC1","MN90"};
		
		graph.addRoute(routes);
		
		assertNull(graph.getRoute("ACD"));
		assertNotNull(graph.getRoute("F G"));		
	}

	@Test
	public void testLoadGraphRoutesFromFile() {
		
		String[] expection	= {"AB", "BC", "CD", "DC", "DE", "AD", "CE" , "EB"};
		
		/*aponta para o arquivo usado somente para tests case*/
		DataFile.setFileName(DataFile.FILE_TESTS_CASES);
		
		graph.loadGraphRoutesFromFile();
		
		assertThat(Arrays.asList(graph.getAllRoutes()),hasItems(expection));
	}

	@Test
	public void testContainsTown() {
		
		String[] routes = {"AB5","FG3","QW2","AC1","MN90"};
		
		graph.addRoute(routes);
		
		assertTrue(graph.containsTown("A"));
		assertTrue(graph.containsTown("B"));
		assertTrue(graph.containsTown("F"));
		assertTrue(graph.containsTown("G"));
		assertTrue(graph.containsTown("Q"));
		assertTrue(graph.containsTown("W"));
		assertTrue(graph.containsTown("C"));
		assertTrue(graph.containsTown("M"));
		assertTrue(graph.containsTown("N"));		
		assertFalse(graph.containsTown("J"));		
	}

	@Test
	public void testIsRouteAvailable() {
		
		String[] routes = {"AB5","BW3","BA2","AC1","WM90","AM"};
		
		graph.addRoute(routes);
		
		assertTrue(graph.isRouteAvailable("AB"));		
		assertTrue(graph.isRouteAvailable("WM"));		
		assertTrue(graph.isRouteAvailable("ABWM"));
		assertTrue(graph.isRouteAvailable("BWM"));
		assertFalse(graph.isRouteAvailable("ABWMC"));
	}
	
	@Test
	public void testcalculateDistance() {

		String[] routes = {"AB5","BW3","BA2","AC1","WM90","AM"};
		
		graph.addRoute(routes);

		assertEquals(5.0,graph.calculateDistance("AB"),0.01);
		assertEquals(8.0,graph.calculateDistance("ABW"),0.01);
		assertEquals(7.0,graph.calculateDistance("ABA"),0.01);		
		assertEquals(93.0,graph.calculateDistance("BWM"),0.01);		
	}
	
	@Test
	public void testclear() {

		graph.addTown("A");
		graph.addTown("B");		

		graph.clear();
		
		assertFalse(graph.containsTown("A"));
		assertFalse(graph.containsTown("B"));		
	}	
}
