package sait.frms.application;

import sait.frms.gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.IOException;

import sait.frms.gui.*;
import sait.frms.manager.*;
/**
 * Application driver.
 * @author Scott Normore, Christian Lay, Gao Liu, Kin Shing Chong
 */
public class AppDriver {
	/**
	 * The Driver Application. Runs the manager.
	 * @param args not used in our program, but every java program main method has em.
	 * @throws IOException Will throw an IOException if it cannot read from flights and airports. This is intended because the application is useless without it.
	 */
	public static void main(String[] args) throws IOException{
		FlightManager FManager= new FlightManager();
		MainWindow mainWindow = new MainWindow();
		mainWindow.display();
	}

}
