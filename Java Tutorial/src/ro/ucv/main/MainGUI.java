package ro.ucv.main;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {

	public MainGUI(){
		super("This is the main GUI - close it to kill the app");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBounds(100, 100, 800, 600);
		this.setLayout(null);
		
		JButton b = new JButton("Interactive Gui");
		b.setBounds(10, 10, 150, 25);
		add(b);
		
		JButton b1 = new JButton("Internet Map GUI");
		b1.setBounds(10, 45, 150, 25);
		add(b1);
		
		repaint();
		
		b.addActionListener(new ButtonInteactiveAction(this));
		b1.addActionListener(new ButtonMapsAction());
	}
	
	
	
	
	

}
