package pk.lkarten.ui;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pk.Ikarten.Lernkarte;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public abstract class Erfassungview extends Stage {

	 protected  Lernkarte karte;
	 
	 public Erfassungview(Stage owner, Lernkarte karte) {
	        this.karte = karte;
	        this.initOwner(owner);
	        this.initModality(Modality.WINDOW_MODAL);
	    }

	 
	 
	public void showView ()  {
	 this.showAndWait();
	
	}
	
	
}
