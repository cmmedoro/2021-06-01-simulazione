package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {
	
	//parametri in ingresso
	private Genes partenza;
	private int n; //numero bioingegneri
	
	//parametri in uscita
	private List<Genes> inStudio; //elenco dei geni per ogni ingegnere ---> indice corrisponde all'ingegnere
	private Map<Genes, Integer> inStudioRis; //numero ingegneri per quel gene
	
	//stato del mondo
	private int mesi; //numero mesi per la simulazione
	private double p1; //0.3
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private Model model;
	
	//coda degli eventi
	private PriorityQueue<Evento> queue;
	
	public Simulatore(Graph<Genes, DefaultWeightedEdge> g, Genes start, int num, Model m) {
		this.grafo = g;
		this.n = num;
		this.partenza = start;
		this.model = m;
	}
	
	public void init() {
		//inizializza tutti i parametri
		this.mesi = 24;
		this.p1 = 0.3;
		this.inStudio = new ArrayList<>();
		//controlla che il vertice di partenza non sia isolato
		if(this.grafo.degreeOf(partenza) == 0) {
			new IllegalArgumentException("Vertice isolato");
		}
		//inizializzo la coda degli eventi
		this.queue = new PriorityQueue<>();
		for(int i = 0; i < this.n; i++) {
			this.queue.add(new Evento(0, i));
		}
		//inizializzo la lista dei geni in studio con quello di partenza perchè è quello in studio all'inizio
		for(int i = 0; i <  this.n; i++) {
			this.inStudio.add(partenza);
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Evento e) {
		int mese = e.getMese();
		int bioIng = e.getNumIngegnere();
		
		if(mese < this.mesi) {
			mese++; 
			if(Math.random() < this.p1) {
				//se sono qui non cambia il gene studiato
				this.queue.add(new Evento(mese, bioIng));
			}else {
				//qui invece cambia il gene studiato
				List<Interactions> adiacenti = this.model.getAdiacenti(this.inStudio.get(bioIng)); //prendo i vertici adiacenti al gene in studio
				double tot = this.model.sumWeightsNear(adiacenti);
				//prendo un valore casuale fra 0 e il totale
				double casual = Math.random()*tot;
				Genes nuovo = null;
				double somma = 0.0; 
				//devo confrontare con le somme parziali dei pesi: la somma delle probabilità per ogni arco farà 1, quindi per capire quale gene prendere
				//devo selezionarlo non in base alla sua semplice probabilità, bensì rispetto anche a quelli calcolati in precedenza
				for(Interactions ii : adiacenti) {
					somma += ii.getExpressionCorr();
					if(somma > casual) {
						nuovo = ii.getG2();
						break;
					}
				}
				this.queue.add(new Evento(mese, bioIng));
				this.inStudio.add(bioIng, nuovo);
			}
		}
	}
	
	public Map<Genes, Integer> getMappa(){
		this.inStudioRis = new HashMap<>();
		for(Genes g : this.inStudio) {
			if(this.inStudioRis.containsKey(g)) {
				this.inStudioRis.put(g, this.inStudioRis.get(g)+1);
			}else {
				this.inStudioRis.put(g, 1);
			}
		}
		return this.inStudioRis;
	}

}
