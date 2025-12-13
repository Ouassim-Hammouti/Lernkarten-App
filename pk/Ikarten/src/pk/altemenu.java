package pk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;



public class altemenu  {
    
private static  LernkarteiSet kartei ;
private static Scanner scanner = new Scanner(System.in);



public static void main(String[] args) {

    
    Menu menu = new Menu();
    menu.anzeigen();

}




    public void anzeigen(){

     boolean running = true;

        while( running){
            System.out.println("Lernkarte-App");
            System.out.println("1. Lernen!");
            System.out.println("2. Einzelantwortkarte hinzufuegen");
            System.out.println("3. Drucke alle Karten");
            System.out.println("4. Drucke Karten zu Kategorie ");
            System.out.println("5. CSV-Export");
            System.out.println("6. Beenden");
            System.out.println("Bitte Aktion wählen: ");


            int auswahl ;
            

            try {
                auswahl = scanner.nextInt();
            scanner.nextLine(); 
            }

            catch (InputMismatchException e) {
            System.out.println("Ungültige Eingabe. Bitte geben Sie eine Zahl ein.");
            scanner.nextLine(); 
            continue;
        }

            switch (auswahl){
            case 1 -> lernen();
            case 2 -> Einzelantwortkartehinzufuegen(); // wie soll ich JOPtion in Menu machen mit einer exception ich hab das in hinzufügen gemacht mit showMessageDialog !
            case 3 -> druckeAlleKarten();
            case 4 -> DruckeKartenZuKategorie();
            case 5 -> {
            String dateiname="";
            boolean wiederholen = true;
            while (wiederholen) {

               dateiname = JOptionPane.showInputDialog(
                null,
                "Bitte geben Sie den Dateinamen für den CSV-Export ein:",
                "CSV-Export",
                JOptionPane.QUESTION_MESSAGE);

            if(dateiname == null || dateiname.trim().isEmpty()){ 
                JOptionPane.showMessageDialog(
                    null,
                    "Der Dateiname darf nicht leer sein. Bitte versuchen Sie es erneut.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE );

                  continue;

            }




            Path datei = Path.of(dateiname);

            if(Files.exists(datei)){

                int antwort = JOptionPane.showConfirmDialog(
                    null,
                    "Die Datei existiert bereits. Möchten Sie sie überschreiben?",
                    "Datei existiert",
                    JOptionPane.YES_NO_OPTION
                );
                if (antwort==JOptionPane.NO_OPTION){
                    continue;
                }


            }
                
                try{
                    kartei.exportiereEintraegeAlsCsv(datei);
                    JOptionPane.showMessageDialog(
                        null,
                        "Die Karten wurden erfolgreich nach " + dateiname + " exportiert.",
                        "Erfolg",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    wiederholen = false;
                }
                catch ( IOException e){
                    JOptionPane.showMessageDialog(
                        null,
                        "Fehler beim Exportieren der Datei: " + e.getMessage(),
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE
                    ); 
                    break;
                 }

            

        }


    }    
        case 6 -> running = false;
            default -> System.out.println("Ungültige Auswahl. Bitte wählen Sie eine Zahl zwischen 1 und 5.");
            }



        }





    } 

    public void lernen(){  
        
        Lernkarte [] deck = kartei.erzeugeDeck(5);
         for(Lernkarte karte : deck){
            System.out.print("Frage: " ); karte.zeigeVorderseite();
            System.out.println("<Drücken Sie Enter, um die Rückseite zu sehen>");
            warteAufEnter();

            System.out.print("Antwort: " ); karte.zeigeRueckseite();
            System.out.println("<Drücken Sie Enter, um zur nächsten Karte zu gelangen>");
            warteAufEnter();
         }


        System.out.println("Alle Karten betrachtet");

         
    }
   public void Einzelantwortkartehinzufuegen(){

    JOptionPane.showMessageDialog(null, "Einzelantwortkarte hinzufügen:");
    String kategorie = JOptionPane.showInputDialog("Bitte gib deine kategorie ein :");
    String frage = JOptionPane.showInputDialog("Bitte gib dein frage ein :");
    String titel = JOptionPane.showInputDialog("Bitte gib deine titel ein :");
    String antwort = JOptionPane.showInputDialog("Bitte gib deine antwort ein :");

    Lernkarte neu = new EinzelantwortKarte(kategorie, titel,frage, antwort);
    kartei.hinzufuegen(neu);

    

   }


    public void druckeAlleKarten(){
        kartei.druckeAlleKarten();
    }

    public void DruckeKartenZuKategorie(){
        String kategorie = JOptionPane.showInputDialog("Bitte gib deine kategorie ein :");

        Lernkarte [] ergebnis = kartei.gibKartenZuKategorie(kategorie);

        if (ergebnis.length == 0) {
        System.out.println("Keine Karten zur Kategorie \"" + kategorie + "\" gefunden.");
        return;
    }

        for(Lernkarte karte : ergebnis ){
            karte.druckeKarte();
            System.out.println();
        }
    }





private void warteAufEnter() {
    System.out.println("(Drücke Enter zum Fortfahren)");
    scanner.nextLine();
}



}

