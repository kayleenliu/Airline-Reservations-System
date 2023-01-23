package sait.frms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import sait.frms.manager.ReservationManager;
import sait.frms.exception.InvalidCodeException;
import sait.frms.exception.VoidInputException;
import sait.frms.manager.FlightManager;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the Reservations tab.
 * 
 */
public class ReservationsTab extends TabBase 
{

	private FlightManager flightManager;
	private ReservationManager reservationManager;
	private JList<Reservation> reservationsList;
	private DefaultListModel<Reservation> reservationsModel;
	
	/**
	 * Creates the components for Reservations tab.
	 * 
	 * @param reservationManager Instance of ReservationManager
	 */
	public ReservationsTab(ReservationManager reservationManager) {
		this.reservationManager = reservationManager;
		
		panel.setLayout(new BorderLayout());
		
		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel southPanel = createSouthPanel();
		panel.add(southPanel,BorderLayout.SOUTH);
		
		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel,BorderLayout.EAST);
	}
	
	/**
	 * Creates the north panel.
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() 
	{
		JPanel panel = new JPanel();
		
		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
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
		reservationsModel = new DefaultListModel<>();
		reservationsList = new JList<>(reservationsModel);
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(this.reservationsList);
		panel.add(scrollPane,BorderLayout.CENTER);
		return panel;
	}
	
	/**
	 * Creates the south panel.
	 * @return JPanel that goes in the south.
	 */
	private JPanel createSouthPanel() {
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BorderLayout());
		
		JPanel titlePanel = new JPanel();
		JPanel inputPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		JLabel title = new JLabel("Search", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		titlePanel.add(title);
		
		
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill=GridBagConstraints.HORIZONTAL;
		

		c.weightx=0.05;
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=1;
		JLabel codeLabel = new JLabel("Code:");
		codeLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(codeLabel,c);
		
		c.weightx=0.05;
		c.gridx=0;
		c.gridy=1;
		c.gridwidth=1;
		JLabel airlineLabel = new JLabel("Airline:");
		airlineLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(airlineLabel,c);
		
		c.weightx=0.05;
		c.gridx=0;
		c.gridy=2;
		c.gridwidth=1;
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(nameLabel,c);

		c.weightx=0.95;
		c.gridx=1;
		c.gridy=0;
		c.gridwidth=400;
		JTextField codeTextField = new JTextField();
		inputPanel.add(codeTextField,c);
		
		c.weightx=0.95;
		c.gridx=1;
		c.gridy=1;
		c.gridwidth=400;
		JTextField airlineTextField = new JTextField();
		inputPanel.add(airlineTextField,c);
		
		c.weightx=0.95;
		c.gridx=1;
		c.gridy=2;
		c.gridwidth=400;
		JTextField nameTextField = new JTextField();
		inputPanel.add(nameTextField,c);
		
		buttonPanel.setLayout(new GridLayout(1,1));
		JButton findReservationButton = new JButton("Find Reservations");
		buttonPanel.add(findReservationButton);
		
		masterPanel.add(titlePanel, BorderLayout.NORTH);
		masterPanel.add(inputPanel, BorderLayout.CENTER);
		masterPanel.add(buttonPanel, BorderLayout.SOUTH);
		/**
		 * Used to add fucntionality to the find reservations button in the south panel
		 */
		class ReservationTabFindReservationsActionListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String code = codeTextField.getText();
				String airline = airlineTextField.getText();
				String name = nameTextField.getText();
				
				try {
					if(!code.equals("")) {
					if((code.toUpperCase().startsWith("I") || code.toUpperCase().startsWith("D")) && code.length()==5 && Integer.parseInt(code.substring(1)) >= 1000) {
						ArrayList<Reservation> matchingReservationList = reservationManager.findReservations(code,airline,name);
						
						reservationsModel.clear();
						for(Reservation reservation : matchingReservationList) {
							reservationsModel.addElement(reservation);
						}
						
					}
					else{
						throw new InvalidCodeException();
					    }
					}
					else if(code.equals("")){
						ArrayList<Reservation> matchingReservationList = reservationManager.findReservations(code,airline,name);
						
						reservationsModel.clear();
						for(Reservation reservation : matchingReservationList) {
							reservationsModel.addElement(reservation);
						}
					}
				}catch(InvalidCodeException ex) {
					JOptionPane.showMessageDialog(null, "Invalid Code, please enter a valid code");
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid Code, please enter a valid code");
				}catch(Exception ex) {
					System.out.print(ex.getMessage());
				}
			}
			
		}
		findReservationButton.addActionListener(new ReservationTabFindReservationsActionListener());
		
		return masterPanel;
	}
	/**
	 * Creates the east panel.
	 * @return JPanel that goes in the east.
	 */
	private JPanel createEastPanel() {
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BorderLayout());
		
		JPanel titlePanel = new JPanel();
		JPanel inputPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		
		//Title Panel
		titlePanel.setLayout(new GridLayout(2,1));
		JLabel title = new JLabel("Reserve", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		titlePanel.add(title);
		
		//Input Panel
		
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		JLabel codeLabel = new JLabel("Code:");
		codeLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(codeLabel,c);
		
		c.gridx=1;
		c.gridy=0;
		JTextField codeTextField = new JTextField(10);
		codeTextField.setEditable(false);
		inputPanel.add(codeTextField,c);
		
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=1;
		JLabel flightLabel = new JLabel("Flight:");
		flightLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(flightLabel,c);
		
		c.gridx=1;
		c.gridy=1;
		JTextField flightTextField = new JTextField(10);
		flightTextField.setEditable(false);
		inputPanel.add(flightTextField,c);
		
		c.gridx=0;
		c.gridy=2;
		JLabel airlineLabel = new JLabel("Airline:");
		airlineLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(airlineLabel,c);
		
		c.gridx=1;
		c.gridy=2;
		JTextField airlineTextField = new JTextField(10);
		airlineTextField.setEditable(false);
		inputPanel.add(airlineTextField,c);
		
		c.gridx=0;
		c.gridy=3;
		JLabel costLabel = new JLabel("Cost:");
		costLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(costLabel,c);
		
		c.gridx=1;
		c.gridy=3;
		JTextField costTextField = new JTextField(10);
		costTextField.setEditable(false);
		inputPanel.add(costTextField,c);
		
		c.gridx=0;
		c.gridy=4;
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(nameLabel,c);

		c.gridx=1;
		c.gridy=4;
		JTextField nameTextField = new JTextField(10);
		inputPanel.add(nameTextField,c);
		
		c.gridx=0;
		c.gridy=5;
		JLabel citizenshipLabel = new JLabel("Citizenship:");
		citizenshipLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(citizenshipLabel,c);
		
		c.gridx=1;
		c.gridy=5;
		JTextField citizenshipTextField = new JTextField(10);
		inputPanel.add(citizenshipTextField,c);
		
		c.gridx=0;
		c.gridy=6;
		JLabel statusLabel = new JLabel("Status:");
		statusLabel.setHorizontalAlignment(JLabel.RIGHT);
		inputPanel.add(statusLabel,c);

		String[] statusOptions = {"Active","Inactive"};
		c.gridx=1;
		c.gridy=6;
		JComboBox statusComboBox = new JComboBox(statusOptions);
		inputPanel.add(statusComboBox,c);
		
		//Button Panel
		
		buttonPanel.setLayout(new GridLayout(1,1));
		JButton reserveButton = new JButton("Update");
		buttonPanel.add(reserveButton);
		
		
		masterPanel.add(titlePanel, BorderLayout.NORTH);
		masterPanel.add(inputPanel, BorderLayout.CENTER);
		masterPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		inputPanel.setPreferredSize(new Dimension(200, 200));
	
		/**
		 *An inner class that handles updating the east panel whenever an item is selected in the center panel. 
		 */
		class ReservationTabListActionListener implements ListSelectionListener{
			/**
			 * Called when user selects an item in the JList.
			 */
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				try {
					Reservation selected = reservationsList.getSelectedValue();
				//Add other Values
					codeTextField.setText(selected.getCode());
					flightTextField.setText(selected.getFlightCode());
					airlineTextField.setText(selected.getAirline());
					costTextField.setText("$"+selected.getCost());
					nameTextField.setText(selected.getName());
					citizenshipTextField.setText(selected.getCitizenship());
					if(selected.isActive()) {
						statusComboBox.setSelectedItem("Active");
					}else {
						statusComboBox.setSelectedItem("Inactive");
					}
				}catch(NullPointerException ex) {
					//exists to stop crashing of program when it new list is selected.
				}
			}
		}
		/**
		 *A button that updates the reservation list with the information in the east panel. 
		 */		
		class UpdateButtonActionListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	boolean active;
                    if(statusComboBox.getSelectedItem() == "Active") {
                        active=true;
                    }else {
                        active=false;                 
                    }
                    if(nameTextField.getText().equals("") || citizenshipTextField.getText().equals("")) {
                    	throw new VoidInputException();
                    }
                    String value=costTextField.getText().substring(1);
                	Reservation r1 = new Reservation(codeTextField.getText(),flightTextField.getText(),airlineTextField.getText(),nameTextField.getText(),citizenshipTextField.getText(),Double.parseDouble(value),active);
                	reservationManager.updateReservation(r1);
                	reservationsModel.setElementAt(r1,reservationsList.getSelectedIndex());
                	if(!r1.isActive()) {
                		reservationsModel.remove(reservationsList.getSelectedIndex());
                	}
                	JOptionPane.showMessageDialog(null, "Reservation Updated!");
                }catch(VoidInputException ex) {
                	JOptionPane.showMessageDialog(null, "Empty inputs: Please fill out Name AND Citizenship");
                }
                catch(IOException ex) {
                	System.out.println(ex.getMessage());
                	System.out.print("Reservation File Not found");	
                }catch(Exception ex) {
                	JOptionPane.showMessageDialog(null, "No Reservation Selected: Please select a Reservation!");
                }
            }

 

        }
        reserveButton.addActionListener(new UpdateButtonActionListener());
		
		reservationsList.addListSelectionListener(new ReservationTabListActionListener());
		
		return masterPanel;
	}
}