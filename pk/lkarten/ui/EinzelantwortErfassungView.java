package pk.lkarten.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pk.Ikarten.EinzelantwortKarte;
import pk.Ikarten.Lernkarte;

public class EinzelantwortErfassungView extends Erfassungview{
	private TextField kat;
    private TextField titel;
    private TextField fra;
    private TextArea ant;
    
	public EinzelantwortErfassungView(Stage owner, Lernkarte karte) {
		super(owner, karte);
		
		this.setTitle("Erfassung einer Einzelantwortkarte");

		
		aufbauenGUI();
	
		if (karte!=null) {
			kat.setText(karte.getKategorie());
			titel.setText(karte.getTitel());
			fra.setText(karte.getFrage());
			ant.setText(((EinzelantwortKarte) karte).getAntwort());
		}
		
		showView();
		
	}


	
	
	@Override
	public void showView()  {
	this.showAndWait();
	
	}

	protected void aufbauenGUI() {
		
		var grid = new GridPane();
		grid.setPadding(new Insets(10.0));
		grid.setHgap(5.0);
		grid.setVgap(5.0);
		
		 kat = new TextField();
		 titel = new TextField();
		 fra = new TextField ();
		 ant = new TextArea();
		
		Button OK = new Button("OK");
		Button Abbrechen = new Button("Abbrechen");
		
		var kategorie = new Label("Kategorie");
		var Titel= new Label ("Titel:");
		var frage = new Label("Frage:");
		var Antwort = new Label ("Antwort");
		
		grid.add(kategorie, 0, 0);
		grid.add(Titel, 0, 1);
		grid.add(frage, 0, 2);
		grid.add(Antwort,0, 3);
		
		grid.add(kat, 1, 0);
		grid.add(titel, 1, 1);
		grid.add(fra,1 ,2);
		grid.add(ant, 1, 3);
		
		 GridPane buttonGrid = new GridPane();
		 
		    buttonGrid.setHgap(10);
		    buttonGrid.setAlignment(Pos.CENTER);  
		   
		    buttonGrid.add(OK, 0, 0);
		    buttonGrid.add(Abbrechen, 1, 0);
		    
		 
		    grid.add(buttonGrid, 0, 4, 2, 1);
	    
		    Scene scene  =	new Scene(grid, 600, 450);
			this.setScene(scene);
	 }
		 
		 
}

