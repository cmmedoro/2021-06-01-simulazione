package it.polito.tdp.genes.model;

public class Interactions implements Comparable<Interactions>{
	
	private Genes g1;
	private Genes g2;
	//private String type;
	private Double expressionCorr;
	public Interactions(Genes g1, Genes g2,/* String type,*/ Double expressionCorr) {
		super();
		this.g1 = g1;
		this.g2 = g2;
		//this.type = type;
		this.expressionCorr = expressionCorr;
	}
	public Genes getG1() {
		return g1;
	}
	public void setG1(Genes g1) {
		this.g1 = g1;
	}
	public Genes getG2() {
		return g2;
	}
	public void setG2(Genes g2) {
		this.g2 = g2;
	}
	/*public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}*/
	public Double getExpressionCorr() {
		return expressionCorr;
	}
	public void setExpressionCorr(Double expressionCorr) {
		this.expressionCorr = expressionCorr;
	}
	@Override
	public String toString() {
		return "Interactions [g1=" + g1 + ", g2=" + g2 + ", expressionCorr=" + expressionCorr + "]";
	}
	@Override
	public int compareTo(Interactions o) {
		return -(this.expressionCorr.compareTo(o.getExpressionCorr()));
	}
	
	

}
