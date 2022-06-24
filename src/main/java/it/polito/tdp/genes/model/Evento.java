package it.polito.tdp.genes.model;

public class Evento implements Comparable<Evento>{

	//l'evento è lo studio condotto da parte di un ingegnere su un certo gene
	private int numIngegnere;
	private int mese;
	public Evento(int numIngegnere, int mese) {
		super();
		this.numIngegnere = numIngegnere;
		this.mese = mese;
	}
	public int getNumIngegnere() {
		return numIngegnere;
	}
	public void setNumIngegnere(int numIngegnere) {
		this.numIngegnere = numIngegnere;
	}
	public int getMese() {
		return mese;
	}
	public void setMese(int mese) {
		this.mese = mese;
	}
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.mese-o.mese;
	}
	
	
}
