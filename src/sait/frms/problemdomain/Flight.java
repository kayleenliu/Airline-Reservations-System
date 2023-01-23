package sait.frms.problemdomain;
/**
 * A template used for storing data of a flight
 */
public class Flight {
	private String code;
	private String airlineName;
	private String from;
	private String to;
	private String weekday;
	private String time;
	private int seats;
	private double costPerSeat;
	
	/**
	 * A Constructor for a flight object
	 * @param code Flight Code
	 * @param fromCode From Location
	 * @param toCode To Location
	 * @param weekday Day of week flight occurs
	 * @param time Time flight occurs
	 * @param seats Number of seats on flight
	 * @param costPerSeat Cost of the seat on the flight
	 */
	
	public Flight(String code, String fromCode, String toCode, String weekday, String time, int seats, double costPerSeat) {
		super();
		this.code=code;
		parseCode(this.code);
		this.from = fromCode;
		this.to = toCode;
		this.weekday = weekday;
		this.time = time;
		this.seats = seats;
		this.costPerSeat = costPerSeat;
	}
	/**
	 * getter for flight code
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * getter for airline name
	 * @return the airline
	 */
	public String getAirlineName() {
		return airlineName;
	}
	/**
	 * getter for from location
	 * @return the location
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * getter for to location
	 * @return the location
	 */
	public String getTo() {
		return to;
	}
	/**
	 * weekday of flight
	 * @return the day
	 */
	public String getWeekday() {
		return weekday;
	}
	/**
	 * time of the flight
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * seats of the flight
	 * @return the seats
	 */
	public int getSeats() {
		return seats;
	}
	/**
	 * used to return cost of seat
	 * @return the seat cost
	 */
	public double getCostPerSeat() {
		return costPerSeat;
	}
	
	/**
	 * used to decrement the number of seats
	 */
	public void reserveSeat() {
		if(this.seats>0) {
			this.seats--;
		}
	}
	
	/**
	 * determines if a flight is domestic or not
	 * @return if a flight is domestic or not
	 */
	public boolean isDomestic() {
		if(this.from.substring(0,1).equals(this.to.substring(0,1)) && this.from.substring(0,1).equals("Y")) {
			return true;
		}
		return false;
	}
	/**
	 * used to parse the airline name for the flight
	 * @param code flight code
	 */
	private void parseCode(String code) {
		this.airlineName=code.substring(0,2);
		
		if(this.airlineName.equals("OA")) {
			this.airlineName="Otto Airlines";
		}
		else if(this.airlineName.equals("CA")) {
			this.airlineName="Conned Air";
		}
		else if(this.airlineName.equals("TB")) {
			this.airlineName="Try a Bus Airways";
		}
		else if(this.airlineName.equals("VA")) {
			this.airlineName="Vertical Airways";
		}
	}
	/**
	 * the to string method for flight
	 * @return a string with the flight information we display in the center panel list
	 */
	@Override
	public String toString() {
		return code +", From: "+ from + ", To: " + to + ", Day: "+ weekday + ", Cost: " + costPerSeat;
	}
}
