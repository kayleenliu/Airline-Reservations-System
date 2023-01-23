package sait.frms.problemdomain;
/**
 * A template used for storing data of a reservation
 */
public class Reservation {
	private String code;
	private String flightCode;
	private String airline;
	private String name;
	private String citizenship;
	private double cost;
	private boolean active;


	/**
	 * A constructor for the reservation object
	 * @param code reservation code
	 * @param flightCode the flight code
	 * @param airline airline name
	 * @param name customer name
	 * @param citizenship customer location
	 * @param cost cost of the reservation
	 * @param active status of the reservation
	 */
	public Reservation(String code, String flightCode, String airline, String name, String citizenship, double cost, boolean active) {
		super();
		this.code = code;
		this.flightCode = flightCode;
		this.airline = airline;
		this.name = name;
		this.citizenship = citizenship;
		this.cost = cost;
		this.active = active;
	}
	/**
	 * returns reservation code
	 * @return code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * returns the flight code
	 * @return code
	 */
	public String getFlightCode() {
		return flightCode;
	}
	/**
	 * returns the airline name
	 * @return airline name
	 */
	public String getAirline() {
		return airline;
	}
	/**
	 * returns customer name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * returns area of citizenship
	 * @return citizenship
	 */
	public String getCitizenship() {
		return citizenship;
	}
	/**
	 * returns the cost of reservation
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	/**
	 * if active or not active
	 * @return status of reservation
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * sets reservation name
	 * @param name name of customer
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * sets reservation citizenship
	 * @param citizenship the citizenship
	 */
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
	/**
	 * sets status
	 * @param active status of reservation
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * returns the reservation in a string format.
	 */
	@Override
	public String toString() {
		String statuslabel;
		if(this.isActive()) {
			statuslabel="Active";
		}else {
			statuslabel="Inactive";
		}
		/*
		return "Reservation Code: " + code + ", Flight Code: " + flightCode + ", Airline Name: " + airline + ", Customer Name: " + name
				+ ", Citizenship: " + citizenship + ", Cost: " + cost + ", Status: " + statuslabel;
		*/
		return code;
	}
}
