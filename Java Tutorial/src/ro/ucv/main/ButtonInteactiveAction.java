package ro.ucv.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import ro.ucv.main.interactive.PictureGUI;

public class ButtonInteactiveAction implements  ActionListener{
	JFrame g;
	
	public ButtonInteactiveAction(JFrame g){
		this.g=g;
	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		//create a new gui when button pressed
		PictureGUI p=new PictureGUI();
		
	}

}
