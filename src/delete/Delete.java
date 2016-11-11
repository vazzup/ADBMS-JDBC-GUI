package delete;

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

public class Delete extends JFrame implements Runnable {

	/**
	 * @author vazzup
	 * This is the Select JFrame
	 * It is a runnable and is called by a thread in HomePage.java
	 * It uses a JComboBoxes to let user select which fields to delete using
	 * It uses a JTextField to let user select which value of selected field to delete
	 */
	
	JComboBox dropDownMenu;
	JLabel header, equalsTo;
	JTextField value;
	JButton deleteButton;
	@Override
	public void run() {
		// Called when Thread calls start
		this.setVisible(true);
	}
	
	public Delete() {
		
		/*
		 * Default Public Constructor
		 * Size set randomly
		 */
		
		this.setSize(700, 500);
		this.setLayout(null);
		this.prepareComponents();
		
	}
	
	public void prepareComponents() {
		
		/*
		 * Function to prepare and add components to GUI
		 * Seperated from run(), and constructor so as to keep it clean
		 */
		
		//Initialize Components
		String dropDownContent[] = {"Roll Number", "Name", "Department", "Year"};
		this.dropDownMenu=new JComboBox(dropDownContent);
		this.header=new JLabel("Delete * where");
		this.equalsTo=new JLabel(" equals ");
		this.value=new JTextField();
		this.deleteButton=new JButton("DELETE");
		
		//Place Components in JFrame
		this.header.setBounds(50, 50, 400, 20);
		this.dropDownMenu.setBounds(50, 90, 100, 20);
		this.equalsTo.setBounds(170, 90, 100, 20);
		this.value.setBounds(290, 90, 100, 20);
		this.deleteButton.setBounds(50, 130, 100, 20);
		
		//Add Components to JFrame
		this.add(this.header);
		this.add(this.dropDownMenu);
		this.add(this.equalsTo);
		this.add(this.value);
		this.add(this.deleteButton);
		
		//Set Button Click Listener
		this.deleteButton.setActionCommand("DELETE");
		this.deleteButton.addActionListener(new ButtonClickListener(this));
		
	}
	
	private class ButtonClickListener implements ActionListener {
		
		
		/**
		 * @params Delete delete
		 * Class that listens for button clicks and takes appropriate action
		 */
		
		Delete delete;
		public ButtonClickListener(Delete delete) {
			this.delete=delete;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String command = e.getActionCommand();
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/studentDB?useSSL=false","root","adbms1234");
				Statement statement = connection.createStatement();
				int resultUpdate;
				switch(command) {
					case "DELETE": {
						String sql="delete from student where ";
						String choice=(String)this.delete.dropDownMenu.getSelectedItem();
						String value=this.delete.value.getText().trim();
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
				this.delete.dispose();
			} catch(Exception exception) {
				System.out.println("Incorrect Query");
			}
		}
	}
}
