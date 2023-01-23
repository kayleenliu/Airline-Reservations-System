package sait.frms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import sait.frms.exception.NoSeatAvailableException;
import sait.frms.exception.VoidInputException;
import sait.frms.manager.FlightManager;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the flights tab.
 * 
 */
public class FlightsTab extends TabBase 
{
	private FlightManager flightManager;
	private ReservationManager reservationManager;
	RandomAccessFile reservationsFile;
	private JList<Flight> flightsList;
	private DefaultListModel<Flight> flightsModel;
	
	/**
	 * Creates the components for flights tab.
	 * 
	 * @param flightManager Instance of FlightManager.
	 * @param reservationManager Instance of ReservationManager
	 * @throws IOException Thrown when unable to read from file
	 */
	public FlightsTab(FlightManager flightManager, ReservationManager reservationManager) throws IOException {
		this.flightManager = flightManager;
		this.reservationManager = reservationManager;
		this.reservationsFile = new RandomAccessFile("res/Reservations.bin", "rw");
		
		panel.setLayout(new BorderLayout());
		
		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel southPanel = createSouthPanel();
		panel.add(southPanel,BorderLayout.SOUTH);
		
		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);
	}
	
	/**
	 * Creates the north panel.
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() 
	{
		JPanel panel = new JPanel();
		
		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);
		
		return panel;
	}
	
	/**
	 * Creates the center panel.
	 * @return JPanel that goes in center.
	 */
	private JPanel createCenterPanel() 
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(10,10,10,10));
		flightsModel = new DefaultListModel<>();
		flightsList = new JList<>(flightsModel);
		
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(this.flightsList);
		
		
		panel.add(scrollPane,BorderLayout.CENTER);
		
		return panel;
	}
	
	/**
	 * Creates the South panel.
	 * @return JPanel that goes in South.
	 */
	private JPanel createSouthPanel() {
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BorderLayout());
		
		JPanel titlePanel = new JPanel();
		JPanel inputPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		JLabel title = new JLabel("Flight Finder", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		titlePanel.add(title);
		
		String[] toValues= flightManager.getUniqueToLocations();
		String[] fromValues= flightManager.getUniqueFromLocations();
		
		String[] dayValues = {"Any","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill=GridBagConstraints.HORIZONTAL;
		

		c.weightx=0.05;
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=1;
		JLabel fromLabel = new JLabel("From:");
		fromLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(fromLabel,c);
		
		c.weightx=0.95;
		c.gridx=1;
		c.gridy=0;
		c.gridwidth=400;
		JComboBox fromBox = new JComboBox(fromValues);
		fromBox.setPreferredSize(new Dimension(30,200));
		inputPanel.add(fromBox,c);
		
		c.weightx=0.05;
		c.gridx=0;
		c.gridy=1;
		c.gridwidth=1;
		JLabel toLabel = new JLabel("To:");
		toLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(toLabel,c);
		
		c.weightx=0.95;
		c.gridx=1;
		c.gridy=1;
		c.gridwidth=400;
		JComboBox toBox = new JComboBox(toValues);
		inputPanel.add(toBox,c);
		
		c.weightx=0.05;
		c.gridx=0;
		c.gridy=2;
		c.gridwidth=1;
		JLabel dayLabel = new JLabel("Day:");
		dayLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(dayLabel,c);
		
		c.weightx=0.95;
		c.gridx=1;
		c.gridy=2;
		c.gridwidth=400;
		JComboBox dayBox = new JComboBox(dayValues);
		inputPanel.add(dayBox,c);
		
		buttonPanel.setLayout(new GridLayout(1,1));
		JButton findFlightButton = new JButton("Find Flights");
		

		/**
		 * Nested class for the "find flights" button in the self panel.  
		 */
		class FlightsTabFindFlightsActionListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {

				String toSelection = (String) toBox.getSelectedItem();
				String fromSelection = (String) fromBox.getSelectedItem();
				String daySelection = (String) dayBox.getSelectedItem();

				ArrayList<Flight> matchingFlightList = flightManager.findFlights(fromSelection, toSelection, daySelection);
				
				flightsModel.clear();
				
				for(Flight flight : matchingFlightList) {
					flightsModel.addElement(flight);
				}
			}
			
		}
		
		findFlightButton.addActionListener(new FlightsTabFindFlightsActionListener());
		buttonPanel.add(findFlightButton);
		
		
		masterPanel.add(titlePanel, BorderLayout.NORTH);
		masterPanel.add(inputPanel, BorderLayout.CENTER);
		masterPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		inputPanel.setPreferredSize(new Dimension(600, 120));
		
		
		
		return masterPanel;
	}
	
	
	/**
	 * Creates the East panel.
	 * @return JPanel that goes in East.
	 */
	private JPanel createEastPanel() {
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BorderLayout());
		
		JPanel titlePanel = new JPanel();
		JPanel inputPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		titlePanel.setLayout(new GridLayout(2,1));
		JLabel title = new JLabel("Reserve", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		titlePanel.add(title);
		
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		JLabel flightLabel = new JLabel("Flight:");
		flightLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(flightLabel,c);
		
		c.gridx=1;
		c.gridy=0;
		JTextField flightTextField = new JTextField(10);
		flightTextField.setEditable(false);
		inputPanel.add(flightTextField,c);
		
		c.gridx=0;
		c.gridy=1;
		JLabel airlineLabel = new JLabel("Airline:");
		airlineLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(airlineLabel,c);
		
		c.gridx=1;
		c.gridy=1;
		JTextField airlineTextField = new JTextField(10);
		airlineTextField.setEditable(false);
		inputPanel.add(airlineTextField,c);
		
		c.gridx=0;
		c.gridy=2;
		JLabel dayLabel = new JLabel("Day:");
		dayLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(dayLabel,c);
		
		c.gridx=1;
		c.gridy=2;
		JTextField dayTextField = new JTextField(10);
		dayTextField.setEditable(false);
		inputPanel.add(dayTextField,c);
		
		c.gridx=0;
		c.gridy=3;
		JLabel timeLabel = new JLabel("Time:");
		timeLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(timeLabel,c);

		c.gridx=1;
		c.gridy=3;
		JTextField timeTextField = new JTextField(10);
		timeTextField.setEditable(false);
		inputPanel.add(timeTextField,c);
		
		c.gridx=0;
		c.gridy=4;
		JLabel costLabel = new JLabel("Cost:");
		costLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(costLabel,c);
		
		c.gridx=1;
		c.gridy=4;
		JTextField costTextField = new JTextField(10);
		costTextField.setEditable(false);
		inputPanel.add(costTextField,c);
		
		c.gridx=0;
		c.gridy=5;
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(nameLabel,c);
		
		c.gridx=1;
		c.gridy=5;
		JTextField nameTextField = new JTextField(10);
		inputPanel.add(nameTextField,c);
		
		c.gridx=0;
		c.gridy=6;
		JLabel citizenshipLabel = new JLabel("Citizenship:");
		citizenshipLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(citizenshipLabel,c);
		
		c.gridx=1;
		c.gridy=6;
		JTextField citizenshipTextField = new JTextField(10);
		inputPanel.add(citizenshipTextField,c);
		
		/**
		 * A nested class used to implement the reserve button in the east panel.
		 */
		class FlightsTabReserveButtonActionListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e){
				Flight selected = flightsList.getSelectedValue();
				try {
	                if(nameTextField.getText().equals("") || citizenshipTextField.getText().equals("")) {
	                	throw new VoidInputException();
	                }
	                if(!reservationManager.seatAvailable(selected)) {
	                	throw new NoSeatAvailableException();
	                }
					Reservation reserve = reservationManager.makeReservation(selected, nameTextField.getText(), citizenshipTextField.getText());
					JOptionPane.showMessageDialog(null, "Reservation Created. Your Code is: "+reserve.getCode());
				}catch(IOException ex){
					System.out.println("Critical Error: unable to write to file!");
				}catch(VoidInputException ex) {
                	JOptionPane.showMessageDialog(null, "Empty inputs: Please fill out Name AND Citizenship");
                }catch(NoSeatAvailableException ex) {
                	JOptionPane.showMessageDialog(null, "Flight Full: Unable to make reservation");
                }catch(NullPointerException ex) {
                	JOptionPane.showMessageDialog(null, "No Flight Selected: Please select a flight!");
                }
				
			}
			
			
		}
		
		buttonPanel.setLayout(new GridLayout(1,1));
		JButton reserveButton = new JButton("Reserve");
		reserveButton.addActionListener(new FlightsTabReserveButtonActionListener());
		buttonPanel.add(reserveButton);
		
		
		masterPanel.add(titlePanel, BorderLayout.NORTH);
		masterPanel.add(inputPanel, BorderLayout.CENTER);
		masterPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		inputPanel.setPreferredSize(new Dimension(200, 200));
		
		/**
		 * A nested class used for the Jlist in the center panel. The nested class is located in the east panel so
		 * it can access the east panel text fields. 
		 */
		class FlightsTabListActionListener implements ListSelectionListener{
			/**
			 * Called when user selects an item in the JList.
			 */
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				try {
					Flight selected = flightsList.getSelectedValue();
					flightTextField.setText(selected.getCode());
					airlineTextField.setText(selected.getAirlineName());
					dayTextField.setText(selected.getWeekday());
					timeTextField.setText(selected.getTime());
					costTextField.setText("$"+selected.getCostPerSeat());
				}catch(NullPointerException ex) {
					//exists to stop crashing of program when it new list is selected.
				}
			}
		}
		
		flightsList.addListSelectionListener(new FlightsTabListActionListener());
		
		return masterPanel;
	}
	
	

	
	
}