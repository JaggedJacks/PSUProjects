package edu.psgv.sweng586;
/*
 * Author: Joshua Daniels
 * Code Challenge 2
 */

import java.util.Scanner;

public class CodingChallenge2JDD {
	public static void main(String[] args) {
		int var, sumFactors; //var is userInput. sumFactors will hold the sum of all of the proper factors
		String factors; //The label that will tell the user what the factors are
		Scanner scanner = new Scanner(System.in); //scan for user input
		while (true) {//infinite loop. (entering a zero later in the code will break this loop)
		sumFactors = 0; //reset the sum of factors upon every re-run
		System.out.print("Please Enter a positive integer. You could also enter 0 to quit:"); //prompt user input		
		var = scanner.nextInt(); //read the input
		if (var == 0) { //if input is 0, inform user and exit
			System.out.println("Thank you for using Perfect Number Finder. Goodbye.");
			scanner.close();
			System.exit(0);
		}//if input is not 0, continue
		factors = "The factors are: "; 
		for (int i = 1; i < var; i++) {//for every number between zero and the user input
			if (var % i == 0) { //check if it is a proper factor.
				sumFactors = sumFactors + i; //sum the factors together
				factors+=i+", ";//append the factor to the list of proper factors the user will see in the end
			}
		}

		if (factors.charAt(factors.length()-2)==',') {//remove the comma at the end of the list of factors if necessary and print them out
			System.out.println(factors.substring(0,factors.length()-2));
		} else
		System.out.println(factors);
		
		//compare factors to the user input and determine what type of number was entered
		if (sumFactors < var)
			System.out.println("Number is imperfect deficient.");
		else if (sumFactors > var)
			System.out.println("Number is imperfect abundant.");
		else
			System.out.println("Given number is Perfect.");
		System.out.println("");
	}
	}
}
