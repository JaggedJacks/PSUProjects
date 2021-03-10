package edu.psgv.sweng861;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FlightChoices implements ActionListener, Constants { //Window to show available flights
	
	//Declare GUI elements
	JFrame frame;
	JLabel banner;
	JTable flightTable;
	static JButton chooseFlight;
	
	FlightChoices ( String [][] table) {//Constructor to construct the window
	//Create the frame and layout
	frame = new JFrame("MyFlights");
	JPanel panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	
	//Add the top label	
	banner = new JLabel ("Here are your available flights:");
	c.gridx = 0;
	c.gridy = 0;
	panel.add(banner, c);	
 
	
	/************Add the flight table************/	
	if(table[0].length==ONE_WAY) {
		flightTable = new JTable(table, smallHeader);
	}
	else
		flightTable = new JTable(table, largeHeader);
	c.gridx = 0;
	c.gridy = 100;
	JScrollPane myPane = new JScrollPane(flightTable);	
	panel.add(myPane, c);
	/**************************************/
	   
	//Add the select button
	chooseFlight = new JButton("Select");
	chooseFlight.addActionListener(this);
	c.gridx = 0;
	c.gridy = 600;
	panel.add(new JScrollPane(chooseFlight), c);
	
	//Add the close button
	chooseFlight = new JButton("Close");
	chooseFlight.addActionListener(this);
	c.gridx = 0;
	c.gridy = 700;
	panel.add(new JScrollPane(chooseFlight), c);
	
	//finalize panel
	frame.add(panel);
	frame.setSize(new Dimension(700, 700));
	panel.setVisible(true);
	frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {//Depending on which button is pressed, perform the following actions:
		if(e.getActionCommand().contentEquals("Select"))
			banner.setText("You chose "+ flightTable.getValueAt(flightTable.getSelectedRow(),flightTable.getColumnCount()-1) +
		". Your price is $"+flightTable.getValueAt(flightTable.getSelectedRow(),flightTable.getColumnCount()-2)); //print the price of the quote
		else if (e.getActionCommand().contentEquals("Close"))
		frame.dispose(); //exit
		// TODO Auto-generated method stub
		
	}
}
