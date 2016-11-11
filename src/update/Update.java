package update;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Update extends JFrame implements Runnable {

	/**
	 * @author vazzup
	 * This is the Update JFrame
	 * It is a runnable and is called by a thread in HomePage.java
	 * It uses Two JComboBoxes to let user select which fields to update
	 * It uses two JTextFields to take value to be updated to and value to be queried
	 */
	JComboBox dropDownMenu, dropDownMenuTwo;
	JLabel header, headerTwo, equalsTo, to;
	JTextField value, toValue;
	JButton updateButton;
	
	@Override
	public void run() {
		// Called when Thread starts
		this.setVisible(true);
	}
	
	public Update() {
		/*
		 * Default Constructor
		 * Size set randomly
		 */
		this.setSize(1000, 1000);
		this.setLayout(null);
		this.prepareComponents();
	}
	
	public void prepareComponents() {
		
		/*
		 * Function to prepare and add components to GUI
		 * Seperated from run(), and constructor so as to keep it clean
		 */
		
		//Initialize components
		String dropDownContent[] = {"Roll Number", "Name", "Department", "Year"};
		this.dropDownMenu=new JComboBox(dropDownContent);
		this.header=new JLabel("Update ");
		this.dropDownMenuTwo=new JComboBox(dropDownContent);
		this.headerTwo=new JLabel(" where ");
		this.equalsTo=new JLabel(" equals ");
		this.value=new JTextField();
		this.to= new JLabel(" to ");
		this.toValue=new JTextField();
		this.updateButton=new JButton("UPDATE");
		
		//Place components
		this.header.setBounds(50, 50, 100, 20);
		this.dropDownMenuTwo.setBounds(170, 50, 100, 20);
		this.headerTwo.setBounds(290, 50, 100, 20);
		this.dropDownMenu.setBounds(50, 90, 100, 20);
		this.equalsTo.setBounds(170, 90, 100, 20);
		this.value.setBounds(290, 90, 100, 20);
		this.to.setBounds(410, 90, 100, 20);
		this.toValue.setBounds(530, 90, 100, 20);
		this.updateButton.setBounds(50, 130, 100, 20);
		
		//Add components to JFrame
		this.add(this.header);
		this.add(this.dropDownMenuTwo);
		this.add(this.headerTwo);
		this.add(this.dropDownMenu);
		this.add(this.equalsTo);
		this.add(this.value);
		this.add(this.to);
		this.add(this.toValue);
		this.add(this.updateButton);
		
		//Set button click listener to react to button click
		this.updateButton.setActionCommand("UPDATE");
		this.updateButton.addActionListener(new ButtonClickListener(this));
	}
	
	private class ButtonClickListener implements ActionListener {
		
		/**
		 * @params Update update
		 * Class that listens for button clicks and takes appropriate action
		 */
		
		Update update;
		public ButtonClickListener(Update update) {
			/*
			 * Constructor 
			 */
			this.update=update;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			/*
			 * Function that responds to button click depending on what button is clicked
			 */
			try {
				String command = e.getActionCommand();
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/studentDB?useSSL=false","root","adbms1234");
				Statement statement = connection.createStatement();
				int resultUpdate;
				switch(command) {
					case "UPDATE": {
						
						String sql="update student set ";
						String choice=(String)this.update.dropDownMenu.getSelectedItem();
						String choiceTwo=(String)this.update.dropDownMenuTwo.getSelectedItem();
						String value=this.update.value.getText().trim();
						String valueTwo=this.update.toValue.getText().trim();
						
						
						switch(choiceTwo) {
							case "Roll Number": {
								sql+="id";
								break;
							} case "Name": {
								sql+="name";
								break;
							} case "Department": {
								sql+="department";
								break;
							} case "Year": {
								sql+="year";
								break;
							} default: {
								System.out.println("Wrong Choice");
							}
						}
						sql+="=";
						if(choiceTwo.equalsIgnoreCase("Name") || choiceTwo.equalsIgnoreCase("Department")) sql+="\"";
						sql+=valueTwo;
						if(choiceTwo.equalsIgnoreCase("Name") || choiceTwo.equalsIgnoreCase("Department")) sql+="\"";
						
						sql+=" where ";
						
						switch(choice) {
							case "Roll Number": {
								sql+="id";
								break;
							} case "Name": {
								sql+="name";
								break;
							} case "Department": {
								sql+="department";
								break;
							} case "Year": {
								sql+="year";
								break;
							} default: {
								System.out.println("Wrong Choice");
							}
						}
						sql+="=";
						if(choice.equalsIgnoreCase("Name") || choice.equalsIgnoreCase("Department")) sql+="\"";
						sql+=value;
						if(choice.equalsIgnoreCase("Name") || choice.equalsIgnoreCase("Department")) sql+="\"";
						sql+=";";
						try {
							resultUpdate=statement.executeUpdate(sql);
						} catch (Exception exception) {
							System.out.println("Error");
							System.out.println(sql);
						}
						break;
					} default: {
						System.out.println("Incorrect Command)");
						break;
					}
				}
				this.update.dispose();
			} catch(Exception exception) {
				System.out.println("Incorrect Query");
			}
		}
	}

}
