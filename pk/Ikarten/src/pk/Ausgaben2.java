package pk;

public class Ausgaben2 {
    
public static void main(String[] args) {

LernkarteiArray kartei = new LernkarteiArray(5);

Lernkarte Einzelkarte1 = new EinzelantwortKarte ("Mathe", "Pythagoras", "Was besagt der Satz des Pythagoras?", "In einem rechtwinkligen Dreieck ist die Summe der Quadrate der Katheten gleich dem Quadrat der Hypotenuse.");
Lernkarte Einzelkarte2 = new EinzelantwortKarte ("Geschichte", "WWII", "Wann begann der Zweite Weltkrieg?", "1939");
Lernkarte Mehrfachkarte1 = new MehrfachantwortKarte ("IT", "Programmiersprachen", "Welche der folgenden sind Programmiersprachen?", new String[] {"HDMI", "Python", "CCS", "Java"}, new int[] {1,3} );
Lernkarte Mehrfachkarte2 = new MehrfachantwortKarte ("Biologie", "Zellorganellen", "Welche der folgenden sind Tiere", new String[] {"Elefant", "Dorn", "Apfel", "Affe"}, new int[] {0,3} );


kartei.hinzufuegen(Einzelkarte1);
kartei.hinzufuegen(Einzelkarte2);
kartei.hinzufuegen(Mehrfachkarte1);
kartei.hinzufuegen(Mehrfachkarte2); 
    
 
    
  System.out.println("--- Anzahl der Karten ---");
        System.out.println("Aktuell in der Kartei: " + kartei.gibAnzahlkarten());


 System.out.println("--- Zufälliges Deck (3 Karten) ---");
        Lernkarte[] deck = kartei.erzeugeDeck(3);
        for (Lernkarte k : deck) {
            k.druckeKarte();
            System.out.println();
        }


  System.out.println("--- Alle Karten in der Kartei ---");
        kartei.druckeAlleKarten();


 System.out.println("--- Karten zu Kategorie 'IT' ---");
        Lernkarte[] ITKarten = kartei.gibKartenZuKategorie("IT");
        for (Lernkarte k : ITKarten) {
            k.druckeKarte();
            System.out.println();
        }

        System.out.println("--- Zufälliges Deck (3 Karten) ---");
         deck = kartei.erzeugeDeck(3);
        for (Lernkarte k : deck) {
            k.druckeKarte();
            System.out.println();
        }




}









}
