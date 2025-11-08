package pk;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import pk.Lernkarte;

public class Lernkartei {
    
ArrayList <Lernkarte> Liste ;

    private int anzahlkarten;
    
    public Lernkartei () { 
        Liste = new ArrayList<>();
    }

    public void hinzufuegen(Lernkarte Lernkarte){
        Liste.add(Lernkarte);
        System.out.println("Karte hinzugefügt.");
    }

    public void druckeAlleKarten(){
       for(Lernkarte karte : Liste){
           karte.druckeKarte();
           System.out.println();
        }

    }



    public int gibAnzahlkarten(){
        Iterator<Lernkarte> iterator = Liste.iterator();
        int count = 0;

        while(iterator.hasNext()){
            Lernkarte karte = iterator.next();
            count++;
        }
        return count;
    }

public Lernkarte[] gibKartenZuKategorie (String kategorie){
   
    Lernkarte[] ergebnis = new Lernkarte[gibAnzahlkarten()];
    for(Lernkarte karte :  Liste ){
        if (karte.getKategorie().equals(kategorie)){
           for(int i = 0 ; i < ergebnis.length ; i++){
               ergebnis[i]=karte;
          }
        
        }
    
     }

    return ergebnis;

    }



 public Lernkarte[] erzeugeDeck (int anzahlKartenImDeck) {
        if (gibAnzahlkarten() == 0) {
            System.out.println("Keine Lernkarten in der Kartei vorhanden.");
        
        }

        Random zufall = new Random();
        Lernkarte[] deck = new Lernkarte[anzahlKartenImDeck];

       for(Lernkarte karte :  Liste ) {
            int zufallsIndex = zufall.nextInt(gibAnzahlkarten()); 
            for (int i = 0; i < anzahlKartenImDeck; i++) {
                
            
            deck[i] = karte.get(zufallsIndex); // Warum bei get Fehlermeldung?
        }                   //get korrigieren !!

       
    }

 return deck;
}


}