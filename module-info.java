module pk {
	
		requires java.desktop;
	    requires java.sql;
	    requires javafx.controls;
	    requires javafx.graphics;
		requires javafx.base;
	    opens pk.lkarten.ui to javafx.graphics;
	    
}
