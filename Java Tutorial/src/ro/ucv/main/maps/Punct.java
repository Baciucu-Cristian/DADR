package ro.ucv.main.maps;

public class Punct {
	private double longitudine,latitudine;
	private int eticheta;
	public Punct()
	{
	}
	public Punct(double lon,double lat,int et)
	{
		longitudine=lon;
		latitudine=lat;
		eticheta=et;
	}
	public double getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}
	public double getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}
	public int getEticheta() {
		return eticheta;
	}
	public void setEticheta(int eticheta) {
		this.eticheta = eticheta;
	}
}
