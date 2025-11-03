package pk;



public class Ausgaben {

    public static void main(String[] args) {
        Lernkartei kartei = new Lernkartei(5);

        Lernkarte karte1 = new EinzelantwortKarte("OOP", "Konstruktor", "Was ist ein Konstruktor?", 
                                         "Ein Konstruktor initialisiert ein neues Objekt.");
        Lernkarte karte2 = new EinzelantwortKarte("OOP", "Vererbung", "Was ist Vererbung?", 
                                         "Eine Klasse kann Eigenschaften einer anderen übernehmen.");
        Lernkarte karte3 = new EinzelantwortKarte("SQL", "SELECT", "Was macht SELECT?", 
                                         "SELECT wählt Daten aus einer Tabelle aus.");

        kartei.hinzufuegen(karte1);
        kartei.hinzufuegen(karte2);
        kartei.hinzufuegen(karte3);

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
        kartei.hinzufuegen(new EinzelantwortKarte("Java", "Datentypen", "Was ist ein Datentyp?", "Definiert, welche Werte eine Variable haben kann."));
        kartei.hinzufuegen(new EinzelantwortKarte("Java", "Schleifen", "Was ist eine Schleife?", "Eine wiederholende Kontrollstruktur."));
        kartei.hinzufuegen(new EinzelantwortKarte("Java", "If-Else", "Was macht eine If-Abfrage?", "Überprüft Bedingungen."));
    }
}

