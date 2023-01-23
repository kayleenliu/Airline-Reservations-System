package sait.frms.manager;

import java.util.*;
import sait.frms.problemdomain.*;
import java.io.*;

/**
 * Used to manage the flights in the program.
 */

public class FlightManager {
	private final String FLIGHTS_PATH="res/flights.csv";
	private final String AIRPORTS_PATH="res/flights.csv";
	public final static String WEEKDAY_ANY="ANY";
	public final static String WEEKDAY_SUNDAY="SUNDAY";
	public final static String WEEKDAY_MONDAY="MONDAY";
	public final static String WEEKDAY_TUESDAY="TUESDAY";
	public final static String WEEKDAY_WEDNESDAY="WEDNESDAY";
	public final static String WEEKDAY_THURSDAY="THURSDAY";
	public final static String WEEKDAY_FRIDAY="FRIDAY";
	public final static String WEEKDAY_SATURDAY="SATURDAY";

	private ArrayList<Flight> flights;
	private ArrayList<String> airports;
	
	/**
	 *  Constructor for our flight manager
	 *  @throws IOException when unable to read from file
	 */
	public FlightManager() throws IOException {
		super();
		this.airports = new ArrayList<String>();
		
		populateFlights();
		populateAirports();

	}
	/**
	 *  Getter for flights
	 *  @return a list of flights
	 */
	public ArrayList<Flight> getFlights() {
		return flights;
	}
	/**
	 *  Getter for airports
	 *  @return a list of airports
	 */
	public ArrayList<String> getAirports() {
		return airports;
	}
	/**
	 *  Returns a flight based on the code
	 *  @param code a flight code
	 *  @return flight based on flight code.
	 */ 
	public Flight findFlightByCode(String code) {
		for(int i=0;i<this.flights.size();i++) {
			if(flights.get(i).getCode().equals(code)) {
				return flights.get(i);
			}
		}
		return null;
	}
	/**
	 *  Returns list of flights that match the input
	 *  @param from location
	 *  @param to location
	 *  @param weekday day of the week. can also be any
	 *  @return a list of flights.
	 */ 
	public ArrayList<Flight> findFlights(String from, String to, String weekday){ 
		ArrayList<Flight> matchingFlights = new ArrayList<Flight>();
		for(int i=0;i<this.flights.size();i++) {
			if(weekday.equals("Any")) {
				if(flights.get(i).getFrom().equals(from) && flights.get(i).getTo().equals(to)) {
					matchingFlights.add(flights.get(i));
				}
			}
			else if(flights.get(i).getFrom().equals(from) && flights.get(i).getTo().equals(to)  && flights.get(i).getWeekday().equals(weekday) ) {
				matchingFlights.add(flights.get(i));
			}
		}
		return matchingFlights;
	}
	/**
	 * Reads a csv file; populating the Fligts list with the data in the file
	 * @throws FileNotFoundException Thrown when flight csv not found.
	 */
	private void populateFlights() throws FileNotFoundException {
		Scanner fileReader=new Scanner(new File(FLIGHTS_PATH));
		this.flights = new ArrayList<Flight>();
		while(fileReader.hasNextLine()) {
			String[] values = fileReader.nextLine().split(",");	
			flights.add(new Flight(values[0],values[1],values[2],values[3],values[4],Integer.parseInt(values[5]),Double.parseDouble(values[6])));
		}
		
	}
	/**
	 * Reads a csv file; populating the Airport list with the data in the file
	 * @throws FileNotFoundException Thrown when airport csv not found.
	 */
	private void populateAirports() throws FileNotFoundException {
		Scanner fileReader=new Scanner(new File(AIRPORTS_PATH));
		while(fileReader.hasNextLine()) {
			airports.add(fileReader.nextLine());
		}
		fileReader.close();
	}
	/**
	 * deciphers unique "to" locations from the flight list
	 * @return an array of unique locations
	 */
	public String[] getUniqueToLocations(){
		ArrayList<String> uniqueLocationList=new ArrayList<String>();
		for(Flight flight : this.flights) {
			if(!uniqueLocationList.contains(flight.getTo())) {
				uniqueLocationList.add(flight.getTo());
			}
		}
		Collections.sort(uniqueLocationList);
		String[] result = new String[uniqueLocationList.size()];
		for(int i=0;i<result.length;i++) {
			result[i]=uniqueLocationList.get(i);
		}
		return result;
	}
	/**
	 * deciphers unique "from" locations from the flight list
	 * @return A array of unique locations
	 */
	public String[] getUniqueFromLocations(){
		ArrayList<String> uniqueLocationList=new ArrayList<String>();
		for(Flight flight : this.flights) {
			if(!uniqueLocationList.contains(flight.getFrom())) {
				uniqueLocationList.add(flight.getFrom());
			}
		}
		Collections.sort(uniqueLocationList);
		String[] result = new String[uniqueLocationList.size()];
		for(int i=0;i<result.length;i++) {
			result[i]=uniqueLocationList.get(i);
		}
		return result;
	}
	
	
}
