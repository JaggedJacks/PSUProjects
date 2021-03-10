package edu.psgv.sweng861;
/*
 * Author: Joshua Daniels
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class UnitsConvertorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	/******Group of tests for the metric system function toMil()******/
	//Test fail case for metric system
	@Test
	public void testInvalidMetricSystemInput() {//expect the code to throw an exception when the wrong units are entered
		long value = 2;
		String unit = "lb"; //Pounds are not metric units
	    Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> UnitsConvertor.toMil(value, unit)); //the wrong units were entered. Pass if exception is thrown
	    assertTrue(exception.getMessage().contains("Bad unit value"));
	}
	//Test pass cases for metric system, using a value, a unit and a formula to verify that the values are being calculated correctly
	@Test
	public void testMm() {
		long value = 3;
		double newValue;
		String unit = "mm";
		newValue = UnitsConvertor.toMil(value, unit);	
		assertEquals(newValue,(double)value*39.3701);
	}
	@Test
	public void testMillimeter() {
		long value = 3;
		double newValue;
		String unit = "millimeter";
		newValue = UnitsConvertor.toMil(value, unit);	
		assertEquals(newValue, (double)value*39.3701);
	}
	@Test
	public void testCm() {
		long value = 4;
		String unit = "cm"; 
		assertEquals(UnitsConvertor.toMil(value, unit),(double)value*393.701);	
	}
	@Test
	public void testCentimeter() {
		long value = 4;
		String unit = "centimeter"; 
		assertEquals(UnitsConvertor.toMil(value, unit) , (double)value*393.701);	
	}
	@Test
	public void testm() {
		long value = 5;
		String unit = "m";
		assertEquals(UnitsConvertor.toMil(value, unit),(double)value*39370.1);		
	}
	@Test
	public void testmeter() {
		long value = 5;
		String unit = "meter";
		assertEquals(UnitsConvertor.toMil(value, unit), (double)value*39370.1);		
	}
	@Test
	public void testKm() {
		long value = 6;
		String unit = "km";
		assertEquals(UnitsConvertor.toMil(value, unit),(double)value*39370100);		
	}
	@Test
	public void testKilometer() {
		long value = 6;
		String unit = "kilometer";
		assertEquals(UnitsConvertor.toMil(value, unit), (double)value*39370100);		
	}
	/******End group of tests for the metric system function toMil()******/
	
	/******Begin group of tests for the imperial system function toMm()******/
	//Test fail case for imperial system
	@Test
	public void testInvalidImperialSystemInput() {//expect the code to throw an exception when the wrong units are entered
		long value = 2;
		String unit = "N"; //Newtons are not imperial units
		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> UnitsConvertor.toMm(value, unit)); //the wrong units were entered. Pass if exception is thrown
		assertTrue(exception.getMessage().contains("Illegal unit value"));
	}
	//Test pass cases for imperial system, using a value, a unit and a formula to verify that the values are being calculated correctly
	@Test
	public void testIn() {
		long value = 1;
		String unit = "in";
		assertEquals(UnitsConvertor.toMm(value, unit), (double)value*25.4);		
	}	
	@Test
	public void testInch() {
		long value = 1;
		String unit = "inch";
		assertEquals(UnitsConvertor.toMm(value, unit), (double)value*25.4);		
	}
	@Test
	public void testFt() {
		long value = 10;
		String unit = "ft";
		assertEquals(UnitsConvertor.toMm(value, unit), (double)value*304.8);		
	}
	@Test
	public void testFoot() {
		long value = 10;
		String unit = "foot";
		assertEquals(UnitsConvertor.toMm(value, unit), (double)value*304.8);		
	}
	@Test
	public void testYd() {
		long value = 20;
		String unit = "yd";
		assertEquals(UnitsConvertor.toMm(value, unit), (double)value*914.4);	
	}
	@Test
	public void testYard() {
		long value = 20;
		String unit = "yard";
		assertEquals(UnitsConvertor.toMm(value, unit), (double)value*914.4);	
	}
	@Test
	public void testMi() {
		long value = 30;
		String unit = "mi";
		assertEquals(UnitsConvertor.toMm(value, unit), (double)value*1609344);
	}
	@Test
	public void testMile() {
		long value = 30;
		String unit = "mile";
		assertEquals(UnitsConvertor.toMm(value, unit), (double)value*1609344);
	}
	@Test
	public void testMil() {
		long value = 40;
		String unit = "mil";
		assertEquals(UnitsConvertor.toMm(value, unit), (double)value*0.0254);		
	}
	/******End group of tests for the imperial system function toMm()******/
	
	/******* Group of tests for the main method **********/
	@Test
	public void mainMetricTest() {
		// simulated input
		String input = "2 mm";
		// save current System.in and System.out
		 InputStream sysIn = System.in;
		 PrintStream sysOut = System.out;
		 InputStream myIn = new ByteArrayInputStream(input.getBytes());
		 System.setIn(myIn);		 
		 final String unNormalizedExpectedOutput =
					"Please Enter the input value followed by the unit:\n" +
					"2 mm is:\n" +
					"79 mil\n" +
					"0.079 inch\n" +
					"0.0066 ft\n" +
					"0.0022 yard\n" +
					"1.2e-06 mile\n" ;		 
		 final String expectedOutput =
		 normalizeExpectedOutput(unNormalizedExpectedOutput);
		 // This is the technique to capture the textual output
		 // from System.out.print calls.
		 final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		 System.setOut(new PrintStream(myOut));;
		 // test
		 UnitsConvertor.main(null);
		 // check results
		 final String printResult = myOut.toString();
		 assertTrue(expectedOutput.equals( printResult));
		 // return System variables to their previous values.
		 System.setOut(sysOut);
		 System.setIn(sysIn);
		}
	
	@Test
	public void mainImperialTest() {//enter valid input and pass
		// simulated input
		String input = "20 in";
		// save current System.in and System.out
		 InputStream sysIn = System.in;
		 PrintStream sysOut = System.out;
		 InputStream myIn = new ByteArrayInputStream(input.getBytes());
		 System.setIn(myIn);		 
		 final String unNormalizedExpectedOutput =
					"Please Enter the input value followed by the unit:\n" +
					"20 in is:\n" +
					"508.000000 mm\n" +
					"50.800000 cm\n" +
					"0.508000 m\n" +
					"0.000508 km\n";		 
		 final String expectedOutput =
		 normalizeExpectedOutput(unNormalizedExpectedOutput);
		 // This is the technique to capture the textual output
		 // from System.out.print calls.
		 final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		 System.setOut(new PrintStream(myOut));;
		 // test
		 UnitsConvertor.main(null);
		 // check results
		 final String printResult = myOut.toString();
		 assertEquals(expectedOutput, printResult);
		 // return System variables to their previous values.
		 System.setOut(sysOut);
		 System.setIn(sysIn);
		}
	
	@Test
	public void testInvalidMainInput() { //fail because no long value has been entered
		// simulated input
		String input = "Jibberish";
		// save current System.in and System.out
		 InputStream sysIn = System.in;
		 PrintStream sysOut = System.out;
		 InputStream myIn = new ByteArrayInputStream(input.getBytes());
		 System.setIn(myIn);		 
		 final String unNormalizedExpectedOutput =
				 "Please Enter the input value followed by the unit:\n" +
					"Invalid input";		 
		 final String expectedOutput =
		 normalizeExpectedOutput(unNormalizedExpectedOutput);
		 // This is the technique to capture the textual output
		 // from System.out.print calls.
		 final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		 System.setOut(new PrintStream(myOut));;
		 // test
		 UnitsConvertor.main(null);
		 // check results
		 final String printResult = myOut.toString();
		 assertEquals(expectedOutput, printResult);
		 // return System variables to their previous values.
		 System.setOut(sysOut);
		 System.setIn(sysIn);
		}
	
	@Test
	public void testInvalidMainUnits() { //fail because invalid unit values have been entered
		// simulated input
		String input = "18 Jibberish";
		// save current System.in and System.out
		 InputStream sysIn = System.in;
		 PrintStream sysOut = System.out;
		 InputStream myIn = new ByteArrayInputStream(input.getBytes());
		 System.setIn(myIn);		 
		 final String unNormalizedExpectedOutput =
				 "Please Enter the input value followed by the unit:\n" +
					"Invalid input";		 
		 final String expectedOutput =
		 normalizeExpectedOutput(unNormalizedExpectedOutput);
		 // This is the technique to capture the textual output
		 // from System.out.print calls.
		 final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		 System.setOut(new PrintStream(myOut));;
		 // test
		 UnitsConvertor.main(null);
		 // check results
		 final String printResult = myOut.toString();
		 assertEquals(expectedOutput, printResult);
		 // return System variables to their previous values.
		 System.setOut(sysOut);
		 System.setIn(sysIn);
		}
	
	//Test main method. Do an Illlegal argument exception and check if the buffer is not null
	/******* End group of tests for the main method **********/
	
	// normalizeExpectedOutput - generate the eol character at run-time.
		// then there is no need to hard-code "\r\n" or "\n" for eol
		// and string comparisons are portable between Windows, macOS, Linux.
		public String normalizeExpectedOutput(String expectedOutput) {
		String normExpectedOutput;
		String [] outputs = expectedOutput.split("\n");
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		for (String str: outputs) {
		pw.println(str);
		}
		pw.close();
		normExpectedOutput = sw.toString();
		return normExpectedOutput;
		}

}
