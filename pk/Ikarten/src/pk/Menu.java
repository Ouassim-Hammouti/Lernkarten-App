package pk;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class Menu {
    
private static  Lernkartei kartei = new Lernkartei();
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
            System.out.println("5. Beenden");
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
            case 5 -> running = false;
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
