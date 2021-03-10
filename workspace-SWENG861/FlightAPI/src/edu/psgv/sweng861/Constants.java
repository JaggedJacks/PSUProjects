package edu.psgv.sweng861;

//useful constants defined in an interface to be used throughout the program
public interface Constants { 
	final int ROUND_TRIP = 8;
	final int ONE_WAY = 5;
	final String[] smallHeader = { "Station", "City", "Country", "Price", "Carrier" };
	final String[] largeHeader = { "Station", "City", "Country", "Return Station", "Return City", "Return Country", "Price", "Carrier" };
}
