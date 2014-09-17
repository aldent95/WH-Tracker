package mapper;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class GUI {
	// GUI Fields
	private JFrame frame;
	private JTextArea textArea;
	private JTextField textField;
	private String tempText;
	// TODO Rename
	private boolean errored = false; // Tracks if we are currently in an error
										// state
	private String header = "Date/Time\t\tJ Number\tSystem RealID\t\tConstellation RealID\tRegion RealID\n";
	Mapper m;
	private JButton btnDeleteLast;// FIXME Are these 3 fields really needed?
	private JButton btnFind;
	private JButton btnAdd_1;

	/**
	 * Protected class that manages the GUI for the program
	 * 
	 * @param m
	 *            - The mapper class for the program
	 */
	protected GUI(Mapper m) {
		this.m = m;
		setupFrame();

	}

	private void setupFrame() {
		// Setup the JFrame with initial size and close operation
		frame = new JFrame();
		frame.setSize(900, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Setup the JPanel for storing the buttons
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		// Setup JLabel
		JLabel lblEnterJNumber = new JLabel("Enter J Number ->");
		panel.add(lblEnterJNumber);

		// Setup the text field where users will enter
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		// TODO Move the save and find buttons to a menu
		// Setup the add button
		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(Color.GREEN);
		btnAdd.addActionListener(new ActionListener() {
			// Action listener for Add will add the current system in the text
			// field to the list
			public void actionPerformed(ActionEvent arg0) {
				try {
					m.add(textField.getText());
				} catch (InvalidSystemException e) {
					error(e);
				}
			}
		});
		panel.add(btnAdd);
		// Setup the save button
		btnAdd_1 = new JButton("Save");
		btnAdd_1.setForeground(Color.BLACK);
		btnAdd_1.setBackground(Color.BLUE);
		btnAdd_1.addActionListener(new ActionListener() {
			// Action listener for Save will pop up a JOptionPane and ask for
			// the save file. It will then go the the mapper to save the current
			// list.
			public void actionPerformed(ActionEvent arg0) {
				String filename = JOptionPane
						.showInputDialog(
								frame,
								"Enter name for save file\n WARNING: Will overwrite any file with same name",
								null);
				m.save(filename, textArea.getText());
			}
		});
		panel.add(btnAdd_1);
		// Setup the delete button
		btnDeleteLast = new JButton("Delete Last");
		btnDeleteLast.setBackground(Color.RED);
		btnDeleteLast.addActionListener(new ActionListener() {
			// Action Listener for delete will call the delete command to delete
			// the last item added to the list
			public void actionPerformed(ActionEvent arg0) {
				delete();
			}
		});
		panel.add(btnDeleteLast);
		// Setup the find button.
		btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			// Action Listener for find takes the current text in the text field
			// and displays information about that system if said system exists
			public void actionPerformed(ActionEvent arg0) {
				try {
					find(m.find(textField.getText()));
				} catch (InvalidSystemException e) {
					error(e);
				}
			}
		});
		panel.add(btnFind);
		// Finally setup the text area for all the information to be displayed
		// in.
		textArea = new JTextArea();
		textArea.setText(header);
		frame.getContentPane().add(textArea, BorderLayout.CENTER);

	}

	/**
	 * Displays the system info when the find button is pressed
	 * 
	 * @param values
	 */
	protected void find(String[] values) {
		if (!errored) {
			tempText = textArea.getText();
			errored = true;
		}
		// TODO Make a method for this
		String line = values[0] + "\t" + values[1] + "\t" + values[2] + "\t\t"
				+ values[3];
		textArea.setText(line + "\n");

	}

	/**
	 * Deletes the last entry on the list
	 */
	protected void delete() {
		if (errored) {
			errored = false;
			textArea.setText(tempText);
		}
		// To remove the last entry we get the current lines, then redisplay the
		// new lines minus the first 2 lines. (Line 1 is the header, Line 2 is
		// the one we want to remove)
		String[] lines = textArea.getText().split("\\n");
		String[] newLines = new String[lines.length - 2];
		for (int i = 2; i < lines.length; i++) {
			newLines[i - 2] = lines[i];
		}
		textArea.setText(header);
		for (int i = 0; i < newLines.length; i++) {
			textArea.append(newLines[i] + "\n");
		}
		m.save("Save", textArea.getText());

	}
	/**
	 * Appens the given Strings to the list
	 * @param values
	 */
	protected void append(String[] values) {
		if (errored) {
			errored = false;
			textArea.setText(tempText);
		}
		String[] lines = textArea.getText().split("\\n");
		lines[0] = values[0] + "\t" + values[1] + "\t" + values[2] + "\t\t"
				+ values[3] + "\t\t" + values[4];
		textArea.setText(header);
		for (int i = 0; i < lines.length; i++) {
			textArea.append(lines[i] + "\n");
		}

	}

	protected void error(InvalidSystemException e) {
		tempText = textArea.getText();
		textArea.setText(e + "");
		errored = true;

	}

	protected void append(String contents) {
		textArea.append(contents);

	}

	// TODO Can this be removed?
	protected void show() {
		frame.setVisible(true);

	}

	protected void clear() {
		textArea.setText(header);

	}
}
