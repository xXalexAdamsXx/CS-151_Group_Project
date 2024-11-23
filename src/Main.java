package src;

import javax.swing.*;
import java.awt.*;

public class Main {

	public static void main(String args[]) {
		JFrame frame = new JFrame(); // Instantiate the frame

		frame.setLayout(new GridBagLayout()); // GridBagLayout is GOATed. All my homies hate BorderBox we on GridBag
												// gang
		frame.setVisible(true);

		final int FIELD_WIDTH = 33; // Define the width
		JTextField textField = new JTextField(FIELD_WIDTH); // Instantiate the text field
		textField.setText("I decided that I want to use JFrame, because that's what I have the most experience with.");

		frame.add(textField);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}
}
