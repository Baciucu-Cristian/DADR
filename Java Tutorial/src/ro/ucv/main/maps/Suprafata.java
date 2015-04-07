package ro.ucv.main.maps;

import java.util.ArrayList;

public class Suprafata {
	private Cerc cerc;
	private ArrayList<Punct> geosSelected;
	public Suprafata()
	{
		cerc=new Cerc();
		geosSelected=new ArrayList<Punct>();
	}
	public Suprafata(Cerc cerc, ArrayList<Punct> geosSelected) {
		this.cerc = cerc;
		this.geosSelected = geosSelected;
	}
	public Cerc getCerc() {
		return cerc;
	}
	public void setCerc(Cerc cerc) {
		this.cerc = cerc;
	}
	public ArrayList<Punct> getGeosSelected() {
		return geosSelected;
	}
	public void setGeosSelected(ArrayList<Punct> geosSelected) {
		this.geosSelected = geosSelected;
	}
	
}
