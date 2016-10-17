package insert;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Insert extends JFrame implements Runnable{

	private JTextField rollNumberText, nameText, departmentText, yearText;
	private JLabel rollNumberLabel, nameLabel, departmentLabel, yearLabel;
	private JButton cancel, insert;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}
	
	public Insert() {
		
		this.setSize(1000, 1000);
		
		this.prepareComponents();
		
	}
	
	public void prepareComponents () {
		
		this.setLayout(null);
		
		this.cancel=new JButton("CANCEL");
		this.insert=new JButton("INSERT");
		
		this.rollNumberText=new JTextField();
		this.nameText=new JTextField();
		this.departmentText=new JTextField();
		this.yearText=new JTextField();
		
		this.rollNumberLabel=new JLabel("Roll Number:");
		this.nameLabel=new JLabel("Name:");
		this.departmentLabel=new JLabel("Department:");
		this.yearLabel=new JLabel("Year:");
		//this.rollNumberLabel.setOpaque(true);
		//this.rollNumberLabel.setBackground(Color.BLACK);
		
		this.rollNumberLabel.setBounds(50, 50, 100, 20);
		this.rollNumberText.setBounds(170, 50, 200, 20);
		this.nameLabel.setBounds(50, 90, 100, 20);
		this.nameText.setBounds(170, 90, 200, 20);
		this.departmentLabel.setBounds(50, 130, 100, 20);
		this.departmentText.setBounds(170, 130, 200, 20);
		this.yearLabel.setBounds(50, 170, 100, 20);
		this.yearText.setBounds(170, 170, 200, 20);
		
		this.cancel.setBounds(50, 220, 100, 20);
		this.insert.setBounds(170, 220, 100, 20);
		
		this.add(this.rollNumberLabel);
		this.add(this.nameLabel);
		this.add(this.departmentLabel);
		this.add(this.yearLabel);
		this.add(this.rollNumberText);
		this.add(this.nameText);
		this.add(this.departmentText);
		this.add(this.yearText);
		this.add(this.cancel);
		this.add(this.insert);
		
		cancel.addActionListener(new ButtonClickListener(this));
		insert.addActionListener(new ButtonClickListener(this));
		cancel.setActionCommand("CANCEL");
		insert.setActionCommand("INSERT");
	}
	
	private static class ButtonClickListener implements ActionListener {

		private Insert insert;
		
		public ButtonClickListener(Insert insert) {
			this.insert=insert;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/studentDB?useSSL=false","root","adbms1234");
				Statement statement = connection.createStatement();
				int resultUpdate;
				
				String command = e.getActionCommand();
				
				switch(command) {
					case "INSERT": {
						String temporaryString;
						String name, departmentName;
						int rollNumber, year;
						temporaryString=this.insert.rollNumberText.getText();
						if(temporaryString.equalsIgnoreCase("")) {
							System.out.println("Please enter the details properly");
							return;
						}
						rollNumber = Integer.parseInt(temporaryString.trim());
						temporaryString=this.insert.nameText.getText();
						if(temporaryString.equalsIgnoreCase("")) {
							System.out.println("Please enter the details properly");
							return;
						}
						name = temporaryString.trim();
						temporaryString=this.insert.departmentText.getText();
						if(temporaryString.equalsIgnoreCase("")) {
							System.out.println("Please enter the details properly");
							return;
						}
						departmentName = temporaryString.trim();
						temporaryString=this.insert.yearText.getText();
						if(temporaryString.equalsIgnoreCase("")) {
							System.out.println("Please enter the details properly");
							return;
						}
						year = Integer.parseInt(temporaryString.trim());
						String sql = "insert into student values(" + rollNumber + ", " + "\"" + name + "\", " + "\"" + departmentName + "\", " + year + ");";
						try {
							resultUpdate=statement.executeUpdate(sql);
						} catch (Exception exception) {
							System.out.println("Enter correct query");
							System.out.println(sql);
						}
						this.insert.dispose();
						break;
					} case "CANCEL": {
						this.insert.dispose();
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
