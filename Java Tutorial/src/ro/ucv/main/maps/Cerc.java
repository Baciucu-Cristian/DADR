package ro.ucv.main.maps;

public class Cerc {
	private Punct centru;
	private double raza;
	public Cerc()
	{
		centru=new Punct();
		raza=0;
	}
	public Cerc(Punct cen,double r)
	{
		centru=cen;
		raza=r;
	}
	public Punct getCentru() {
		return centru;
	}
	public void setCentru(Punct centru) {
		this.centru = centru;
	}
	public double getRaza() {
		return raza;
	}
	public void setRaza(double raza) {
		this.raza = raza;
	}
}
