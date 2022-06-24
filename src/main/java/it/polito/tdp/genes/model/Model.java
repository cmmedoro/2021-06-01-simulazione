package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	//grafo semplice, pesato, non orientato
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private List<Genes> geni;
	private Map<String, Genes> geniIdMap;
	private List<Interactions> interazioni;
	//strutture dati per la simulazione
	private Map<Genes, Integer> studiatiIng;
	private List<Genes> studiati;
	private Simulatore sim;
	
	public Model() {
		this.dao = new GenesDao();
		this.geniIdMap = new HashMap<>();
	}
	
	public void creaGrafo() {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//inserire i vertici: geni essenziali
		this.geni = new ArrayList<>(this.dao.getVertici(this.geniIdMap));
		Graphs.addAllVertices(this.grafo, this.geni);
		//inserire gli archi: coppia di geni diversi in interactions
		this.interazioni = new ArrayList<>(this.dao.getInterazioni(geniIdMap));
		Double peso = 0.0;
		for(Interactions i : this.interazioni) {
			Genes g1 = i.getG1();
			Genes g2 = i.getG2();
			if(g1.getChromosome() == g2.getChromosome()) {
				peso = Math.abs(i.getExpressionCorr()) * 2;
			}else {
				peso = Math.abs(i.getExpressionCorr());
			}
			Graphs.addEdgeWithVertices(this.grafo, g1, g2, peso);
		}
	}
	
	public boolean isGraphCreated() {
		if(this.grafo == null) {
			return false;
		}
		return true;
	}
	public List<Genes> getVertices(){
		return this.geni;
	}
	public int nVertices() {
		return this.grafo.vertexSet().size();
	}
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	public List<Interactions> getAdiacenti(Genes g){
		List<Interactions> inter = new ArrayList<>();
		List<Genes> adiacenti = new ArrayList<>();
		adiacenti = Graphs.neighborListOf(this.grafo, g);
		//ordino per peso decrescente
		for(Genes gg : adiacenti) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(g, gg));
			inter.add(new Interactions(g, gg, peso));
		}
		Collections.sort(inter);
		return inter;
	}
	//calcolo somma pesi dei geni adiacenti
	public double sumWeightsNear(List<Interactions> adiacenti) {
		double sum = 0.0;
		for(Interactions ii : adiacenti) {
			sum += ii.getExpressionCorr();
		}
		return sum;
	}
	
	public Map<Genes, Integer> simula(Genes partenza, int numIng) {
		this.sim = new Simulatore(this.grafo, partenza, numIng, this );
		this.studiati = new ArrayList<>();
		this.studiatiIng = new HashMap<>();
		this.sim.init();
		this.sim.run();
		this.studiatiIng = this.sim.getMappa();
		return this.studiatiIng;
	}
	
}
