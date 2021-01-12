package edu.psgv.sweng586;

/*
 * Author: Joshua Daniels
 * Code Challenge 3
 */

import java.util.Scanner;

public class CodingChallenge3JDD {
	static Scanner scanner;
	/*
	 * Main method. Uses in input scanner for the start and stop numbers. Calls the "Categorize" method
	 * to determine the type of number entered.
	 */
	public static void main(String[] args) {
		int min, max; // variables for the minimum, maximum and sum of the factors of each number
		scanner = new Scanner(System.in); 
		System.out.print("Enter a start number: "); 
			min = scanner.nextInt(); 
			System.out.println("");
			System.out.print("Enter a stop number: "); 
			max = scanner.nextInt();
			categorize(min, max); //process the user input
			System.out.println("End");
	}

	/*
	 * Method to process the input and determine the category of the numbers between the min and max
	 */
	public static void categorize(int min, int max) {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		int sumFactors; //store the sum of the factors to determine if the number is perfect/imperfect
		boolean prime; //boolean to determine if the number is prime
		
		//determine if number is prime, and  determine the sum of the input number's factors
		for (int var = min; var <= max; var++) { 
			prime = true; 
			sumFactors = 0; 
			for (int j = 1; j < var; j++) {
				if (var % j == 0) { 
					sumFactors = sumFactors + j; 
					if(j > 1 && j<=var/2) {
						prime = false;
						}
					}					
			}

			// compare the sum of the factors to the user input and determine what type of number was
			// entered
			if(prime)
				System.out.println("The number "+var+" is: Prime");
			else if (sumFactors < var)
				System.out.println("The number "+var+" is: Imperfect Deficient");
			else if (sumFactors > var)
				System.out.println("The number "+ var+" is: Imperfect Abundant");
			else
				System.out.println("The number "+ var +" is: Perfect");
			System.out.println("");
		}
	}
}
