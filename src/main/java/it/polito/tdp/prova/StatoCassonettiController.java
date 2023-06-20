package it.polito.tdp.prova;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.prova.model.Cassonetto;
import it.polito.tdp.prova.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StatoCassonettiController 
{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="clContenuto"
    private TableColumn<Cassonetto, Integer> clContenuto; // Value injected by FXMLLoader
    @FXML // fx:id="clDimensione"
    private TableColumn<Cassonetto, Integer> clDimensione; // Value injected by FXMLLoader
    @FXML // fx:id="clIndirizzo"
    private TableColumn<Cassonetto, String> clIndirizzo; // Value injected by FXMLLoader
    @FXML // fx:id="clPercentuale"
    private TableColumn<Cassonetto, Integer> clPercentuale; // Value injected by FXMLLoader
    @FXML // fx:id="cmbTipo"
    private ComboBox<String> cmbTipo; // Value injected by FXMLLoader
    @FXML // fx:id="tblCassonetti"
    private TableView<Cassonetto> tblCassonetti; // Value injected by FXMLLoader
    @FXML // fx:id="txtCassonettiN"
    private TextField txtCassonettiN; // Value injected by FXMLLoader
    @FXML // fx:id="txtLitriCamion"
    private TextField txtLitriCamion; // Value injected by FXMLLoader
    @FXML // fx:id="txtLitriTotali"
    private TextField txtLitriTotali; // Value injected by FXMLLoader
    @FXML // fx:id="txtSuggerimenti"
    private TextArea txtSuggerimenti; // Value injected by FXMLLoader
    @FXML // fx:id="btnCalcolaPercorso"
    private Button btnCalcolaPercorso; // Value injected by FXMLLoader
    @FXML // fx:id="btnVisualizzaStato"
    private Button btnVisualizzaStato; // Value injected by FXMLLoader
    
    private Model model;

    /*
     *	Alla pressione del bottone "aggiorna dati"
     *	vengono riempiti i cassonetti 
     */
    @FXML
    void aggiornaDati(ActionEvent event) 
    {
    	// riempio i cassonetti
    	model.riempiCassonetti();
    	
    	// una volta riempiti i cassonetti
    	// abilito il bottone per visualizzare i dati
    	// abilito il bottone per cambiare schermata a calcola percorso
    	// (disabilitato in precedenza)
    	
    	this.btnVisualizzaStato.setDisable(false);
    	this.btnCalcolaPercorso.setDisable(false);
    	
    	// pulisco la schermata
    	
    	this.txtCassonettiN.clear();
    	this.txtLitriCamion.clear();
    	this.txtLitriTotali.clear();
    	this.txtSuggerimenti.clear();
    	this.cmbTipo.setValue(null);
    	this.cmbTipo.setPromptText("Seleziona tipo:");
    	this.tblCassonetti.getItems().clear();
    	
    	// aggiorno suggerimenti
    	String s1 = "Seleziona un tipo di rifiuto dal menù a tendina per visualizzare lo stato dei cassonetti.";
    	String s2 = "Inoltre è ora possibile calcolare un percorso di raccolta.";
    	
    	this.txtSuggerimenti.setText(s1 + "\n" + s2);
    }


    @FXML
    void visualizzaStato(ActionEvent event) 
    {
    	String tipo = this.cmbTipo.getValue();
    	
    	if(tipo == null)
    	{
    		// nessun tipo selezionato --> suggerimento + svuota tabella
    		
    		this.txtSuggerimenti.setText("ERRORE! Seleziona un tipo di rifiuto dal menù a tendina");
    		
    		this.tblCassonetti.getItems().clear();
    		
    		return;
    	}
    	
    	// ho il tipo di rifiuto desiderato
    	// chiedo al model l'elenco di cassonetti, ordinati in modo decrescente di riempimento
    	// riempio la tabella
    	
    	List<Cassonetto> all = model.getCassonettiBytipo(tipo);
    	
    	this.tblCassonetti.setItems(FXCollections.observableArrayList(all));
    	
    	// inserisco le statistiche dei cassonetti
    	
    	// numero cassonetti
    	Integer n = all.size();
    	this.txtCassonettiN.setText(n.toString());
    	
    	// litri totali nei cassonetti
    	Integer tot = 0;
    	
    	for(Cassonetto c: all)
    	{
    		tot = tot + c.getContenuto();
    	}
    		
    	this.txtLitriTotali.setText(tot.toString());
    	
    	// litri camion a disposizione di default
    	Cassonetto c = all.get(0);
    	String tipoCassonetto = c.getTipo();
    	
    	if(tipo.compareTo("vetro") == 0)
    	{
    		this.txtLitriCamion.setText("25000");
    		// camion raccolta vetro --> 25 m3
    	}
    	else
    	{
    		this.txtLitriCamion.setText("20000");
    		// camion altre raccolte --> 20 m3
    	}
    	
    	// aggiorno i suggerimenti
    	String s1 = "In tabella sono presenti i cassonetti del tipo selezionato, ordinati per riempimento decrescente.";
    	String s2 = "Inoltre è possibile calcolare un percorso di raccolta.";
    	
    	this.txtSuggerimenti.setText(s1 + "\n" + s2);
    }

    @FXML
    void goToCalcolaPercorso(ActionEvent event) throws IOException 
    {
    	//cambio schermata
    	
    	Stage stage = null;
    	Parent root = null;
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/calcolaPercorso.fxml"));
        root = loader.load();
        CalcolaPercorsoController controller = loader.getController();
        Model model = new Model(); 
        controller.setModel(model);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        assert clContenuto != null : "fx:id=\"clContenuto\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert clDimensione != null : "fx:id=\"clDimensione\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert clIndirizzo != null : "fx:id=\"clIndirizzo\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert clPercentuale != null : "fx:id=\"clPercentuale\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert cmbTipo != null : "fx:id=\"cmbTipo\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert tblCassonetti != null : "fx:id=\"tblCassonetti\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert txtCassonettiN != null : "fx:id=\"txtCassonettiN\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert txtLitriCamion != null : "fx:id=\"txtLitriCamion\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert txtLitriTotali != null : "fx:id=\"txtLitriTotali\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert txtSuggerimenti != null : "fx:id=\"txtSuggerimenti\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
        assert btnVisualizzaStato != null : "fx:id=\"btnVisualizzaStato\" was not injected: check your FXML file 'statoCassonetti.fxml'.";
      
        this.clIndirizzo.setCellValueFactory(new PropertyValueFactory<Cassonetto, String>("indirizzo"));
        this.clPercentuale.setCellValueFactory(new PropertyValueFactory<Cassonetto, Integer>("percentuale"));
        this.clDimensione.setCellValueFactory(new PropertyValueFactory<Cassonetto, Integer>("dimensione"));
        this.clContenuto.setCellValueFactory(new PropertyValueFactory<Cassonetto, Integer>("contenuto"));
        
    }
    
    public void setModel(Model model) 
	{
		this.model = model;
		
		this.cmbTipo.getItems().add("carta");
		this.cmbTipo.getItems().add("indifferenziata");
		this.cmbTipo.getItems().add("plastica");
		this.cmbTipo.getItems().add("vetro");
		
		String s1 = "Aggiorna i dati e seleziona il tipo di rifiuto per visualizzare lo stato dei cassonetti.";
		String s2 = "Per calcolare un nuovo percorso è necessario aggiornare i dati.";
		
		this.txtSuggerimenti.setText(s1 + "\n" + s2);
	}

}
