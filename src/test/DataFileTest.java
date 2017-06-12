package test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import java.util.Arrays;


import org.junit.Before;
import org.junit.Test;

import utilities.DataFile;
import utilities.FileProperty;

public class DataFileTest {

	@Before
	public void setUp() throws Exception {
		
		DataFile.setFileName(DataFile.FILE_TESTS_CASES);		
	}

	@Test
	public void testOpen() {
	
		assertEquals(true,DataFile.open());
	}

	@Test
	public void testReadListString() {

		String[]	expectation = {"AB5","BC4","CD8","DC8","DE6","AD5","CE2","EB3","AE7"};

		assertThat(Arrays.asList(DataFile.readList(FileProperty.GRAPH_ROUTES)),hasItems(expectation));
		
		assertNull(DataFile.readList(null));		
	}

	@Test
	public void testReadListStringInt() {
		
		String[]	expectation = {"ABCDC" , "ADCDC", "ADEBC", "AB"};

		assertThat(Arrays.asList(DataFile.readList(FileProperty.FILTER_ROUTES,2)),hasItems(expectation));
		assertNull(DataFile.readList(FileProperty.FILTER_ROUTES,0) );
	}

	@Test
	public void testReadConditionStringInt() {
		
		assertNull(DataFile.readCondition(FileProperty.FILTER_CONDITION,0) );
		assertEquals("<=", DataFile.readCondition(FileProperty.FILTER_CONDITION,1) );
		assertEquals("==", DataFile.readCondition(FileProperty.FILTER_CONDITION,2) );
		assertEquals("<", DataFile.readCondition(FileProperty.FILTER_CONDITION,3) );		
	}

	@Test
	public void testReadDoubleStringInt() {
		
		assertEquals(Double.MAX_VALUE,DataFile.readDouble(FileProperty.FILTER_CONDITION,0),0.01);
		assertEquals(4.0,DataFile.readDouble(FileProperty.FILTER_CONDITION,2),0.01);
	}

	@Test
	public void testReadIntegerStringInt() {
		
		assertEquals(Integer.MAX_VALUE,DataFile.readInteger(FileProperty.FILTER_CONDITION,0));		
		assertEquals(3, DataFile.readInteger(FileProperty.FILTER_CONDITION,1));
	}

	@Test
	public void testReadLiteralOperandStringInt() {
		
		assertNull(DataFile.readLiteralOperand(FileProperty.FILTER_CONDITION,0));		
		assertEquals("STOPS",DataFile.readLiteralOperand(FileProperty.FILTER_CONDITION,1));
		assertEquals("STOPS",DataFile.readLiteralOperand(FileProperty.FILTER_CONDITION,2));		
		assertEquals("DISTANCE",DataFile.readLiteralOperand(FileProperty.FILTER_CONDITION,3));		
	}
}
