package pk;

public class Lernkarte {
    
    private static int naechsteId = 1;

    
    private int id;
    private String kategorie;
    private String titel;
    private String frage;
    private String antwort;

    
    public Lernkarte(String kategorie, String titel, String frage, String antwort) {
        this.id = naechsteId;     
        naechsteId++;            
        this.kategorie = kategorie;
        this.titel = titel;
        this.frage = frage;
        this.antwort = antwort;
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

    public String getAntwort() {
        return antwort;
    }


    public void zeigeVorderseite() {
        System.out.println("[" + id + ", " + kategorie + "] " + titel + ":");
        System.out.println(frage);
    }

    public void zeigeRueckseite() {
        System.out.println("    " + antwort);
    }

    public void druckeKarte() {
        zeigeVorderseite();
        zeigeRueckseite();
    }
}
