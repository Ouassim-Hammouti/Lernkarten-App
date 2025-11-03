package pk;

public class MehrfachantwortKarte extends Lernkarte {

    private String[] moeglicheAntworten;
    private int [] richtigeAntwort;
   

public MehrfachantwortKarte( String kategorie, String titel, String frage , String[] moeglicheAntworten, int[] richtigeAntwort) {
   super(kategorie,titel,frage);
    this.moeglicheAntworten = moeglicheAntworten;
    this.richtigeAntwort = richtigeAntwort;
    
}


    @Override
    public void zeigeVorderseite() {
    System.out.println("[" + getId() + ", " + getKategorie() + "] " + getTitel() + ":");
    for(int i = 0; i<moeglicheAntworten.length; i++) {
        System.out.println((i) + ": " + moeglicheAntworten[i]);
    }



}



 @Override 
    public void zeigeRueckseite() {
     System.out.println("Die richtigen Antworten sind:");
    for (int i = 0; i < richtigeAntwort.length; i++) {
    int j = richtigeAntwort[i];
    System.out.println(j + ": " + moeglicheAntworten[j]);
}

    }



    @Override
    public void druckeKarte() {
        zeigeVorderseite();
        zeigeRueckseite();
    }





}