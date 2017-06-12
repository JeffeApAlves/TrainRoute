package test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import domain.CalculateDistance;
import domain.Route;
import utilities.DataFile;

public class CalculateDistanceTest {
	
	CalculateDistance cd;

	@Before
	public void setUp() throws Exception {
		
		cd =new CalculateDistance();
		
		DataFile.setFileName(DataFile.FILE_TESTS_CASES);
	}

	@Test
	public void testCalculateAll() {
		
		double[] expectation = {9,5,13,22,0};
		
		Route[] routes = cd.calculateAll();
		
		int i=0;
		
		for(Route r:routes){
			
			assertEquals(expectation[i++],r.getDistance(),0.001);
		}
	}

	@Test
	public void testCalculate() {
		
		double[] expectation = {9,5,13,22,0};
		
		String[] ids = {"A-B-C "," A-D" , "A-D-C" , "A-E-B-C-D" , "A-E-D"};
		
		Route[] routes = CalculateDistance.calculate(ids);
		
		int i=0;
		
		for(Route r:routes){
			
			assertEquals(expectation[i++],r.getDistance(),0.001);
		}
	}
}
