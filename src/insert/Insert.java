package insert;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Insert extends JFrame implements Runnable{

	private JTextField rollNumberText, nameText, departmentText, yearText;
	private JButton cancel, insert;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}
	
	Insert() {
		
		this.setSize(500, 1000);
		
	}
	
	public void prepareComponents () {
		
		this.cancel=new JButton("CANCEL");
		this.insert=new JButton("INSERT");
		
		this.rollNumberText=new JTextField();
		this.nameText=new JTextField();
		this.departmentText=new JTextField();
		this.yearText=new JTextField();
		
		this.add(this.cancel);
		this.add(this.insert);
		this.add(this.rollNumberText);
		this.add(this.nameText);;
		this.add(this.departmentText);
		this.add(this.yearText);
	}
	
}
