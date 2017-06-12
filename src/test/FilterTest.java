package test;


import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import domain.Filter;
import domain.Route;
import utilities.DataFile;

public class FilterTest {

	Filter filter;
	@Before
	public void setUp() throws Exception {
		
		filter = new Filter();
		
		DataFile.setFileName(DataFile.FILE_TESTS_CASES);
	}

	@Test
	public void testFilterBy() {
		
		String[] expectation = {"CDC" , "CEBC"};

		Route[] routes = filter.filterBy(1);
		
		int i = 0;
		for(Route r:routes){

			assertEquals(expectation[i++],r.getKey());
		}
		
		assertNull(filter.filterBy(0));
	}
}
