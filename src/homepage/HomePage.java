package homepage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import insert.Insert;

public class HomePage extends JFrame {
	
	/**
	 * 
	 */
	
	private JButton insert, select, update, delete;
	private static final long serialVersionUID = 1L;

	HomePage() {
		
		this.setSize(660, 280);
		
		this.prepareButtons();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(null);
	}
	
	public void prepareButtons() {

		this.insert=new JButton("Insert");
		this.select=new JButton("Select");
		this.update=new JButton("Update");
		this.delete=new JButton("Delete");
		
		this.insert.setBounds(100, 100, 100, 20);
		this.select.setBounds(220, 100, 100, 20);
		this.update.setBounds(340, 100, 100, 20);
		this.delete.setBounds(460, 100, 100, 20);
		
		this.add(this.insert);
		this.add(this.select);
		this.add(this.update);
		this.add(this.delete);
		
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
		HomePage homePage = new HomePage();
		homePage.setVisible(true);
	}
	
	private class ButtonClickListener implements ActionListener {

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
				} case "UPDATE": {
					
				} case "SELECT": {
					
				} case "DELETE": {
					
				}
				default: {
					System.out.println("Invalid Command");
				}
			}
			
		}
		
	}
}
