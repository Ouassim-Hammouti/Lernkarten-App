package pk.Ikarten;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import pk.Ikarten.db.Database;
import pk.Ikarten.db.LernkartenDao;

public class Menu {

    private static Lernkartei kartei;  
    private static Scanner scanner = new Scanner(System.in);
 
    public static void main(String[] args) {
         try (Connection connection = Database.getInstance().getConnection()) {

         System.out.println("Datenbankverbindung erfolgreich hergestellt.");
            

          
            LernkartenDao dao = new LernkartenDao(connection);
             kartei = new Lernkartei(dao);

              Menu menu = new Menu();
              menu.anzeigen();
         

        } catch (SQLException e) {
            System.err.println("Fehler bei der Datenbankverbindung: " + e.getMessage());
            e.printStackTrace();
        }

     
    }

    public void anzeigen() {
        boolean running = true;

        while (running) {
            System.out.println("Lernkarte-App");
            System.out.println("1. Lernen!");
            System.out.println("2. Einzelantwortkarte hinzufügen");
            System.out.println("3. Drucke alle Karten");
            System.out.println("4. Drucke Karten zu Kategorie");
            System.out.println("5. CSV-Export");
            System.out.println("6. Beenden");
            System.out.print("Bitte Aktion wählen: ");

            int auswahl;

            try {
                auswahl = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Ungültige Eingabe. Bitte geben Sie eine Zahl ein.");
                scanner.nextLine();
                continue;
            }

            switch (auswahl) {
                case 1 -> lernen();
                case 2 -> Einzelantwortkartehinzufuegen();
                case 3 -> druckeAlleKarten();
                case 4 -> DruckeKartenZuKategorie();
                case 5 -> CSVExport();
                case 6 -> running = false;
                default -> System.out.println("Ungültige Auswahl. Bitte wählen Sie eine Zahl zwischen 1 und 6.");
            }
        }
    }

    public void lernen() {
        try {
            Lernkarte[] deck = kartei.erzeugeDeck(5);
            for (Lernkarte karte : deck) {
                System.out.print("Frage: ");
                karte.zeigeVorderseite();
                System.out.println("<Drücken Sie Enter, um die Rückseite zu sehen>");
                warteAufEnter();

                System.out.print("Antwort: ");
                karte.zeigeRueckseite();
                System.out.println("<Drücken Sie Enter, um zur nächsten Karte zu gelangen>");
                warteAufEnter();
            }
            System.out.println("Alle Karten betrachtet.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Laden der Karten: " + e.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Einzelantwortkartehinzufuegen() {
        try {
            JOptionPane.showMessageDialog(null, "Einzelantwortkarte hinzufügen:");

            String kategorie = JOptionPane.showInputDialog("Bitte gib deine Kategorie ein:");
            String frage = JOptionPane.showInputDialog("Bitte gib deine Frage ein:");
            String titel = JOptionPane.showInputDialog("Bitte gib deinen Titel ein:");
            String antwort = JOptionPane.showInputDialog("Bitte gib deine Antwort ein:");

            Lernkarte neu = new EinzelantwortKarte(kategorie, titel, frage, antwort);
            kartei.hinzufuegen(neu);

            JOptionPane.showMessageDialog(null, "Karte erfolgreich hinzugefügt.",
                    "Erfolg", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Datenbankfehler: " + e.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (UngueltigeKarteException e) {
            JOptionPane.showMessageDialog(null, "Ungültige Karte: " + e.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (DoppelteKarteException e) {
            JOptionPane.showMessageDialog(null, "Doppelte Karte: " + e.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fehler: " + e.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void druckeAlleKarten() {
        try {
            kartei.druckeAlleKarten();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Laden der Karten: " + e.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void DruckeKartenZuKategorie() {
        try {
            String kategorie = JOptionPane.showInputDialog("Bitte gib deine Kategorie ein:");
            Lernkarte[] ergebnis = kartei.gibKartenZuKategorie(kategorie);

            if (ergebnis.length == 0) {
                JOptionPane.showMessageDialog(null, "Keine Karten zur Kategorie \"" + kategorie + "\" gefunden.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Lernkarte karte : ergebnis) {
                karte.druckeKarte();
                System.out.println();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Laden der Karten: " + e.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void CSVExport() {
        boolean wiederholen = true;
        while (wiederholen) {
            String dateiname = JOptionPane.showInputDialog(null,
                    "Bitte geben Sie den Dateinamen für den CSV-Export ein:",
                    "CSV-Export", JOptionPane.QUESTION_MESSAGE);

            if (dateiname == null || dateiname.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Der Dateiname darf nicht leer sein. Bitte versuchen Sie es erneut.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            Path datei = Path.of(dateiname);

            if (Files.exists(datei)) {
                int antwort = JOptionPane.showConfirmDialog(null,
                        "Die Datei existiert bereits. Möchten Sie sie überschreiben?",
                        "Datei existiert", JOptionPane.YES_NO_OPTION);
                if (antwort == JOptionPane.NO_OPTION) continue;
            }

            try {
                kartei.exportiereEintraegeAlsCsv(datei);
                JOptionPane.showMessageDialog(null,
                        "Die Karten wurden erfolgreich nach " + dateiname + " exportiert.",
                        "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                wiederholen = false;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Datenbankfehler: " + e.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                break;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Exportieren: " + e.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
    }

    private void warteAufEnter() {
        System.out.println("(Drücke Enter zum Fortfahren)");
        scanner.nextLine();
    }
}
