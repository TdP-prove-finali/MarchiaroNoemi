/**
 * Sample Skeleton for 'riepilogo.fxml' Controller Class
 */

package it.polito.tdp.prova;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.prova.model.Cassonetto;
import it.polito.tdp.prova.model.Model;
import it.polito.tdp.prova.model.Riepilogo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RiepilogoController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="clIndirizzo"
    private TableColumn<Cassonetto, String> clIndirizzo; // Value injected by FXMLLoader
    @FXML // fx:id="tblCassonetti"
    private TableView<Cassonetto> tblCassonetti; // Value injected by FXMLLoader
    @FXML // fx:id="txtCapacitaCamion"
    private TextField txtCapacitaCamion; // Value injected by FXMLLoader
    @FXML // fx:id="txtCassonettiDaSvuotareN"
    private TextField txtCassonettiDaSvuotareN; // Value injected by FXMLLoader
    @FXML // fx:id="txtDiscarica"
    private TextField txtDiscarica; // Value injected by FXMLLoader
    @FXML // fx:id="txtDurataPrevista"
    private TextField txtDurataPrevista; // Value injected by FXMLLoader
    @FXML // fx:id="txtPartenzaDa"
    private TextField txtPartenzaDa; // Value injected by FXMLLoader
    @FXML // fx:id="txtRitornoA"
    private TextField txtRitornoA; // Value injected by FXMLLoader
    @FXML // fx:id="txtSuggerimenti"
    private TextArea txtSuggerimenti; // Value injected by FXMLLoader
    @FXML // fx:id="txtTipoRifiuto"
    private TextField txtTipoRifiuto; // Value injected by FXMLLoader

    private Model model;
    
    
    @FXML
    void tornaPianificaFromRiepilogo(ActionEvent event) 
    {

    }

    @FXML
    void tornaStatoFromRiepilogo(ActionEvent event) 
    {

    }
    
    public void displayRiepilogo(Riepilogo riepilogo)
    {
    	this.txtTipoRifiuto.setText(riepilogo.getTipoRifiuto());
    	this.txtCapacitaCamion.setText(riepilogo.getCapacitaCamion().toString());
    	this.txtCassonettiDaSvuotareN.setText(riepilogo.getnCassonetti().toString());
    	this.txtDurataPrevista.setText(riepilogo.getDurataPrevista().toString());
    	
    	this.txtPartenzaDa.setText(riepilogo.getPartenza().toString());
    	this.tblCassonetti.setItems(FXCollections.observableArrayList(riepilogo.getPercorso()));
    	this.txtDiscarica.setText(riepilogo.getDiscarica().toString());
    	this.txtRitornoA.setText(riepilogo.getArrivo().toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        assert clIndirizzo != null : "fx:id=\"clIndirizzo\" was not injected: check your FXML file 'riepilogo.fxml'.";
        assert tblCassonetti != null : "fx:id=\"tblCassonetti\" was not injected: check your FXML file 'riepilogo.fxml'.";
        assert txtCapacitaCamion != null : "fx:id=\"txtCapacitaCamion\" was not injected: check your FXML file 'riepilogo.fxml'.";
        assert txtCassonettiDaSvuotareN != null : "fx:id=\"txtCassonettiDaSvuotareN\" was not injected: check your FXML file 'riepilogo.fxml'.";
        assert txtDiscarica != null : "fx:id=\"txtDiscarica\" was not injected: check your FXML file 'riepilogo.fxml'.";
        assert txtDurataPrevista != null : "fx:id=\"txtDurataPrevista\" was not injected: check your FXML file 'riepilogo.fxml'.";
        assert txtPartenzaDa != null : "fx:id=\"txtPartenzaDa\" was not injected: check your FXML file 'riepilogo.fxml'.";
        assert txtRitornoA != null : "fx:id=\"txtRitornoA\" was not injected: check your FXML file 'riepilogo.fxml'.";
        assert txtSuggerimenti != null : "fx:id=\"txtSuggerimenti\" was not injected: check your FXML file 'riepilogo.fxml'.";
        assert txtTipoRifiuto != null : "fx:id=\"txtTipoRifiuto\" was not injected: check your FXML file 'riepilogo.fxml'.";

        this.clIndirizzo.setCellValueFactory(new PropertyValueFactory<Cassonetto, String>("indirizzo"));
    }

    public void setModel(Model model) 
   	{
   		
   		String s1 = "Viene visualizzato il riepilogo del percorso ottimo calcolato.";
   		String s2 = "Inoltre è possibile tornare alle schermate precedenti.";
   		
   		this.txtSuggerimenti.setText(s1 + "\n" + s2);
   	}
}
