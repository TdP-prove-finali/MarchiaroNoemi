package it.polito.tdp.SortedWaste;

import it.polito.tdp.SortedWaste.controller.AvvioController;
import it.polito.tdp.SortedWaste.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class EntryPoint extends Application 
{

    @Override
    public void start(Stage stage) throws Exception 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/avvioProgramma.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);
         
        Model model = new Model();
        AvvioController controller = loader.getController();
        controller.setModel(model);
        
        stage.setTitle("Tesi Marchiaro");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }

}