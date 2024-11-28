//package main;

import Front_End.*;

/**
 * The Main class serves as the entry point for the cashier application.
 * It initializes the login screen and transitions to the main application
 * interface upon successful login.
 */
public class Main {
	/**
	 * The main method launches the application.
	 *
	 * @param args Command-line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		// Create the login screen with a callback to launch the main UI
		new LogIn(username -> {
			// Launch MainUI when login is successful
			String cashierName = "User"; // Replace with the logged-in user's name if available
			new MainUI(cashierName);
		});
	}
}
