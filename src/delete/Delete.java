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

	JComboBox dropDownMenu;
	JLabel header, equalsTo;
	JTextField value;
	JButton deleteButton;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}
	
	public Delete() {
		this.setSize(700, 500);
		this.setLayout(null);
		this.prepareComponents();
		
	}
	
	public void prepareComponents() {
		
		String dropDownContent[] = {"Roll Number", "Name", "Department", "Year"};
		this.dropDownMenu=new JComboBox(dropDownContent);
		this.header=new JLabel("Delete * where");
		this.equalsTo=new JLabel(" equals ");
		this.value=new JTextField();
		this.deleteButton=new JButton("DELETE");
		
		this.header.setBounds(50, 50, 400, 20);
		this.dropDownMenu.setBounds(50, 90, 100, 20);
		this.equalsTo.setBounds(170, 90, 100, 20);
		this.value.setBounds(290, 90, 100, 20);
		this.deleteButton.setBounds(50, 130, 100, 20);
		
		this.add(header);
		this.add(dropDownMenu);
		this.add(equalsTo);
		this.add(value);
		this.add(deleteButton);
		
		this.deleteButton.setActionCommand("DELETE");
		this.deleteButton.addActionListener(new ButtonClickListener(this));
		
	}
	
	private class ButtonClickListener implements ActionListener {
		
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
			} catch(Exception exception) {
				System.out.println("Incorrect Query");
			}
		}
	}
}
