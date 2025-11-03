package pk;

public abstract class Lernkarte {
    
    private static int naechsteId = 1;

    
    private int id;
    private String kategorie;
    private String titel;
    private String frage;
    

    
    public Lernkarte(String kategorie, String titel, String frage) {
        this.id = naechsteId;     
        naechsteId++;            
        this.kategorie = kategorie;
        this.titel = titel;
        this.frage = frage;
       
    }

   
    public int getId() {
        return id;
    }

    public String getKategorie() {
        return kategorie;
    }

    public String getTitel() {
        return titel;
    }

    public String getFrage() {
        return frage;
    }

   


    public void zeigeVorderseite() {
        System.out.println("[" + id + ", " + kategorie + "] " + titel + ":");
        System.out.println(frage);
    }

    public abstract void zeigeRueckseite()   ;

       
    

    public void druckeKarte() {
        zeigeVorderseite();
        zeigeRueckseite();
    }
}
