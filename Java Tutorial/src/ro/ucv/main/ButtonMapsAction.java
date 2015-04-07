package ro.ucv.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ro.ucv.main.maps.MapGUI;

public class ButtonMapsAction implements  ActionListener{

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//create a new gui when button is pressed
		MapGUI g=new MapGUI();
		
	}

}
