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

	JCheckBox rollNumberBox, nameBox, departmentBox, yearBox;
	JButton search;
	JLabel displayLabel;
	
	@Override
	public void run() {
		this.setVisible(true);
	}
	
	public Select () {
		this.setSize(500, 500);
		this.setLayout(null);
		this.prepareComponents();
	}
	
	public void prepareComponents() {
		
		this.rollNumberBox=new JCheckBox("Roll Number");
		this.nameBox=new JCheckBox("Name");
		this.departmentBox=new JCheckBox("Department");
		this.yearBox=new JCheckBox("Year");
		this.search=new JButton("Search");
		this.displayLabel=new JLabel();
		
		this.rollNumberBox.setBounds(50, 50, 200, 20);
		this.nameBox.setBounds(50, 90, 200, 20);
		this.departmentBox.setBounds(50, 130, 200, 20);
		this.yearBox.setBounds(50, 170, 200, 20);
		this.search.setBounds(50, 220, 100, 20);
		this.displayLabel.setBounds(50, 250, 400, 200);
		
		this.add(rollNumberBox);
		this.add(nameBox);
		this.add(departmentBox);
		this.add(yearBox);
		this.add(search);
		this.add(displayLabel);
		
		this.search.addActionListener(new ButtonClickListener(this));
		this.search.setActionCommand("SEARCH");
	}
	
	private class ButtonClickListener implements ActionListener {

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
