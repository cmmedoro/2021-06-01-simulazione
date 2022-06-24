package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//metodo per recuperare i vertici ---> geni essenziali
	public List<Genes> getVertici(Map<String, Genes> idMap){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome "
				+ "FROM genes g "
				+ "WHERE g.Essential = 'Essential'";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Genes genes = new Genes(res.getString("GeneID"), res.getString("Essential"), res.getInt("Chromosome"));
				result.add(genes);
				idMap.put(res.getString("GeneID"), genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//prendi tutte le interazioni per i geni che sono contenuti nei vertici
	public List<Interactions> getInterazioni(Map<String, Genes> idMap){
		String sql = "SELECT * "
				+ "FROM interactions";
		List<Interactions> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Genes g1 = idMap.get(res.getString("GeneID1"));
				Genes g2 = idMap.get(res.getString("GeneID2"));
				if(g1 != null && g2 != null && !g1.equals(g2)) {
					Interactions in = new Interactions(g1, g2, /*res.getString("Type"),*/ res.getDouble("Expression_corr"));
					result.add(in);
				}
			}
			res.close();
			st.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//alternativa senza uso di idMap:
	/*
	 * SELECT DISTINCT i.GeneID1, i.GeneID2, i.Expression_Corr
FROM interactions i, genes g1, genes g2
WHERE i.GeneID1 <> i.GeneID2 AND g1.Essential='Essential' AND g2.Essential = g1.Essential AND g1.GeneID = i.GeneID1 AND g2.GeneID = i.GeneID2
	 */

	
}
