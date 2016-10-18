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

	JComboBox dropDownMenu, dropDownMenuTwo;
	JLabel header, headerTwo, equalsTo, to;
	JTextField value, toValue;
	JButton updateButton;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}
	
	public Update() {
		this.setSize(1000, 1000);
		this.setLayout(null);
		this.prepareComponents();
	}
	
	public void prepareComponents() {
		
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
		
		this.header.setBounds(50, 50, 100, 20);
		this.dropDownMenuTwo.setBounds(170, 50, 100, 20);
		this.headerTwo.setBounds(290, 50, 100, 20);
		this.dropDownMenu.setBounds(50, 90, 100, 20);
		this.equalsTo.setBounds(170, 90, 100, 20);
		this.value.setBounds(290, 90, 100, 20);
		this.to.setBounds(410, 90, 100, 20);
		this.toValue.setBounds(530, 90, 100, 20);
		this.updateButton.setBounds(50, 130, 100, 20);
		
		this.add(this.header);
		this.add(this.dropDownMenuTwo);
		this.add(this.headerTwo);
		this.add(this.dropDownMenu);
		this.add(this.equalsTo);
		this.add(this.value);
		this.add(this.to);
		this.add(this.toValue);
		this.add(this.updateButton);
		
		this.updateButton.setActionCommand("UPDATE");
		this.updateButton.addActionListener(new ButtonClickListener(this));
	}
	
	private class ButtonClickListener implements ActionListener {
		
		Update update;
		public ButtonClickListener(Update update) {
			this.update=update;
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
