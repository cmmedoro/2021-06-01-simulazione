/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;
import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGeni"
    private ComboBox<Genes> cmbGeni; // Value injected by FXMLLoader

    @FXML // fx:id="btnGeniAdiacenti"
    private Button btnGeniAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtIng"
    private TextField txtIng; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	//in questo caso non ci sono input --> passo direttamente alla creazione del grafo
    	this.model.creaGrafo();
    	this.txtResult.appendText("Grafo creato con "+this.model.nVertices()+" vertici e "+this.model.nArchi()+" archi.\n");
    	//riempire la tendina con gli archi
    	this.cmbGeni.getItems().clear();
    	this.cmbGeni.getItems().addAll(this.model.getVertices());
    }

    @FXML
    void doGeniAdiacenti(ActionEvent event) {
    	this.txtResult.clear();
    	//controllo sull'input
    	if(!this.model.isGraphCreated()) {
    		this.txtResult.setText("Devi creare prima il grafo");
    		return;
    	}
    	Genes g = this.cmbGeni.getValue();
    	if(g == null) {
    		this.txtResult.setText("Devi selezionare un gene dalla combobox");
    		return;
    	}
    	//se sono qui, tutto a posto
    	this.txtResult.setText("Geni adiacenti a "+g+": \n");
    	for(Interactions ii : this.model.getAdiacenti(g)) {
    		this.txtResult.appendText(""+ii.getG2()+" "+ii.getExpressionCorr()+"\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	//prendi l'input e controlla
    	if(!this.model.isGraphCreated()) {
    		this.txtResult.setText("Devi creare prima il grafo");
    		return;
    	}
    	Genes g = this.cmbGeni.getValue();
    	if(g == null) {
    		this.txtResult.setText("Devi selezionare un gene dalla combobox");
    		return;
    	}
    	int n;
    	try {
    		n = Integer.parseInt(this.txtIng.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire un numero intero come numero di ingegneri");
    		return;
    	}
    	//Se sono qui posso avviare la simulazione
    	Map<Genes, Integer> studiati = this.model.simula(g, n);
    	//Stampa elenco dei geni studiati
    	this.txtResult.setText("Geni studiati: \n");
    	for(Genes gg : studiati.keySet()) {
    		this.txtResult.appendText("GENE: "+gg+" - #INGEGNERI: "+studiati.get(gg)+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGeni != null : "fx:id=\"cmbGeni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGeniAdiacenti != null : "fx:id=\"btnGeniAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtIng != null : "fx:id=\"txtIng\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
}
