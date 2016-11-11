package select;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Select extends JFrame implements Runnable {

	/**
	 * @author vazzup
	 * This is the Select JFrame
	 * It is a runnable and is called by a thread in HomePage.java
	 * It uses Four JCheckBoxes to let user select which fields to show
	 * It uses a JLabel to print result of Query
	 */
	
	JCheckBox rollNumberBox, nameBox, departmentBox, yearBox;
	JButton search;
	JLabel displayLabel;
	
	@Override
	public void run() {
		//Called when Thread starts
		this.setVisible(true);
	}
	
	public Select () {
		
		/* 
		 * Default Public Constructor
		 * Size set randomly
		 */
		this.setSize(500, 500);
		this.setLayout(null);
		this.prepareComponents();
	}
	
	public void prepareComponents() {
		
		/*
		 * Function to prepare and add components to GUI
		 * Seperated from run(), and constructor so as to keep it clean
		 */
		
		//Initialize components
		this.rollNumberBox=new JCheckBox("Roll Number");
		this.nameBox=new JCheckBox("Name");
		this.departmentBox=new JCheckBox("Department");
		this.yearBox=new JCheckBox("Year");
		this.search=new JButton("Search");
		this.displayLabel=new JLabel();
		
		//Place Components in JFrame
		this.rollNumberBox.setBounds(50, 50, 200, 20);
		this.nameBox.setBounds(50, 90, 200, 20);
		this.departmentBox.setBounds(50, 130, 200, 20);
		this.yearBox.setBounds(50, 170, 200, 20);
		this.search.setBounds(50, 220, 100, 20);
		this.displayLabel.setBounds(50, 250, 400, 200);
		
		//Add Components to JFrame
		this.add(rollNumberBox);
		this.add(nameBox);
		this.add(departmentBox);
		this.add(yearBox);
		this.add(search);
		this.add(displayLabel);
		
		//Set Button Click Listener
		this.search.addActionListener(new ButtonClickListener(this));
		this.search.setActionCommand("SEARCH");
	}
	
	private class ButtonClickListener implements ActionListener {

		/**
		 * @params Select select
		 * Class that listens for button clicks and takes appropriate action
		 */
		
		Select select;
		
		public ButtonClickListener(Select select) {
			this.select=select;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/studentDB?useSSL=false","root","adbms1234");
				Statement statement = connection.createStatement();
				ResultSet resultSet;
				
				String command = e.getActionCommand();
				String toPrint="<html>";
				switch(command) {
					case "SEARCH": {
						String sql = "select ";
						boolean firstSelected=false;
						if(this.select.rollNumberBox.isSelected()) {
							sql+= "id";
							System.out.print("ID\t");
							toPrint+="ID    ";
							if(!firstSelected) firstSelected=true;
						}
						if(this.select.nameBox.isSelected()) {
							if(firstSelected) {
								sql+=", ";
							} else firstSelected=true;
							System.out.print("NAME\t");
							toPrint+="NAME    ";
							sql+="name";
						}
						if(this.select.departmentBox.isSelected()) {
							if(firstSelected) {
								sql+=", ";
							} else firstSelected=true;
							System.out.print("DEPARTMENT\t");
							toPrint+="DEPARTMENT    ";
							sql+="department";
						}
						if(this.select.yearBox.isSelected()) {
							if(firstSelected) {
								sql+=", ";
							} else firstSelected=true;
							System.out.print("YEAR\t");
							toPrint+="YEAR    ";
							sql+="year";
						}
						if(!firstSelected) {
							System.out.println("Please choose atleast one checkbox");
							break;
						}
						System.out.println();
						toPrint+="<br>";
						resultSet=statement.executeQuery(sql + " from student;");
						while(resultSet.next()) {
							int i=1;
							if(this.select.rollNumberBox.isSelected()) {
								int temp = resultSet.getInt(i++);
								System.out.print(temp + "\t");
								toPrint+=temp + "    ";
							}
							if(this.select.nameBox.isSelected()) {
								String temp = resultSet.getString(i++);
								System.out.print( temp + "\t");
								toPrint+=temp+"    ";
							}
							if(this.select.departmentBox.isSelected()) {
								String temp = resultSet.getString(i++);
								System.out.print(temp + "\t");
								toPrint+=temp+"    ";
							}
							if(this.select.yearBox.isSelected()) {
								int temp=resultSet.getInt(i++) ;
								System.out.print(temp + "\t");
								toPrint+=temp+"    ";
							}
							toPrint+="<br>";
							System.out.println();
						}
						toPrint+=" </html>";
						this.select.displayLabel.setText(toPrint);
						break;
					} default: {
						System.out.println("Invalid Command");
					}
				}
				connection.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			
		}
		
		
	}
}
