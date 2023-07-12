package main;

public class Marca {
	private int MarCod;
	private String MarNom;
	
	public Marca(int marCod, String marNom) {
		MarCod = marCod;
		MarNom = marNom;
	}
	public int getMarCod() {
		return MarCod;
	}
	public void setMarCod(int marCod) {
		MarCod = marCod;
	}
	public String getMarNom() {
		return MarNom;
	}
	public void setMarNom(String marNom) {
		MarNom = marNom;
	}
	@Override
	public String toString() {
		return "Marca [MarNom=" + MarNom + "]";
	}
	
}
