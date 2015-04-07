package ro.ucv.main.maps;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

@SuppressWarnings("serial")
public class MapGUI extends JFrame implements MouseListener {

	private JTextField longitude = new JTextField();
	private JTextField latitude = new JTextField();
	private JTextField radius = new JTextField();
	private Random rand; 
	private int ovalRadius = 0;
	private Double lastZoom;
	private Point lastClick = null;
	private GeoPosition lastClickedLocation,pp,geoSelected;
	private ArrayList<Punct> points,geosSelected;
	@SuppressWarnings("rawtypes")
	private WaypointPainter waypointPainter = new WaypointPainter();
	private JXMapKit map;
	JXMapViewer m;
	JLabel lblRange; 
	@SuppressWarnings("rawtypes")
	JList list;
	Suprafata area;
	int nrpuncte;
	DefaultListModel<String> model;

	@SuppressWarnings({ "unused", "rawtypes" })
	public MapGUI() {
		super("Open Map - needs internet connection");
		this.setVisible(true);
		this.setBounds(150, 150, 850, 650);
		this.setLayout(null);
		nrpuncte=3;
		map = (JXMapKit) (new SetupMap()).createOpenMap();
		map.setBounds(20, 20, 600, 300);
		map.getMainMap().addMouseListener(this);
		add(map);
		m = map.getMainMap();
		points=new ArrayList<Punct>();
		rand=new Random();
		for(int i=0;i<nrpuncte;i++)
			points.add(new Punct(22+rand.nextInt(9)*rand.nextDouble(),44+rand.nextInt(4)*rand.nextDouble(),i+1));
		//points.add(new Punct(23.790550231933594,44.325321736648355,2));
		//points.add(new Punct(23.8238525390625,44.3275321634483,3));
		//points.add(new Punct(23.830032348632812,44.323111226553735,4));
		//points.add(new Punct(23.85852813720703,44.33047926961188,5));
		geosSelected=new ArrayList<Punct>();
		moveWaypoint();
		JLabel lblLong = new JLabel("Longitude");
		JLabel lblLat = new JLabel("Latitude");
		JLabel lblRadius = new JLabel("Radius");
		lblRange = new JLabel("Points in area 0");
		lblLong.setBounds(20, 330, 150, 25);
		lblLat.setBounds(180, 330, 150, 25);
		lblRadius.setBounds(340, 330, 150, 25);
		lblRange.setBounds(500, 330, 150, 25);
		add(lblLong);
		add(lblLat);
		add(lblRadius);
		add(lblRange);
		longitude.setBounds(20, 365, 150, 25);
		latitude.setBounds(180, 365, 150, 25);
		radius.setBounds(340, 365, 150, 25);
		add(longitude);
		add(latitude);
		add(radius);
		model=new DefaultListModel<String>();
		list=new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(5);
		list.setVisible(true);
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false)
				setColorWaypoints();
			}
			
		});
		JScrollPane pane=new JScrollPane(list);
		pane.setBounds(500, 365, 150, 100);
		add(pane);
		repaint();
		waypointPainter.setRenderer(new WaypointRenderer() {
			@Override
			public boolean paintWaypoint(Graphics2D g, JXMapViewer map,
					Waypoint wp) {
				float alpha = 0.6f;
				int type = AlphaComposite.SRC_OVER;
				AlphaComposite composite = AlphaComposite.getInstance(type,
						alpha);
				Color color;
				if (ovalRadius > 0 && wp.getPosition().getLatitude()==pp.getLatitude() && wp.getPosition().getLongitude()==pp.getLongitude()) {
					color = new Color((float) 0.5, 0, 0, alpha);
					Double currentZoom=lastClick.distance(map.getCenter());
					Double r=currentZoom/lastZoom *ovalRadius;
					int zoomedRadius=r.intValue();
					//System.out.println("radius "+ovalRadius+"   zoomed"+zoomedRadius);
					g.setColor(color);
					//g.fillOval(-zoomedRadius, -zoomedRadius, 2 * zoomedRadius,2 * zoomedRadius);
					g.setColor(Color.RED);
					g.drawOval(-zoomedRadius, -zoomedRadius, 2 * zoomedRadius,
							2 * zoomedRadius);
				}
			    color = new Color(1, 0, 0, alpha); // Red
				if(ovalRadius>0 && area!=null)
					for(int i=0;i<area.getGeosSelected().size();i++)
						if(wp.getPosition().getLatitude()==area.getGeosSelected().get(i).getLatitudine() && wp.getPosition().getLongitude()==area.getGeosSelected().get(i).getLongitudine())
							color = new Color(0, 0, 1, alpha);
				if(ovalRadius>0 && geoSelected!=null)
				{
					if(wp.getPosition().getLatitude()==geoSelected.getLatitude() && wp.getPosition().getLongitude()==geoSelected.getLongitude())
						color = new Color(0, 1, 0, alpha); // Red
				}
				g.setColor(color);
				g.fillOval(-5, -5, 10,10);
				g.setColor(Color.BLACK);
				g.drawOval(-5, -5, 10,10);
				return true;
			}
		});
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void mouseClicked(MouseEvent e) {
		m = map.getMainMap();
		GeoPosition g = m.convertPointToGeoPosition(e.getPoint());
		latitude.setText("" + g.getLatitude());
		longitude.setText("" + g.getLongitude());
		if (e.getButton() == MouseEvent.BUTTON1) {
			Punct p=new Punct(g.getLongitude(),g.getLatitude(),points.size()+1);
			lastClick = e.getPoint();
			lastClickedLocation = g;
			ovalRadius = 0;
			pp=g;
			if(points.size()>nrpuncte)
				points.remove(nrpuncte);
			points.add(p);
			moveWaypoint();
			if(!model.isEmpty())
			{
				lblRange.setText("Points in area 0");
				model.clear();
			}
		} else {
			if (lastClick != null) {
				if(!model.isEmpty())
				model.clear();
				double range=geoDistance(lastClickedLocation, g);
				geosSelected.clear();
				radius.setText("" + range);
				for(int i=0;i<points.size()-1;i++)
					if(range>geoDistance(points.get(i),points.get(points.size()-1)))
					{
						String element="Punct "+points.get(i).getEticheta()+", "+Double.toString(points.get(i).getLongitudine())+","+Double.toString(points.get(i).getLatitudine());
						model.addElement(element);
						geosSelected.add(points.get(i));
					}
				area=new Suprafata(new Cerc(new Punct(g.getLongitude(),g.getLatitude(),0),range),geosSelected);
				lblRange.setText("Points in area "+Integer.toString(model.size()));
				list.setModel(model);
				ovalRadius = ((Double) (lastClick.distance(e.getPoint())))
						.intValue();
				lastZoom=lastClick.distance(map.getMainMap().getCenter());
				geoSelected=new GeoPosition(0,0);
			} else
			{
				moveWaypoint();
			}
		}
		repaint();
	}
@SuppressWarnings("unchecked")
public void moveWaypoint() {
		if (waypointPainter.getWaypoints().isEmpty())
		for(Punct gg:points)
			waypointPainter.getWaypoints().add(
					new Waypoint(gg.getLatitudine(), gg.getLongitudine()));
		else {
			waypointPainter.getWaypoints().clear();
			for(Punct gg:points)
			waypointPainter.getWaypoints().add(
					new Waypoint(gg.getLatitudine(), gg.getLongitudine()));
		}
		map.getMainMap().setOverlayPainter(waypointPainter);
	}
	public double returnGeoDistance(double longitudine1,double latitudine1,double longitudine2,double latitudine2)
	{
		final int EARTHRADIUS = 6371; // The radius of the earth in kilometers
		// Get the distance between latitudes and longitudes
		double deltaLat = Math.toRadians(latitudine1 - latitudine2);
		double deltaLong = Math
				.toRadians(longitudine1 - longitudine2);
		// Apply the Haversine function
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(Math.toRadians(latitudine2))
				* Math.cos(Math.toRadians(latitudine1))
				* Math.sin(deltaLong / 2) * Math.sin(deltaLong / 2);
		return EARTHRADIUS * 2 * Math.asin(Math.sqrt(a));
	}
	public double geoDistance(GeoPosition g1, GeoPosition g2) {
		return returnGeoDistance(g1.getLongitude(),g1.getLatitude(),g2.getLongitude(),g2.getLatitude());
	}
	public double geoDistance(Punct g1, Punct g2) {
		return returnGeoDistance(g1.getLongitudine(),g1.getLatitudine(),g2.getLongitudine(),g2.getLatitudine());
	}
	void setColorWaypoints()
	{
		if(model.size()!=0)
		{
			if(list.isSelectionEmpty()==false){
		String value=(String) list.getSelectedValue();
		double lat,lon;
		StringTokenizer tokenizer = new StringTokenizer(value,",");	
		tokenizer.nextToken();
        lon = Double.parseDouble(tokenizer.nextToken());
        lat = Double.parseDouble(tokenizer.nextToken());
        geoSelected=new GeoPosition(lat,lon);
        repaint();
		}}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("entered");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("exited");

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("pressed");

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("releaseed");

	}
}
