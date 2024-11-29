package main.Menus;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.function.Consumer;

/**
 * The LogIn class displays a login screen and executes a callback upon successful login.
 */
public class LogIn extends JFrame {
	private static final String TITLE = "User Log-in Screen";
	private JTextField usernameField;
	private JPasswordField passwordField;
	private Consumer<String> onLoginSuccess; // Callback to execute upon successful login

	/**
	 * Constructs the LogIn screen with a callback for successful login.
	 *
	 * @param onLoginSuccess A Consumer callback to execute when login is successful.
	 *                       The callback receives the logged-in username.
	 */
	public LogIn(Consumer<String> onLoginSuccess) {
		this.onLoginSuccess = onLoginSuccess;

		// Set up the frame
		setTitle(TITLE);
		setSize(500, 300);
		setLayout(new BorderLayout(10, 20));

		// Add title label at the top
		JLabel titleLabel = new JLabel(TITLE, SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		add(titleLabel, BorderLayout.NORTH);

		// Create and add input fields to the center
		createInputs();

		// Add submission button at the bottom
		JButton submitButton = new JButton("Login");
		add(submitButton, BorderLayout.SOUTH);

		// Add action listener to the submit button
		submitButton.addActionListener(event -> {
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());

			if (username.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Both fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Validate credentials (placeholder logic)
			if (validateCredentials(username, password)) {
				dispose(); // Close the login screen
				onLoginSuccess.accept(username); // Execute the callback with the username
			} else {
				JOptionPane.showMessageDialog(this, "Invalid login credentials.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		// Set default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Creates input fields for username and password.
	 */
	private void createInputs() {
		JPanel inputsPanel = new JPanel(new GridLayout(2, 2, 10, 10));

		// Username field
		JLabel usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(20);
		inputsPanel.add(usernameLabel);
		inputsPanel.add(usernameField);

		// Password field
		JLabel passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField(20);
		inputsPanel.add(passwordLabel);
		inputsPanel.add(passwordField);

		add(inputsPanel, BorderLayout.CENTER);
	}

	/**
	 * Validates the username and password.
	 * (This is placeholder logic and should be replaced with real validation.)
	 *
	 * @param username The entered username.
	 * @param password The entered password.
	 * @return true if credentials are valid; false otherwise.
	 */
	private boolean validateCredentials(String username, String password) {
		return "admin".equals(username) && "password".equals(password); // Replace with real validation logic
	}
}
