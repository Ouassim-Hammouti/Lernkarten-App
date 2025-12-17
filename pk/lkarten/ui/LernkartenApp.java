package pk.lkarten.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pk.Ikarten.EinzelantwortKarte;
import pk.Ikarten.Lernkarte;
import pk.Ikarten.MehrfachantwortKarte;

public class LernkartenApp extends Application {

    @Override 
    public void start(Stage primaryStage) throws Exception {
    	
    	
    	
       EinzelantwortKarte karte1 = new EinzelantwortKarte("Mathe", "Addition", "Was ist 2+2?", "4");
        
       EinzelantwortKarte karte2 = new EinzelantwortKarte(null,null,null,null);
       
       MehrfachantwortKarte karte3 = new MehrfachantwortKarte(
                "Geografie", "Hauptstädte", "Welche sind Hauptstädte?", 
                new String[]{"Paris", "Berlin", "London", "Madrid", "Rom", "Wien"}, null);
       
       
        MehrfachantwortKarte karte4 = new MehrfachantwortKarte(
                "Physik", "Größen", "Welche sind SI-Einheiten?", 
                new String[]{"Meter", "Sekunde"}, null);
        
        MehrfachantwortKarte karte5 = new MehrfachantwortKarte(null,null,null,null,null);
        
        
      
        new EinzelantwortErfassungView(primaryStage, karte1);
        new EinzelantwortErfassungView(primaryStage, karte2);
        new MehrfachantwortKarteErfassungView(primaryStage, karte3);
        new MehrfachantwortKarteErfassungView(primaryStage, karte4);
        new MehrfachantwortKarteErfassungView(primaryStage, karte5); 
        
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
