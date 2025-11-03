package pk;
import java.util.Random;
;


public class Lernkartei  {

    private Lernkarte[] karten;
    private int anzahlkarten;
    
    public Lernkartei(int maxKarten) {
        karten = new Lernkarte[maxKarten];
        anzahlkarten = 0;
    }

    public void hinzufuegen(Lernkarte karte){
        if(anzahlkarten<karten.length){
            karten[anzahlkarten]=karte;
            anzahlkarten++;
        }
        else{
            System.out.println("Maximale Anzahl an Karten erreicht.");
        }
    }

    public void druckeAlleKarten(){
        if(anzahlkarten==0){
            System.out.println("Keine Karten vorhanden");
            return;
        }

        for(int i = 0 ; i < karten.length ; i++){
            karten[i].druckeKarte();
            System.out.println();
        }
    }

    public int gibAnzahlkarten(){
        return anzahlkarten;
    }

public Lernkarte[] gibKartenZuKategorie (String kategorie){
    int zahler = 0 ;


    for(int i = 0;i<anzahlkarten;i++){
        if (karten[i].getKategorie().equals(kategorie)){ // Warum fehlermeldung für kategorie?
            zahler++;
        }
    }
Lernkarte[] ergebnis = new Lernkarte[zahler];
        int index = 0;
        for (int i = 0; i < anzahlkarten; i++) {
            if (karten[i].getKategorie().equalsIgnoreCase(kategorie)) { // Warum fehlermeldung für kategorie?
                ergebnis[index] = karten[i];
                index++;
            }
        }

        return ergebnis; 

}

 public Lernkarte[] erzeugeDeck(int anzahlKartenImDeck) {
        if (anzahlkarten == 0) {
            System.out.println("Keine Lernkarten in der Kartei vorhanden.");
            return new Lernkarte[0];
        }

        Random zufall = new Random();
        Lernkarte[] deck = new Lernkarte[anzahlKartenImDeck];

        for (int i = 0; i < anzahlKartenImDeck; i++) {
            int zufallsIndex = zufall.nextInt(anzahlkarten); 
            deck[i] = karten[zufallsIndex]; 
        }

        return deck;
    }















}
