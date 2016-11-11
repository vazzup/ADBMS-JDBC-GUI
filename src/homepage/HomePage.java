package homepage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import delete.Delete;
import insert.Insert;
import select.Select;
import update.Update;

public class HomePage extends JFrame {
	
	/**
	 * @author vazzup
	 * Main Class
	 * Has Four JButtons to call Windows for Insert, Select, Update and Delete Functions
	 */
	
	private JButton insert, select, update, delete;
	private static final long serialVersionUID = 1L;

	public HomePage() {
		
		/*
		 * Default Public Constructor
		 */
		this.setSize(660, 280);
		
		this.prepareButtons();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(null);
	}
	
	public void prepareButtons() {

		/*
		 * Function to prepare and add components to GUI
		 * Seperated from run(), and constructor so as to keep it clean
		 */
		
		//Initialize Components
		this.insert=new JButton("Insert");
		this.select=new JButton("Select");
		this.update=new JButton("Update");
		this.delete=new JButton("Delete");
		
		//Place Components in JFrame
		this.insert.setBounds(100, 100, 100, 20);
		this.select.setBounds(220, 100, 100, 20);
		this.update.setBounds(340, 100, 100, 20);
		this.delete.setBounds(460, 100, 100, 20);
		
		//Add Components to JFrame
		this.add(this.insert);
		this.add(this.select);
		this.add(this.update);
		this.add(this.delete);
		
		//Set Button Click Listener
		this.insert.setActionCommand("INSERT");
		this.select.setActionCommand("SELECT");
		this.delete.setActionCommand("DELETE");
		this.update.setActionCommand("UPDATE");
		
		this.insert.addActionListener(new ButtonClickListener());
		this.select.addActionListener(new ButtonClickListener());
		this.delete.addActionListener(new ButtonClickListener());
		this.update.addActionListener(new ButtonClickListener());
		
	}
	
	public static void main(String args[]) {
		
		// Main Function
		HomePage homePage = new HomePage();
		homePage.setVisible(true);
	}
	
	private class ButtonClickListener implements ActionListener {

		/**
		 * Class that listens for button clicks and takes appropriate action
		 */
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			switch(command) {
				case "INSERT": {
					Thread thread = new Thread(new Insert());
					thread.start();
					try {
						thread.join();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					break;
				} case "UPDATE": {
					Thread thread = new Thread(new Update());
					thread.start();
					try {
						thread.join();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					break;
				} case "SELECT": {
					Thread thread = new Thread(new Select());
					thread.start();
					try {
						thread.join();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					break;
				} case "DELETE": {
					Thread thread = new Thread(new Delete());
					thread.start();
					try {
						thread.join();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					break;
				}
				default: {
					System.out.println("Invalid Command");
				}
			}
			
		}
		
	}
}
