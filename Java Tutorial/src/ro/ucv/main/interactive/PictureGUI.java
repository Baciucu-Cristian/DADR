package ro.ucv.main.interactive;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class PictureGUI extends JFrame implements MouseListener {

	private LinkedList<Point> entities= new LinkedList<Point>();

	private int ovalRadius=20;	
	
	public void paint(Graphics g) {
		super.paint(g);	//this line will ensure that paint can still draw buttons and other controls	
		
		g.setColor(Color.RED);		
		for(Point p:entities){
			g.fillOval(p.x-ovalRadius/2, p.y-ovalRadius/2, ovalRadius, ovalRadius);
		}
	}

	/**
	 * creates a GUI puts a picture in the background and draws circles on click
	 */
	public PictureGUI() {
		super("Click Anywhere");

		this.setVisible(true);
		this.setBounds(120, 120, 820, 620);
		this.setLayout(null);

		JLabel l = new JLabel();
		l.setBounds(0, 0, 800, 600);
		l.setIcon(new ImageIcon(".\\leaf.jpg"));
		this.add(l);

		
		JButton b = new JButton("dummy buton");
		b.setBounds(200, 500, 150, 25);
		l.add(b);

		this.addMouseListener(this);
		
		repaint();
		

	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point p= new Point(e.getX(),e.getY());
		entities.add(p);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//do something when mouse pointer enters the frame
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// do something when mouse pointer exits the frame	
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
