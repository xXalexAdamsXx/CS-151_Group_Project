package src.Menus;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class LogIn extends JFrame {
	private static final String TITLE = "User Log-in screen";
	private JTextField[] dataInput;

	public LogIn() {
		// Initialize the data input fields array
		dataInput = new JTextField[2];

		// Set up the frame
		setTitle(TITLE);
		setSize(500, 300); // Arbitrary size, can be adjusted
		setLayout(new BorderLayout(10, 20));

		// Add title label at the top
		JLabel titleLabel = new JLabel(TITLE, SwingConstants.CENTER);
		add(titleLabel, BorderLayout.NORTH);

		// Create and add input fields to the center
		String[] inputs = { "User Name", "Password" };
		createInputs(inputs, BorderLayout.CENTER);

		// Add submission button at the bottom
		JButton submitButton = new JButton("Submit");
		add(submitButton, BorderLayout.SOUTH);

		// Add action listener to the submit button
		submitButton.addActionListener(event -> {
			getUserInput();
			submitButton.setBackground(Color.BLUE);
		});

		// Set default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Make the frame visible
		setVisible(true);
		pack();
	}

	/**
	 * Creates input fields with labels and adds them to the frame.
	 *
	 * @param placeHolders Array of placeholder strings for the input fields.
	 * @param location     The layout location where the input fields should be
	 *                     added.
	 */
	private void createInputs(String[] placeHolders, String location) {
		JPanel inputsPanel = new JPanel(); // This will serve as the "package" that will be delivered to the greater
											// frame
		// Grad layout for control over element placement.
		inputsPanel.setLayout(new GridLayout(placeHolders.length, 2, 10, 10));

		for (int i = 0; i < placeHolders.length; i++) {
			// Create a label for the input field
			JLabel label = new JLabel(placeHolders[i] + ":");
			// Create input field (JPasswordField for password)
			JTextField inputField;
			if (placeHolders[i].equalsIgnoreCase("Password")) { // Deal with password case
				inputField = new JPasswordField(20);
			} else {
				inputField = new JTextField(20);
			}
			dataInput[i] = inputField;
			// Add elements in this specific order so that the label is on the left and the
			// field is on the right
			inputsPanel.add(label);
			inputsPanel.add(inputField);
		}

		add(inputsPanel, location);
	}

	/**
	 * Retrieves user input from the input fields.
	 *
	 * @return An array of strings containing the user inputs.
	 */
	private String[] getUserInput() {
		String[] inputs = new String[dataInput.length];

		for (int i = 0; i < inputs.length; i++) {
			inputs[i] = dataInput[i].getText();
		}

		// For debugging purposes
		System.out.println("User Name: " + inputs[0]);
		System.out.println("Password: " + inputs[1]);

		return inputs;
	}
}