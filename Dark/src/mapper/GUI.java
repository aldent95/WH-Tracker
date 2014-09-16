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
		private boolean errored = false;
		private String header = "Date/Time\t\tJ Number\tSystem RealID\t\tConstellation RealID\tRegion RealID\n";
		Mapper m;
		private JButton btnDeleteLast;
		private JButton btnFind;
		private JButton btnAdd_1;
		public GUI(Mapper m){
			this.m = m;
			setupFrame();
			
		}
		private void setupFrame() {
			frame = new JFrame();
			frame.setSize(900, 900);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JPanel panel = new JPanel();
			frame.getContentPane().add(panel, BorderLayout.NORTH);
			
			JLabel lblEnterJNumber = new JLabel("Enter J Number ->");
			panel.add(lblEnterJNumber);
			
			textField = new JTextField();
			panel.add(textField);
			textField.setColumns(10);
			
			JButton btnAdd = new JButton("Add");
			btnAdd.setBackground(Color.GREEN);
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						m.add(textField.getText());
					} catch (InvalidSystemException e) {
						error(e);
					}
				}
			});
			panel.add(btnAdd);
			 btnAdd_1 = new JButton("Save");
			 btnAdd_1.setForeground(Color.BLACK);
			 btnAdd_1.setBackground(Color.BLUE);
			btnAdd_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String filename = JOptionPane.showInputDialog(frame,"Enter name for save file\n WARNING: Will overwrite any file with same name",null);
					m.save(filename,textArea.getText());
				}
			});
			panel.add(btnAdd_1);
			
			btnDeleteLast = new JButton("Delete Last");
			btnDeleteLast.setBackground(Color.RED);
			btnDeleteLast.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					delete();
				}
			});
			panel.add(btnDeleteLast);
			
			btnFind = new JButton("Find");
			btnFind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try{
					find(m.find(textField.getText()));
					}catch (InvalidSystemException e) {
						error(e);
					}
				}
			});
			panel.add(btnFind);
			
			textArea = new JTextArea();
			textArea.setText(header);
			frame.getContentPane().add(textArea, BorderLayout.CENTER);
			
		}
		protected void find(String[] values) {
			if(!errored){
			tempText = textArea.getText();
			errored = true;
			}
			String line = values[0] + "\t" + values[1] + "\t" + values[2] + "\t\t" + values[3];
			textArea.setText(line + "\n");
			
		}
		protected void delete() {
			if(errored){ 
				errored = false;
				textArea.setText(tempText);
			}
			String[] lines = textArea.getText().split("\\n");
			String[] newLines = new String[lines.length-2];
			for(int i = 2; i < lines.length; i++){
				newLines[i-2] = lines[i];
			}
			textArea.setText(header);
			for(int i = 0; i < newLines.length; i++){
				textArea.append(newLines[i] + "\n");
			}
			m.save("Save", textArea.getText());
			
		}
		public void append(String[] values) {
			if(errored){ 
				errored = false;
				textArea.setText(tempText);
			}
			String[] lines = textArea.getText().split("\\n");
			lines[0] = values[0] + "\t" + values[1] + "\t" + values[2] + "\t\t" + values[3] + "\t\t" + values[4];
			textArea.setText(header);
			for(int i = 0; i < lines.length; i++){
				textArea.append(lines[i] + "\n");
			}
			
			
		}
		public void error(InvalidSystemException e) {
			tempText = textArea.getText();
			textArea.setText(e + "");
			errored = true;
			
		}
		public void append(String contents) {
			textArea.append(contents);
			
		}
		public void show() {
			frame.setVisible(true);
			
		}
		public void clear() {
			textArea.setText(header);
			
		}
}
