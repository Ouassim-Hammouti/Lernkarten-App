package pk;



public class Ausgaben {

    public static void main(String[] args) {
        Lernkartei kartei = new Lernkartei(5);

        Lernkarte karte1 = new Lernkarte("OOP", "Konstruktor", "Was ist ein Konstruktor?", 
                                         "Ein Konstruktor initialisiert ein neues Objekt.");
        Lernkarte karte2 = new Lernkarte("OOP", "Vererbung", "Was ist Vererbung?", 
                                         "Eine Klasse kann Eigenschaften einer anderen übernehmen.");
        Lernkarte karte3 = new Lernkarte("SQL", "SELECT", "Was macht SELECT?", 
                                         "SELECT wählt Daten aus einer Tabelle aus.");

        kartei.hinzufügen(karte1);
        kartei.hinzufügen(karte2);
        kartei.hinzufügen(karte3);

        System.out.println("--- Alle Karten in der Kartei ---");
        kartei.druckeAlleKarten();

        System.out.println("--- Anzahl der Karten ---");
        System.out.println("Aktuell in der Kartei: " + kartei.gibAnzahlkarten());

        System.out.println("--- Karten zu Kategorie 'OOP' ---");
        Lernkarte[] oopKarten = kartei.gibKartenZuKategorie("OOP");
        for (Lernkarte k : oopKarten) {
            k.druckeKarte();
            System.out.println();
        }

        System.out.println("--- Zufälliges Deck (3 Karten) ---");
        Lernkarte[] deck = kartei.erzeugeDeck(3);
        for (Lernkarte k : deck) {
            k.druckeKarte();
            System.out.println();
        }

        System.out.println("--- Test: Kartei ist voll ---");
        kartei.hinzufügen(new Lernkarte("Java", "Datentypen", "Was ist ein Datentyp?", "Definiert, welche Werte eine Variable haben kann."));
        kartei.hinzufügen(new Lernkarte("Java", "Schleifen", "Was ist eine Schleife?", "Eine wiederholende Kontrollstruktur."));
        kartei.hinzufügen(new Lernkarte("Java", "If-Else", "Was macht eine If-Abfrage?", "Überprüft Bedingungen."));
    }
}

