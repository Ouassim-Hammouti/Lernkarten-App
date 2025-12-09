package pk;

import java.util.Objects;

public abstract class Lernkarte implements Comparable<Lernkarte> , ValidierbareKarte, CsvExportable {
    
    private static int naechsteId = 1;

    
    private int id;
    private String kategorie;
    private String titel;
    private String frage;
    

    
    public Lernkarte(String kategorie, String titel, String frage) {
        this.id = naechsteId;     
        naechsteId++;            
        this.kategorie = kategorie;
        this.titel = titel;
        this.frage = frage;
       
    }
    @Override
     public int compareTo(Lernkarte o) {
        return Integer.compare(o.getId(), this.id);
    }
   
    public int getId() {
        return id;
    }

    public String getKategorie() {
        return kategorie;
    }

    public String getTitel() {
        return titel;
    }

    public String getFrage() {
        return frage;
    }

   


    public void zeigeVorderseite() {
        System.out.println("[" + id + ", " + kategorie + "] " + titel + ":");
        System.out.println(frage);
    }

    public abstract void zeigeRueckseite()   ;

       
    

    public void druckeKarte() {
     
    }

public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null || getClass() != obj.getClass())
        return false;
    Lernkarte other = (Lernkarte) obj;
    return  
           kategorie.equals(other.kategorie) &&
           titel.equals(other.titel) &&
           frage.equals(other.frage);

}

 public int hashCode() {
        return Objects.hash( getKategorie(), getTitel(), getFrage());
    }

    @Override
    public void validiere( ) throws UngueltigeKarteException {
       if (kategorie == null || kategorie.trim().isEmpty()) {
        throw new UngueltigeKarteException("Ungültige Kategorie");
    }
     if (titel == null || titel.trim().isEmpty()) {
        throw new UngueltigeKarteException("Ungültiger Titel");
    }
     if (frage == null || frage.trim().isEmpty()) {
        throw new UngueltigeKarteException("Ungültige Frage");
    }

}

   public String exportiereAlsCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",");
        sb.append(kategorie).append(",");
        sb.append(titel).append(",");
        sb.append(frage);
        sb.append("\n");

        return sb.toString();
    }



}