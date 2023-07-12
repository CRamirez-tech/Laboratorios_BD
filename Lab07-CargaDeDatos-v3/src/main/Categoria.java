package main;

public class Categoria {
	private int CatNum;
	private String CatNom;
	public int getCatNum() {
		return CatNum;
	}
	public void setCatNum(int catNum) {
		CatNum = catNum;
	}
	public String getCatNom() {
		return CatNom;
	}
	public void setCatNom(String catNom) {
		CatNom = catNom;
	}
	public Categoria(int catNum, String catNom) {
		CatNum = catNum;
		CatNom = catNom;
	}
	@Override
	public String toString() {
		return "Categoria [CatNom=" + CatNom + "]";
	}
	
}
