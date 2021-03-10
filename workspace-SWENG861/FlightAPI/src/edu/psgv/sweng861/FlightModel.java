package edu.psgv.sweng861;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

//Flight Model, handles processing
public class FlightModel implements ActionListener, Constants {
	static OkHttpClient client;
	JTextField countryTextField, currencyTextField, localeTextField, outboundLocationTextField,
			inboundLocationTextField, outboundDateTextField, inboundDateTextField;
	static JLabel errorLabel;
	static FlightChoices fc; // class that contains the JPanel that shows flight choices
	private static String[][] flightTable; // will be added to a JTable that lists flight options

	FlightModel(OkHttpClient client, JTextField countryTextField, JTextField currencyTextField, JTextField localeTextField,
			JTextField outboundLocationTextField, JTextField inboundLocationTextField, JTextField outboundDateTextField,
			JTextField inboundDateTextField, JLabel errorLabel) { // constructor to set up all of the UI elements for
																	// editing
		this.countryTextField = countryTextField;
		this.currencyTextField = currencyTextField;
		this.localeTextField = localeTextField;
		this.outboundLocationTextField = outboundLocationTextField;
		this.inboundLocationTextField = inboundLocationTextField;
		this.outboundDateTextField = outboundDateTextField;
		this.inboundDateTextField = inboundDateTextField;
		this.errorLabel = errorLabel;
		FlightModel.client = client;
		initializeTextFields();
	}

	// set all textfields to an initial value
	private void initializeTextFields() {
		countryTextField.setText("US");
		currencyTextField.setText("USD");
		localeTextField.setText("en-US");
		outboundLocationTextField.setText("SFO-sky");
		inboundLocationTextField.setText("JFK-sky");
		outboundDateTextField.setText("2020-11-02");
		inboundDateTextField.setText("2020-11-05");
	}
	
	// Upon button press use OKHttp to send a request to SkyScanner
	// the error label should be cleared. Whether it is reinstated depends on the
	// request in progress
	@Override
	public void actionPerformed(ActionEvent e) {
		errorLabel.setVisible(false);
		if (validInput()) {// check all of the inputs first
			// what to do if dates are in the past?
			if (!inboundDateTextField.getText().equals("")) { // If round trip date is entered, request for round trip
				searchInbound(countryTextField.getText(), currencyTextField.getText(), localeTextField.getText(),
						outboundLocationTextField.getText(), inboundLocationTextField.getText(),
						outboundDateTextField.getText(), inboundDateTextField.getText());
			} else { // if round trip date isn't entered, request a one-way trip
				searchOutbound(countryTextField.getText(), currencyTextField.getText(), localeTextField.getText(),
						outboundLocationTextField.getText(), inboundLocationTextField.getText(),
						outboundDateTextField.getText());
			}
		}

	}

	// Display an error if there is one
	private static void displayError(String str) {
		errorLabel.setText(str);
		errorLabel.setForeground(Color.RED);
		errorLabel.setVisible(true);
	}
	
	//Given the response from the server, determine if there are any further errors and display them
			//if there are no errors, proceed to searching for the quotes
	public static void processResponse(String reply, boolean roundtrip) { 
		int numRows = roundtrip ? 	ROUND_TRIP: ONE_WAY; //Accomodate the new table for how many rows it may need
		JSONObject obj = new JSONObject(reply);
		if (reply.contains("ValidationErrors")) { // if there are errors, print them
			String param = obj.getJSONArray("ValidationErrors").getJSONObject(0).getString("ParameterName");
			if(param.contentEquals("OutboundDate")||param.contentEquals("InboundDate")||param.contentEquals("OutboundDate & InboundDate")) //date errors
				displayError(obj.getJSONArray("ValidationErrors").getJSONObject(0).getString("Message"));
				else //Errors in other parameters
				displayError("Incorrect "
					+ obj.getJSONArray("ValidationErrors").getJSONObject(0).getString("ParameterName").toLowerCase()
					+ " value.");
		} else if (obj.getJSONArray("Quotes").length() == 0) { //No available quotes. Not an error, but still don't proceed.
			displayError("There are no quotes for you at this time.");
		} else {//else, find the quotes
			flightTable = new String[obj.getJSONArray("Quotes").length()][numRows]; // make a 2dim array to prepare a
			// table with the departure
			// Airport, City, Country and
			// price. Add an extra 3 columns
			// for return values
			searchQuotes(obj, flightTable);// find the quotes
		}
		
	}

	// validate text boxes
			// regex patterns to verify valid text
	public boolean validInput() { 
		Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		Pattern countryPattern = Pattern.compile("[A-Z][A-Z]([A-Z])?");
		Pattern currencyPattern = countryPattern;
		Pattern localePattern = Pattern.compile("[a-z]{2}-[A-Z]{2}");
		Pattern originPattern = Pattern.compile("[A-Z]{3}([A-Z])?-[a-z]{3}");
		Pattern destinationPattern = originPattern;

		// if-else block to check if the aforementioned patterns are adhered to
		if (!datePattern.matcher(outboundDateTextField.getText()).matches()) {
			displayError("Outbound date not entered properly.");
			return false;
		} else if (!datePattern.matcher(inboundDateTextField.getText()).matches()
				&& !inboundDateTextField.getText().contentEquals("")) {
			displayError("Inbound date not entered properly.");
			return false;
		} else if (!countryPattern.matcher(countryTextField.getText()).matches()) {
			displayError("Country name not entered properly.");
			return false;
		} else if (!currencyPattern.matcher(currencyTextField.getText()).matches()) {
			displayError("Currency not entered properly.");
			return false;
		} else if (!localePattern.matcher(localeTextField.getText()).matches()) {
			displayError("Locale not entered properly.");
			return false;
		} else if (!originPattern.matcher(outboundLocationTextField.getText()).matches()) {
			displayError("Outbound location not entered properly.");
			return false;
		} else if (!destinationPattern.matcher(inboundLocationTextField.getText()).matches()) {
			displayError("Return flight not entered properly.");
			return false;
		}
		return true;
	}
	
	// Search the Places array to associate the place with the ID of the
	// location specified
	public static void searchPlaces(JSONObject route, int placeId, String msg, String[][] flightTable,
			boolean returnTrip, int quoteIndex) { 
		JSONArray placeArray = route.getJSONArray("Places");
		JSONObject placeObject;
		int i;
		for (i = 0; i < placeArray.length(); i++) {
			placeObject = placeArray.getJSONObject(i);
			if (placeObject.getInt("PlaceId") == placeId) {
				if (!returnTrip) { // account for round trip selection
					flightTable[quoteIndex][0] = placeObject.getString("Name");
					flightTable[quoteIndex][1] = placeObject.getString("CityName");
					flightTable[quoteIndex][2] = placeObject.getString("CountryName");
				} else {
					flightTable[quoteIndex][3] = placeObject.getString("Name");
					flightTable[quoteIndex][4] = placeObject.getString("CityName");
					flightTable[quoteIndex][5] = placeObject.getString("CountryName");
				}
				break;
			}
		}
		return;
	}

	public static void searchCarriers(JSONObject route, JSONArray carrierInQuote, boolean roundTrip, int index) {// Search for the carriers associated
																					// with the carrier ID specified by
																					// the quote
		JSONArray carrierCatalog = route.getJSONArray("Carriers");
		for (int i = 0; i < carrierInQuote.length(); i++) {//for each quote
			for (int j = 0; j < carrierCatalog.length(); j++) {//search the catalog ids for the catalog that belongs to the quote
				if (carrierInQuote.getInt(i) == carrierCatalog.getJSONObject(j).getInt("CarrierId")) {
					if (roundTrip)																
						flightTable[index][ROUND_TRIP-1]= carrierCatalog.getJSONObject(j).getString("Name");
					else
						flightTable[i][ONE_WAY-1]= carrierCatalog.getJSONObject(j).getString("Name");
				}
			}
		}
		System.out.println("");

	}

	public static void searchOutbound(String country, String currency, String locale, String origin, String destination,
			String outboundDate) { // Search outbound flights. Leave inbound date null
		String webUrl = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/"
				+ country + "/" + currency + "/" + locale + "/" + origin + "/" + destination + "/" + outboundDate
				+ "?inboundpartialdate=null";

		Request request = new Request.Builder().url(webUrl).get()
				.addHeader("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
				.addHeader("x-rapidapi-key", "c62ae135ddmsh8a2644f822aebccp1eb96fjsn11a6d7573b18").build();
		try {//Collect the resonse and process it. Account for the request failing.
			Response response = client.newCall(request).execute();
			String reply = response.body().string();
			processResponse(reply, false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			displayError("Failed to submit request, please try again.");
			e1.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void searchInbound(String country, String currency, String locale, String origin, String destination,
			String outboundDate, String inboundDate) {
		OkHttpClient client = new OkHttpClient();
		String webUrl = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/"
				+ country + "/" + currency + "/" + locale + "/" + origin + "/" + destination + "/" + outboundDate + "/"
				+ inboundDate;
		Request request = new Request.Builder().url(webUrl).get()
				.addHeader("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
				.addHeader("x-rapidapi-key", "c62ae135ddmsh8a2644f822aebccp1eb96fjsn11a6d7573b18").build();
		try {
			//Collect the resonse and process it. Account for the request failing.
			Response response = client.newCall(request).execute();
			String reply = response.body().string();
			processResponse(reply, true);

		} catch (IOException e) {
			// print out error to the gui and try again
			// TODO Auto-generated catch block
			displayError("Failed to submit request, please try again.");
			e.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block

			e1.printStackTrace();
		}
	}

	public static void searchQuotes(JSONObject obj, String[][] flightTable) {// Method to search the content of the JSON
																				// object received from the web request
																				// and place in a 2d array to be sent to
																				// a JTable
		JSONArray quoteArray = obj.getJSONArray("Quotes");
		for (int i = 0; i < quoteArray.length(); i++) {// There can be more than one quote. A price is associated with
														// each quote.
			JSONObject quotes = quoteArray.getJSONObject(i);
			// include carrier info
			// info about flight leg
			JSONObject flightLeg = quotes.getJSONObject("OutboundLeg");
			int originId = flightLeg.getInt("OriginId");
			int destinationId = flightLeg.getInt("DestinationId");
						
			JSONArray carrierIds = flightLeg.getJSONArray("CarrierIds");
			if (flightTable[0].length == ONE_WAY)// Create one-way flight list if array size fits
			{
				flightTable[i][ONE_WAY-2] = ("" + quotes.getInt("MinPrice"));
				searchCarriers(obj, carrierIds, false, i);
				searchPlaces(obj, originId, "Departure Info", flightTable, false, i);
			} else if (flightTable[0].length == ROUND_TRIP) { // Create one-way flight list with more columns if needed
				// place inbound leg info
				flightTable[i][ROUND_TRIP-2] = ("" + quotes.getInt("MinPrice"));
				searchCarriers(obj, carrierIds, true, i);
				searchPlaces(obj, originId, "Departure Info", flightTable, false, i);
				searchPlaces(obj, destinationId, "Return Info", flightTable, true, i);
			}

			
			
		}
		//Create the new frame containing available flight choices given the user-provided parameters
		FlightChoices fc = new FlightChoices(flightTable);
	}
}
