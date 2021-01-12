//============================================================================
// Name        : Rampant.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
using namespace std;

#include <fstream>
#include <iostream>
using namespace std;

class Car{
	private: //access modifier is private by default (you don't have to write this line)
	string brand;
	string model;
	public:
	Car (string, string); //constructor
	void printCar(){ //method
	cout<<"You bought a "<<brand<<" "<<model<<"! ";
	cout<<"Good choice!";
	}
};
	Car::Car (string brand , string model){ // Methods are defined with "::" outside the class
		this -> brand = brand;
		this -> model = model;
	}


int main() {
	int low;
	int high;
	int step;
	string smt;
	Car myCar ("Honda", "Civic");
	myCar.printCar();
	cout << "Type something to print to a file: ";

//	cin >> smt;
//	ofstream myfile;
//	myfile.open("WhoKnows.txt");
//	myfile << "Writing this to a file: "<< smt;
//	myfile.close();

	return 0;
}
