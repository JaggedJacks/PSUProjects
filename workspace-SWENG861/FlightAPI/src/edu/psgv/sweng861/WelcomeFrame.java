package edu.psgv.sweng861;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

//Binary is set to run this class first. Contains the initial GUI that asks the user for relevant information
	//Declare all of the GUI elements
public class WelcomeFrame {
	public static JTextField countryTextField, currencyTextField, localeTextField, outboundLocationTextField,
	inboundLocationTextField, outboundDateTextField, inboundDateTextField;
	public static JLabel errorLabel;
	
	public static void main(String[] args) {
		//Set up the client
		OkHttpClient client = new OkHttpClient();
		
		//Set up the frame
		JFrame frame = new JFrame("MyFrame");
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Create top label
		JLabel banner = new JLabel("Welcome to MyFlights!", SwingConstants.CENTER );
		banner.setFont(new Font("Serif", Font.BOLD, 20));		
		c.gridx = 0;
		c.ipadx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.EAST;
		c.gridy = 0;
		c.weightx = 0;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth= GridBagConstraints.REMAINDER;
		panel.add(banner, c);
		
		//Label for country
		JLabel country = new JLabel("Country: ", SwingConstants.RIGHT);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
		c.weightx = 1;
		c.gridy = 100;
		c.gridwidth= 1;
		panel.add(country, c);

		//Text field for country
		countryTextField = new JTextField();
		c.gridx = 100;
		c.gridy = 100;
		countryTextField.setColumns(10);
		panel.add(countryTextField, c);
		
		//Label for currency
		JLabel currency = new JLabel("Currency: ", SwingConstants.LEFT);
		c.gridx = 0;
		c.gridy = 200;
		panel.add(currency, c);
		
		//Text field for currency
		currencyTextField = new JTextField();
		c.gridx = 100;
		c.gridy = 200;
		currencyTextField.setColumns(10);
		panel.add(currencyTextField, c);
		
		//Label for locale
		JLabel locale = new JLabel("Locale: ", SwingConstants.LEFT);
		c.gridx = 0;
		c.gridy = 300;
		panel.add(locale, c);
		
		//Text field for locale
		localeTextField = new JTextField();
		c.gridx = 100;
		c.gridy = 300;
		localeTextField.setColumns(10);
		panel.add(localeTextField, c);
		
		//Label for outbound location
		JLabel outboundLocation = new JLabel("Origin: ", SwingConstants.LEFT);
		c.gridx = 0;
		c.gridy = 400;
		panel.add(outboundLocation, c);
		
		//Text field for outbound location
		outboundLocationTextField = new JTextField();
		c.gridx = 100;
		c.gridy = 400;
		outboundLocationTextField.setColumns(10);
		panel.add(outboundLocationTextField, c);
		
		//Label for outbound date
		JLabel outboundDate = new JLabel("Outbound Date: ", SwingConstants.LEFT);
		c.gridx = 0;
		c.gridy = 500;
		panel.add(outboundDate, c);
		
		//Text field for outbound date
		outboundDateTextField = new JTextField();
		c.gridx = 100;
		c.gridy = 500;
		outboundDateTextField.setColumns(10);
		panel.add(outboundDateTextField, c);
		
		//Label for inbound location
		JLabel inboundLocation = new JLabel("Destination: ", SwingConstants.LEFT);
		c.gridx = 0;
		c.gridy = 600;
		panel.add(inboundLocation, c);
		
		//Text field for inbound location
		inboundLocationTextField = new JTextField();
		c.gridx = 100;
		c.gridy = 600;
		inboundLocationTextField.setColumns(10);
		panel.add(inboundLocationTextField, c);
		
		//Label for inbound date
		JLabel inboundDate = new JLabel("Return Date: ", SwingConstants.LEFT);
		c.gridx = 0;
		c.gridy = 700;
		panel.add(inboundDate, c);
		
		//Text field for inbound date
		inboundDateTextField = new JTextField();
		c.gridx = 100;
		c.gridy = 700;
		inboundDateTextField.setColumns(10);
		panel.add(inboundDateTextField, c);
			
		//Label for country
		errorLabel = new JLabel("");
		errorLabel.setVisible(false);
		c.gridx = 0;
		c.gridy = 900;
		c.gridwidth= GridBagConstraints.REMAINDER;
		panel.add(errorLabel, c);
		
		//Button to search for flights
		JButton button = new JButton("Search Flights");
		//Attach action listener from another class. When performed, the other class creates a window with the flight options.
		FlightModel al = new FlightModel(client, countryTextField, currencyTextField, localeTextField, outboundLocationTextField,
				inboundLocationTextField, outboundDateTextField, inboundDateTextField, errorLabel);
		button.addActionListener(al);
		c.gridx = 100;
		c.gridy = 800;
		c.gridwidth= 1;
		panel.add(button, c);
		
		//Finalize frame
		frame.add(panel);
		frame.setTitle("MyFlights");
		frame.setMinimumSize(new Dimension(300,300));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
