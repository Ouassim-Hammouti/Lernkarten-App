package pk.lkarten.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pk.Ikarten.Lernkarte;
import pk.Ikarten.MehrfachantwortKarte;

public class MehrfachantwortKarteErfassungView extends Erfassungview{

	TextField kat;
	TextField titel;
	TextArea Frage;
	
	private TextArea[] antwortFelder = new TextArea[5];
    private CheckBox[] richtigBoxen = new CheckBox[5];
	
    
	public MehrfachantwortKarteErfassungView(Stage owner, Lernkarte karte) {
		super(owner, karte);
	
		this.setTitle("Erfassung einer Mehrfachantwortkarte");
		
		aufbauenGUI();
		
		if (karte != null && karte instanceof MehrfachantwortKarte) {
			kat.setText(karte.getKategorie());
			titel.setText(karte.getTitel());
			Frage.setText(karte.getFrage());
			
			MehrfachantwortKarte mak = (MehrfachantwortKarte) karte;
		    String[] antworten = mak.getMoeglicheAntworten();

		    for (int i = 0; i < antwortFelder.length; i++) {
		        if (antworten != null && i < antworten.length) {
		            antwortFelder[i].setText(antworten[i]); 
		        } else {
		            antwortFelder[i].setText("");
		        }
		    }
			
			
		}
		showView();
	}

	@Override
	public void showView () {
	
		this.showAndWait();
		
	}

	
	public void aufbauenGUI() {
		
		var grid = new GridPane();
		grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        
        var kategorie = new Label("Kategorie");
		var Titel= new Label ("Titel:");
		var frage = new Label("Frage:");
		
		
		 kat = new TextField();
		 titel = new TextField();
		Frage = new TextArea();
		Frage.setPrefRowCount(2);
		
		
		grid.add(kategorie, 0, 0);
		grid.add(Titel, 0, 1);
		grid.add(frage, 0, 2);
		
		
		grid.add(kat, 1, 0);
		grid.add(titel, 1, 1);
		grid.add(Frage,1 ,2);
		
		var buttonGrid =new GridPane();
		 var OK = new Button("OK");
		 var Abbrechen = new Button("Abbrechen");
		   
		buttonGrid.setHgap(10);
	    buttonGrid.setAlignment(Pos.CENTER);
	    
	    buttonGrid.add(OK, 0, 0);
	    buttonGrid.add(Abbrechen, 1, 0);
	   
	   
	    
		for(int i  = 0 ; i < 5; i ++) {
			
			antwortFelder[i] = new TextArea();
			richtigBoxen[i]= new CheckBox("Richtig ?");
			 antwortFelder[i].setPrefRowCount(2);
			    antwortFelder[i].setPrefWidth(250);
			
			HBox hbox = new HBox(10, antwortFelder[i], richtigBoxen[i]);
		    hbox.setAlignment(Pos.CENTER_LEFT);
	    
		
		    grid.add(new Label("Antwort " + (i + 1) + ":"), 0, 3 + i);

		    grid.add(hbox, 1, i+3);
			
		}
		
	
		grid.add(buttonGrid, 0, 8,2,1);
		
		
		Scene scene  =	new javafx.scene.Scene(grid, 600, 450);
		this.setScene(scene);
		
		
	}
	

}
