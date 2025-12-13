package pk;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import pk.lkarten.db.LernkartenDao;


public class Lernkartei {


private LernkartenDao dao;

    public Lernkartei(LernkartenDao dao) {
        this.dao = dao;
    }



   public void hinzufuegen(Lernkarte lernkarte) throws SQLException, UngueltigeKarteException, DoppelteKarteException {
        lernkarte.validiere();
        if (lernkarte instanceof EinzelantwortKarte) {
            dao.createEinzelantwortKarte((EinzelantwortKarte) lernkarte);
        } else if (lernkarte instanceof MehrfachantwortKarte) {
            dao.createMehrfachantwortKarte((MehrfachantwortKarte) lernkarte);
        } else {
            throw new IllegalArgumentException("Unbekannter Kartentyp.");
        }
    }
    


    public void druckeAlleKarten() throws SQLException{
        
        List<Lernkarte> Liste = dao.findAll();

        Collections.sort(Liste);

       for(Lernkarte karte : Liste){
           karte.druckeKarte();
           System.out.println();
        }

    }



    public int 
    gibAnzahlkarten()throws SQLException {
        return dao.countLernkarten();
       
    }

    public Lernkarte[] gibKartenZuKategorie (String kategorie)throws SQLException {
        
        List<Lernkarte> Liste = dao.findByKategorie(kategorie);
            
        Lernkarte [] karten = new Lernkarte[Liste.size()];
                for(int i=0; i<Liste.size(); i++){
                karten[i]=Liste.get(i);

            }
        return karten;

           }




 public Lernkarte[] erzeugeDeck (int anzahlKartenImDeck) throws SQLException {

        List<Lernkarte> Liste = dao.findAll();
        if (Liste.isEmpty()) return new Lernkarte[0];
  

        Random zufall = new Random();
        Lernkarte[] deck = new Lernkarte[anzahlKartenImDeck];

        
           for (int i = 0; i < anzahlKartenImDeck; i++) {
                 int zufallsIndex = zufall.nextInt(gibAnzahlkarten()); 
            
            deck[i] = Liste.get(zufallsIndex);
            
            }

         return deck;

    }

    public void exportiereEintraegeAlsCsv(Path datei) throws SQLException, IOException {
        List<Lernkarte> Liste = dao.findAll();
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Kategorie,Titel,Frage,Antwort(en),Richtige Antwort(en)\n");

        for (Lernkarte karte : Liste) {
            csv.append(karte.exportiereAlsCsv()).append("\n");
        }

        Files.writeString(datei, csv.toString());
    }



 
public void exportiereEintraegeAlsCsvNio(Path datei) throws IOException, SQLException {
 List<Lernkarte> Liste =dao.findAll();
 try (FileOutputStream fos = new FileOutputStream(datei.toFile());  
         OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
         BufferedWriter writer = new BufferedWriter(osw)) {

      
        writer.write("ID,Kategorie,Titel,Frage,Antwort(en),Richtige Antwort(en)");
        writer.newLine();

      
        for (Lernkarte karte : Liste) {
            writer.write(karte.exportiereAlsCsv());
            writer.newLine();
        }

    
     }

    }

}
