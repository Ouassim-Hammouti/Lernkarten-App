package pk.lkarten.ui;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pk.Ikarten.EinzelantwortKarte;
import pk.Ikarten.Lernkarte;
import pk.Ikarten.Lernkartei;
import pk.Ikarten.MehrfachantwortKarte;
import pk.Ikarten.db.Database;
import pk.Ikarten.db.LernkartenDao;
import pk.lkarten.ui.util.DialogUtil;

	
	

	public class LernkartenApp extends Application {
	
	
	
	private ObservableList<Lernkarte> observableKarten;  
	private ListView<Lernkarte> karteList; 
	private Lernkartei lernkartei;
	private LernkartenDao dao;
	
    @Override 
    public void start(Stage primaryStage) throws Exception {
    	
    	
    	try {
    		
    		 Database.getInstance().getConnection(); 
    		 
    		 
    		 	 dao = new LernkartenDao( Database.getInstance().getConnection());
    		    lernkartei = new Lernkartei(dao);
    		    
    	}
    	
    	catch(SQLException e){
    		DialogUtil.showErrorDialog("Fehlermeldung", 
                    "Fehler beim Schließen der Datenbankverbindung: " + e.getMessage());
    	}
    	
    	
    	
       EinzelantwortKarte karte1 = new EinzelantwortKarte("Mathe", "Addition", "Was ist 2+2?", "4");
        
       EinzelantwortKarte karte2 = new EinzelantwortKarte(null,null,null,null);
       
       MehrfachantwortKarte karte3 = new MehrfachantwortKarte(
                "Geografie", "Hauptstädte", "Welche sind Hauptstädte?", 
                new String[]{"Paris", "Berlin", "London", "Madrid", "Rom", "Wien"}, null);
       
       
        MehrfachantwortKarte karte4 = new MehrfachantwortKarte(
                "Physik", "Größen", "Welche sind SI-Einheiten?", 
                new String[]{"Meter", "Sekunde"}, null);
        
        MehrfachantwortKarte karte5 = new MehrfachantwortKarte(null,null,null,null,null);
        
        
      
        new EinzelantwortErfassungView(primaryStage, karte1,lernkartei);
        new EinzelantwortErfassungView(primaryStage, karte2,lernkartei);
        new MehrfachantwortKarteErfassungView(primaryStage, karte3,lernkartei);
        new MehrfachantwortKarteErfassungView(primaryStage, karte4,lernkartei);
        new MehrfachantwortKarteErfassungView(primaryStage, karte5,lernkartei); 
         
        
        
        MenuBar mb = new MenuBar();
        Menu Datei = new Menu("Datei");
        Menu Lernkartei = new Menu ("Lernkartei");
        
        mb.getMenus().addAll(Datei,Lernkartei);

    
        MenuItem CSVExport = new MenuItem("CSV-Export");
        
        CSVExport.setOnAction(event-> {
        	
        	
        	FileChooser chooser = new FileChooser();  
            chooser.setTitle("Lernkartei als CSV speichern");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV-Dateien", "*.csv"));
            
        	try{
        		
        		
        		File file = chooser.showSaveDialog(primaryStage);  
            	
            	if (file!=null) {
            		
            		
            		
        		lernkartei.exportiereEintraegeAlsCsv(file.toPath()); 
        		
        		DialogUtil.showMessageDialog("CSV-Export","CSV-Export erflogreich");

            	}
        	}
        	
        	catch(Exception e){
        		DialogUtil.showErrorDialog("Fehlermeldung","Fehler beim Export der Datei");
        		e.getMessage();
        	}
        });
        
        MenuItem Beenden = new MenuItem("Beenden");
        
        
        Beenden.setOnAction(event -> primaryStage.close());

        

        
        MenuItem einzelhin = new MenuItem("Einzelantwortkarte hinzufügen");
        
        einzelhin.setOnAction(event->{
        	
        	new EinzelantwortErfassungView(primaryStage,null,lernkartei);
        	 ladeKartenNeu();
        	
        });
       
        MenuItem mehrhin = new MenuItem("Mehrfachantwortkarte hinzufügen");
        
        	mehrhin.setOnAction(event->{
        	
        	new MehrfachantwortKarteErfassungView(primaryStage,null,lernkartei);
        	 ladeKartenNeu();
        	
        });
        	
        	
        	
        
        Datei.getItems().add(CSVExport);
        Datei.getItems().add(new SeparatorMenuItem());
        Datei.getItems().add(Beenden);
        
        Lernkartei.getItems().addAll(einzelhin,mehrhin);
        
        BorderPane root = new BorderPane();
        root.setTop(mb);

        
         karteList = new ListView<>();
        
        
        try {
        	
        	
            List<Lernkarte> karten = dao.findAll();
            
            observableKarten = FXCollections.observableArrayList(karten);
            karteList.setItems(observableKarten);
            
           
            
            
        }
        catch(SQLException e) {
        	
        	DialogUtil.showErrorDialog("Datenbankfehler",e.getMessage());
        }
        
        
        
        var lernen = new Button ("Lernen!");
        Spinner<Integer> spinner = new Spinner<>(5,15,5);
        		 
        
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(lernen, spinner);
         
        
        lernen.setOnAction(event->{
        	
        int	anzahl = spinner.getValue();
        	
       Lernkarte[] randomdeck = new Lernkarte[anzahl];
        
       String ruekseitezeigen = "Rückseite zeigen ";
      
        	try {
        		
				randomdeck = lernkartei.erzeugeDeck(anzahl);
				
				for ( Lernkarte karte : randomdeck) {
					 
					DialogUtil.showTextDialog(karte.getTitel(),karte.getKategorie(),karte.gibVorderseite(),"Rückseite zeigen");
				
					
					DialogUtil.showTextDialog(karte.getTitel(),karte.getKategorie(),karte.gibRueckseite(),"Nächste Karte zeigen ");
					
					
				}
				
			} catch (SQLException e) {
				DialogUtil.showErrorDialog("Datenbankfehler",e.getMessage());
				e.printStackTrace();
			}
        	
        	
        });
        
        
        
        
        
        
        
        
        
        root.setBottom(bottomBox);
        root.setCenter(karteList);
        
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lernkarten-App");
        primaryStage.show();
        
    
    
      
    }
    
    
    
    @Override
    public void stop() {
        try {
            Database.getInstance().close();
        } catch (SQLException exp) {
            DialogUtil.showErrorDialog("Fehlermeldung",
                    "Fehler beim Schließen der Datenbankverbindung: " + exp.getMessage());
        }
    }

    private void ladeKartenNeu() {
        try {
            observableKarten.clear();
            List<Lernkarte> karten = dao.findAll();
            observableKarten.addAll(karten);
        } catch (SQLException e) {
            DialogUtil.showErrorDialog("Datenbankfehler", e.getMessage());
        }
    }

    
    public static void main(String[] args) {
        launch(args);

    }
}
