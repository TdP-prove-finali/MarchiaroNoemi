package it.polito.tdp.prova;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.prova.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AvvioController 
{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="btnPianificaPercorso"
    private Button btnPianificaPercorso; // Value injected by FXMLLoader
    @FXML // fx:id="btnVisualizzaStato"
    private Button btnVisualizzaStato; // Value injected by FXMLLoader

    private Model model;
    
    
    /**
     *	Alla pressione del bottone "start"
     *	vengono riempiti i cassonetti e abilitati i bottoni
     *	@param event
     */
    @FXML
    void avvioProgramma(ActionEvent event) 
    {
    	// riempio i cassonetti
    	model.riempiCassonetti();
    	
    	// una volta riempiti i cassonetti
    	// abilito il bottone per cambiare schermata a 'stato cassonetti'
    	// abilito il bottone per cambiare schermata a 'calcola percorso'
    	// (disabilitati in precedenza)
    	
    	this.btnVisualizzaStato.setDisable(false);
    	this.btnPianificaPercorso.setDisable(false);
    	
    }

    /**
     * metodo che permette di cambiare schermata
     * @param event
     * @throws IOException 
     */
    @FXML
    void goToCalcolaPercorso(ActionEvent event) throws IOException 
    {
    	// cambio schermata
    	
    	// non passo nulla perchè la lista dei cassonetti pieni è salvata nel model
    	
    	Stage stage = null;
    	Parent root = null;
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/calcolaPercorso.fxml"));
        root = loader.load();
        CalcolaPercorsoController controller = loader.getController();
        // Model model = new Model(); 
        // voglio mantenere il model attuale
        controller.setModel(model);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    /**
     * metodo che permette di cambiare schermata
     * @param event
     * @throws IOException 
     */
    @FXML
    void goToStatoCassonetti(ActionEvent event) throws IOException 
    {
    	// cambio schermata
    	
    	// non passo nulla perchè la lista dei cassonetti pieni è salvata nel model
    	
    	Stage stage = null;
    	Parent root = null;
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/statoCassonetti.fxml"));
        root = loader.load();
        StatoCassonettiController controller = loader.getController();
        // Model model = new Model(); 
        // voglio mantenere il model attuale
        controller.setModel(model);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        assert btnPianificaPercorso != null : "fx:id=\"btnPianificaPercorso\" was not injected: check your FXML file 'avvioProgramma.fxml'.";
        assert btnVisualizzaStato != null : "fx:id=\"btnVisualizzaStato\" was not injected: check your FXML file 'avvioProgramma.fxml'.";
    }
    
    public void setModel(Model model) 
	{
		this.model = model;
	}

}
