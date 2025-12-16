package pk.Ikarten;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

public class LernkarteiSet  {
    
    private Set<Lernkarte> Liste;

    
    public LernkarteiSet () { 
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
            iterator.next();
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
            
           for (int i = 0; i < anzahlKartenImDeck; i++) {
                 int zufallsIndex = zufall.nextInt(gibAnzahlkarten()); 
            
            deck[i] = karteListe.get(zufallsIndex);
            
            }

       
    

 return deck;
}

  
  public  void exportiereEintraegeAlsCsv(Path datei) throws IOException {

    String csv = "";

    
    csv += "ID,Kategorie,Titel,Frage,Antwort(en),Richtige Antwort(en)\n";

    
    for (Lernkarte karte : Liste) {
        csv += karte.exportiereAlsCsv() + "\n";
    }

   
    Files.writeString(datei, csv);
}



 
public void exportiereEintraegeAlsCsvNio(Path datei) throws IOException {

 try (FileOutputStream fos = new FileOutputStream(datei.toFile());  
         OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
         BufferedWriter writer = new BufferedWriter(osw)) {

      
        writer.write("ID,Kategorie,Titel,Frage,Antwort(en),Richtige Antwort(en)");
        writer.newLine();

      
        for (Lernkarte karte : Liste) {
            writer.write(karte.exportiereAlsCsv());
            writer.newLine();
        }

    } catch (IOException e) {
       
        throw e;
    }
}


}


