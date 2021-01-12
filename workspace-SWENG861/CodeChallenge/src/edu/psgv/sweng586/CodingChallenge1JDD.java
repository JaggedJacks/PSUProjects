package edu.psgv.sweng586;

import java.util.regex.Pattern;

/*
 *Author: Joshua Daniels 
 */
public class CodingChallenge1JDD {
	/*
	 * Main method: checks for user input and processes it
	 */
	public static void main(String[] args) {
		if (args.length < 1) {// If no parameters are entered, say so
			System.out.println("Please enter at least one valid character.");
			System.exit(1);
			;
		} else if (args.length > 1) {//If too many parameters are entered, warn the user
			System.out.println("Too many arguments entered");
			System.exit(1);
			;
		}else { //otherwise, print out what the user typed and say whether it's valid
			System.out.println("You typed: "+ args[0]);
			spotCheck(args[0]);//method to determine whether input is valid
		}
	}
	
	/*
	 * Method for checking input. Input can contain any combination of numbers or letters in any case.
	 * No special characters or whitespaces allowed
	 */
	public static void spotCheck (String str) {
		Pattern wordPattern = Pattern.compile("\\w{8,}");
		if (!wordPattern.matcher(str).matches()) { //if string is less than 8 characters, return.
			System.out.println("INVALID. Please type at least 8 characters.");
			return;
		}
		System.out.println("Congratulations, your input is VALID!"); //otherwise, all characters in the string are valid. Indicate in print statement
		return;
		
	}
	
	
}
