package it.polito.tdp.prova;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.prova.model.Cassonetto;
import it.polito.tdp.prova.model.Model;
import it.polito.tdp.prova.model.Riepilogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CalcolaPercorsoController 
{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="btnAccettaPercorso"
    private Button btnAccettaPercorso; // Value injected by FXMLLoader
    @FXML // fx:id="btnIndietro"
    private Button btnIndietro; // Value injected by FXMLLoader
    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader
    @FXML // fx:id="clIndirizzoEsclusi"
    private TableColumn<Cassonetto, String> clIndirizzoEsclusi; // Value injected by FXMLLoader
    @FXML // fx:id="clIndirizzoPercorso"
    private TableColumn<Cassonetto, String> clIndirizzoPercorso; // Value injected by FXMLLoader
    @FXML // fx:id="clPercentualeEsclusi"
    private TableColumn<Cassonetto, Integer> clPercentualeEsclusi; // Value injected by FXMLLoader
    @FXML // fx:id="clPercentualePercorso"
    private TableColumn<Cassonetto, Integer> clPercentualePercorso; // Value injected by FXMLLoader
    @FXML // fx:id="cmbTipo"
    private ComboBox<String> cmbTipo; // Value injected by FXMLLoader
    @FXML // fx:id="pieCassonetti"
    private PieChart pieCassonetti; // Value injected by FXMLLoader
    @FXML // fx:id="pieRifiuti"
    private PieChart pieRifiuti; // Value injected by FXMLLoader
    @FXML // fx:id="tblEsclusi"
    private TableView<Cassonetto> tblEsclusi; // Value injected by FXMLLoader
    @FXML // fx:id="tblPercorso"
    private TableView<Cassonetto> tblPercorso; // Value injected by FXMLLoader
    @FXML // fx:id="txtCapacitaMezzo"
    private TextField txtCapacitaMezzo; // Value injected by FXMLLoader
    @FXML // fx:id="txtCassonettiNonSvuotatiN"
    private TextField txtCassonettiNonSvuotatiN; // Value injected by FXMLLoader
    @FXML // fx:id="txtCassonettiSvuotatiN"
    private TextField txtCassonettiSvuotatiN; // Value injected by FXMLLoader
    @FXML // fx:id="txtCassonettiVuotiN"
    private TextField txtCassonettiVuotiN; // Value injected by FXMLLoader
    @FXML // fx:id="txtDurataEffettivaViaggio"
    private TextField txtDurataEffettivaViaggio; // Value injected by FXMLLoader
    @FXML // fx:id="txtQuantitaNonRaccolta"
    private TextField txtQuantitaNonRaccolta; // Value injected by FXMLLoader
    @FXML // fx:id="txtQuantitaRaccolta"
    private TextField txtQuantitaRaccolta; // Value injected by FXMLLoader
    @FXML // fx:id="txtSuggerimenti"
    private TextArea txtSuggerimenti; // Value injected by FXMLLoader
    @FXML // fx:id="txtdurataViaggioMassima"
    private TextField txtdurataViaggioMassima; // Value injected by FXMLLoader
    
    private Model model;
    
    private Riepilogo riepilogo;

    @FXML
    void accettaPercorso(ActionEvent event) throws IOException 
    {
    	//cambio schermata e passo i dati del riepilogo
    	
    	Stage stage = null;
    	Parent root = null;
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/riepilogo.fxml"));
        root = loader.load();
        RiepilogoController controller = loader.getController();
        // inserisco i dati di riepilogo nella schermata successiva
        controller.displayRiepilogo(this.riepilogo);
        // svuoto i cassonetti del percorso accettato
        this.model.svuotaCassonettiPercorso();
        
        Model model = new Model(); 
        controller.setModel(model);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void calcolaPercorso(ActionEvent event) 
    {
    	// acquisisco i dati capacità mezzo e durata turno
    	String capacitaS = this.txtCapacitaMezzo.getText();
    	String durataS = this.txtdurataViaggioMassima.getText();
    	// controllo che la capacità del mezzo sia un numero intero 'ragionevole'
    	Integer capacita;
    	try
    	{
    		capacita = Integer.parseInt(capacitaS);
    		
    		if(capacita < 1000 || capacita > 60000)
    		{
    			String s1 = "ERRORE! Controlla che la capacià del mezzo di raccolta sia corretta.";
    	    	String s2 = "La capacità è espressa in Litri e non in metri cubi.";
    	    	this.txtSuggerimenti.setText(s1 + "\n" + s2);
        		this.txtCapacitaMezzo.clear();
        		return;
    		}
    	}
    	catch (NumberFormatException e)
    	{
    		String s1 = "ERRORE! La capacià del mezzo di raccolta deve essere in formato numerico.";
	    	String s2 = "La capacità è espressa in Litri e non in metri cubi.";
	    	this.txtSuggerimenti.setText(s1 + "\n" + s2);
    		this.txtCapacitaMezzo.clear();
    		return;
    	}
    	
    	// controllo che la durata del turno sia scritta in formato corretto H:M e la converto in minuti
    	String[] array;
    	Integer durata;
    	try 
    	{
    		array = durataS.split(":");
    		Integer ore = Integer.parseInt(array[0]);
    		Integer minuti = Integer.parseInt(array[1]);
    		
    		if(ore < 0 || minuti < 0 || minuti > 59)
    		{
    			String s1 = "ERRORE! Controlla che la durata del turno sia corretta.";
    	    	String s2 = "Ore e minuti vanno scritti in formato numerico, separati dai due punti.";
    	    	this.txtSuggerimenti.setText(s1 + "\n" + s2);
        		this.txtCapacitaMezzo.clear();
        		return;
    		}
    		
    		durata = ore*60 + minuti;
    	}
    	catch (Exception e)
    	{
    		String s1 = "ERRORE! La durata del turno deve essere inserita nel formato h:m";
	    	String s2 = "Ore e minuti vanno scritti in formato numerico, separati dai due punti.";
	    	this.txtSuggerimenti.setText(s1 + "\n" + s2);
    		this.txtdurataViaggioMassima.clear();
    		return;
    	}
    	
    	// acquisisco il tipo di rifiuto
    	String tipo = this.cmbTipo.getValue();
    	
    	if(tipo == null)
    	{
    		// nessun tipo selezionato --> suggerimento
    		
    		this.txtSuggerimenti.setText("ERRORE! Seleziona un tipo di rifiuto dal menù a tendina");
    		
    		return;
    	}
    
    	// calcolo il percorso ottimo
    	List<Cassonetto> percorso = this.model.calcolaPercorso(tipo, capacita, durata);
    	
    	// riempio le tabelle Percorso e Esclusi
    	this.tblPercorso.setItems(FXCollections.observableArrayList(percorso));
    	this.tblEsclusi.setItems(FXCollections.observableArrayList(model.getEsclusi()));
    	
    	// riempio le caselle di testo con le statistiche
    	this.txtCassonettiSvuotatiN.setText(this.model.getCassonettiSvuotati().toString());
    	this.txtCassonettiNonSvuotatiN.setText(this.model.getCassonettiNonSvuotati().toString());
    	this.txtCassonettiVuotiN.setText(this.model.getCassonettiVuoti().toString());
    	this.txtQuantitaRaccolta.setText(this.model.getQuantitaRaccolta().toString());
    	this.txtQuantitaNonRaccolta.setText(this.model.getQuantitaNonRaccolta().toString());
    	
    	// creo i grafici
    	ObservableList<PieChart.Data> datiCassonetti = FXCollections.observableArrayList(
                new PieChart.Data("Cassonetti svuotati", this.model.getCassonettiSvuotati()),
                new PieChart.Data("Cassonetti non svuotati", this.model.getCassonettiNonSvuotati()),
                new PieChart.Data("Cassonetti vuoti", this.model.getCassonettiVuoti()));
    	this.pieCassonetti.setData(datiCassonetti);
    	this.pieCassonetti.setClockwise(true); 
    	this.pieCassonetti.setLabelsVisible(true);
    	this.pieCassonetti.setStartAngle(0);     
    	this.pieCassonetti.setLegendVisible(true); 
    	this.pieCassonetti.setVisible(true);
    	this.pieCassonetti.setLabelLineLength(10);
    	this.pieCassonetti.setLegendSide(Side.BOTTOM);
    	
    	ObservableList<PieChart.Data> datiRifiuti = FXCollections.observableArrayList(
                new PieChart.Data("Quantità raccolta", this.model.getQuantitaRaccolta()),
                new PieChart.Data("Quantità non raccolta", this.model.getQuantitaNonRaccolta()));
    	this.pieRifiuti.setData(datiRifiuti);
    	this.pieRifiuti.setClockwise(true); 
    	this.pieRifiuti.setLabelsVisible(true);
    	this.pieRifiuti.setStartAngle(0);     
    	this.pieRifiuti.setLegendVisible(true); 
    	this.pieRifiuti.setVisible(true);
    	this.pieRifiuti.setLabelLineLength(10);
    	this.pieRifiuti.setLegendSide(Side.BOTTOM);
    	
    	// chiedo al model il riepilogo
    	this.riepilogo = model.getRiepilogo();
    	
    	// aggiorno i suggerimenti
    	String s1 = "Viene visualizzato il percorso ottimo con le relative statistiche.";
    	String s2 = "Si può accettare il percorso consigliato, calcolare un nuovo percorso con parametri diversi o tornare alla schermata iniziale.";
    	
    	this.txtSuggerimenti.setText(s1 + "\n" + s2);
    }

    @FXML
    void changeTipoChangeCapacitaMezzo(ActionEvent event) 
    {
    	// rendo editabili le due caselle di testo su capacità mezzo e durata turno
    	this.txtCapacitaMezzo.setDisable(false);
    	this.txtdurataViaggioMassima.setDisable(false);
    	
    	// rendo cliccabile il bottone per calcolare il percorso
    	this.btnCalcola.setDisable(false);
    	
    	// aggiungo la durata del turno di default
    	this.txtdurataViaggioMassima.setText("6:20");
    	
    	// aggiungo la capacità del mezzo in base al tipo di rifiuto selezionato
    	
    	String tipo = this.cmbTipo.getValue();
    	
    	if(tipo.compareTo("vetro") == 0)
    	{
    		// vetro ---> 25 m3
    		this.txtCapacitaMezzo.setText("25000");
    	}
    	else
    	{
    		// altri rifiuti ---> 20 m3
    		this.txtCapacitaMezzo.setText("20000");
    	}
    	
    	// aggiorno i suggerimenti
    	String s1 = "Ora è possibile calcolare il percorso di raccolta ottimizzato per il tipo di rifiuto selezionato.";
    	String s2 = "Inoltre è possibile modificare i parametri di default (capacità mezzo e durata viaggio massima).";
    	
    	this.txtSuggerimenti.setText(s1 + "\n" + s2);
    }

    @FXML
    void indietroToStato(ActionEvent event) throws IOException 
    {
    	//cambio schermata
    	
    	Stage stage = null;
    	Parent root = null;
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/statoCassonetti.fxml"));
        root = loader.load();
        StatoCassonettiController controller = loader.getController();
        Model model = new Model(); 
        controller.setModel(model);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        assert btnAccettaPercorso != null : "fx:id=\"btnAccettaPercorso\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert btnIndietro != null : "fx:id=\"btnIndietro\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert clIndirizzoEsclusi != null : "fx:id=\"clIndirizzoEsclusi\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert clIndirizzoPercorso != null : "fx:id=\"clIndirizzoPercorso\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert clPercentualeEsclusi != null : "fx:id=\"clPercentualeEsclusi\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert clPercentualePercorso != null : "fx:id=\"clPercentualePercorso\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert cmbTipo != null : "fx:id=\"cmbTipo\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert pieCassonetti != null : "fx:id=\"pieCassonetti\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert pieRifiuti != null : "fx:id=\"pieRifiuti\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert tblEsclusi != null : "fx:id=\"tblEsclusi\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert tblPercorso != null : "fx:id=\"tblPercorso\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert txtCapacitaMezzo != null : "fx:id=\"txtCapacitaMezzo\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert txtCassonettiNonSvuotatiN != null : "fx:id=\"txtCassonettiNonSvuotatiN\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert txtCassonettiSvuotatiN != null : "fx:id=\"txtCassonettiSvuotatiN\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert txtCassonettiVuotiN != null : "fx:id=\"txtCassonettiVuotiN\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert txtDurataEffettivaViaggio != null : "fx:id=\"txtDurataEffettivaViaggio\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert txtQuantitaNonRaccolta != null : "fx:id=\"txtQuantitaNonRaccolta\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert txtQuantitaRaccolta != null : "fx:id=\"txtQuantitaRaccolta\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert txtSuggerimenti != null : "fx:id=\"txtSuggerimenti\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";
        assert txtdurataViaggioMassima != null : "fx:id=\"txtdurataViaggioMassima\" was not injected: check your FXML file 'calcolaPercorso.fxml'.";

        this.clIndirizzoPercorso.setCellValueFactory(new PropertyValueFactory<Cassonetto, String>("indirizzo"));
        this.clPercentualePercorso.setCellValueFactory(new PropertyValueFactory<Cassonetto, Integer>("percentuale"));
        this.clIndirizzoEsclusi.setCellValueFactory(new PropertyValueFactory<Cassonetto, String>("indirizzo"));
        this.clPercentualeEsclusi.setCellValueFactory(new PropertyValueFactory<Cassonetto, Integer>("percentuale"));
    }
    
    public void setModel(Model model) 
	{
    	this.model = model;
    	
    	this.cmbTipo.getItems().add("carta");
		this.cmbTipo.getItems().add("indifferenziata");
		this.cmbTipo.getItems().add("plastica");
		this.cmbTipo.getItems().add("vetro");
		
		// suggerimenti iniziali
		
		String s1 = "Seleziona il tipo di rifiuto prima di procedere al calcolo del percorso.";
		
		this.txtSuggerimenti.setText(s1);
	}

}



