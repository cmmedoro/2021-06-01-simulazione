package it.polito.tdp.genes.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;

public class TestDao {

	public static void main(String[] args) {

		GenesDao dao = new GenesDao();
		List<Genes> list = dao.getAllGenes();
		
		Map<String, Genes> idMap = new HashMap<>();
		List<Genes> vertex = dao.getVertici(idMap);
		List<Interactions> interazioni = dao.getInterazioni(idMap);

		/*for (Genes g : list) {
			System.out.format("%-10s %-20s %1d\n", g.getGeneId(), g.getEssential(), g.getChromosome() );
		}*/
		
		System.out.println(vertex.size());
		System.out.println(interazioni.size());
		
	}

}
