package edu.psgv.sweng586;
/*
 * Author: Joshua Daniels
 * Code Challenge 4
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodingChallenge4JDD {
	static Scanner scanner;

	/*
	 * Main method. Uses in input scanner for the start and stop numbers. Calls the
	 * "Categorize" method to determine the type of number entered.
	 */
	public static void main(String[] args) {
		int min, max; // variables for the minimum, maximum and sum of the factors of each number
		String fileName;
		
		System.out.println("Welcome to the Prime, Perfect Square, and Perfect Number Tester");
		System.out.println("");
		scanner = new Scanner(System.in); //Scanner for user input
		System.out.print("Enter the name of the output file: ");
		fileName = scanner.next();
		
		File numberFile = new File(fileName); //File to be created
		try {
			numberFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File could not be created");
			System.exit(1);
		}
		
		System.out.println("");
		System.out.print("Enter a start number: ");
		min = scanner.nextInt();
		System.out.println("");
		System.out.print("Enter a stop number: ");
		max = scanner.nextInt();
		try {
			categorize(min, max, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // process the user input
		System.out.println("End");
	}

	/*
	 * Method to process the input and determine the category of the numbers between
	 * the min and max. Print results and write them to a file.
	 */
	public static void categorize(int min, int max, String numFile) throws IOException {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		int sumFactors; // store the sum of the factors to determine if the number is perfect/imperfect
		boolean prime; // boolean to determine if the number is prime
		double radical;

		FileWriter myWriter = new FileWriter(numFile);

		// determine if number is prime, and determine the sum of the input number's
		// factors
		for (int var = min; var <= max; var++) {
			radical = Math.sqrt(var);
			prime = true;
			sumFactors = 0;
			for (int j = 1; j < var; j++) {
				if (var % j == 0) {
					sumFactors = sumFactors + j;
					if (j > 1 && j <= var / 2) {
						prime = false;
					}
				}
			}
			// compare the sum of the factors to the user input and determine what type of
			// number was
			// entered
			if (radical - Math.floor(radical) == 0) { //perfect square checking added
				System.out.println("The number " + var + " is: Perfect Square");
				myWriter.write("The number " + var + " is: Perfect Square" + "\n"); 
			}
			else if (prime) {
				System.out.println("The number " + var + " is: Prime");
				myWriter.write("The number " + var + " is: Prime" + "\n");
			} else if (sumFactors < var) {
				System.out.println("The number " + var + " is: Imperfect Deficient");
				myWriter.write("The number " + var + " is: Imperfect Deficient"+ "\n");
			} else if (sumFactors > var) {
				System.out.println("The number " + var + " is: Imperfect Abundant");
				myWriter.write("The number " + var + " is: Imperfect Abundant"+ "\n");
			} else {
				System.out.println("The number " + var + " is: Perfect");
				myWriter.write("The number " + var + " is: Perfect"+ "\n");
			}
			System.out.println("");
			myWriter.write("\n");
		}
		System.out.println("File \""+numFile+"\" created");
		System.out.println("");
		myWriter.close();
	}

}
