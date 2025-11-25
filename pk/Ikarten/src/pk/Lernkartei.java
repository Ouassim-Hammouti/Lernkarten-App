package pk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;

public class Lernkartei  {
    
    private Set<Lernkarte> Liste;

    private int anzahlkarten;

    
    public Lernkartei () { 
        Liste = new HashSet<>();
    }

    public void hinzufuegen(Lernkarte lernkarte){
        try {
            lernkarte.validiere();
        } catch (UngueltigeKarteException ex) {
           JOptionPane.showMessageDialog(null, "Fehler beim Hinzufügen der Karte: " + ex.getMessage());
           return;
        }
        Liste.add(lernkarte);
        System.out.println("Karte hinzugefügt.");
    }

    


    public void druckeAlleKarten(){
        
        ArrayList<Lernkarte> Liste = new ArrayList<>(this.Liste);

        Collections.sort(Liste);

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
   
    int count = 0;
    for (Lernkarte karte : Liste) {
        if (karte.getKategorie().equals(kategorie)) { 
            count++;
        }
    }


    Lernkarte[] ergebnis = new Lernkarte[count];
    int i =0;


    for(Lernkarte karte :  Liste ){
        if (karte.getKategorie().equals(kategorie)){
          
               ergebnis[i]=karte;
               i++;
          
        
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

            ArrayList<Lernkarte> karteListe = new ArrayList<>(Liste);
            Iterator<Lernkarte> it = karteListe.iterator();
            
           for (int i = 0; i < anzahlKartenImDeck; i++) {
                 int zufallsIndex = zufall.nextInt(gibAnzahlkarten()); 
            
            deck[i] = karteListe.get(zufallsIndex);
            
            }

       
    

 return deck;
}

  

}