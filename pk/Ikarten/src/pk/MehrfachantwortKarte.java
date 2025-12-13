package pk;

import java.util.Arrays;
import java.util.Objects;

public class MehrfachantwortKarte extends Lernkarte {

    private String[] moeglicheAntworten;
    private int[] richtigeAntwort;

    public MehrfachantwortKarte(String kategorie, String titel, String frage, String[] moeglicheAntworten, int[] richtigeAntwort) {
        super(kategorie, titel, frage);
        this.moeglicheAntworten = moeglicheAntworten;
        this.richtigeAntwort = richtigeAntwort;
    }

    public MehrfachantwortKarte(int id, String kategorie, String titel, String frage, String[] moeglicheAntworten, int[] richtigeAntwort) {
        super(id, kategorie, titel, frage);
        this.moeglicheAntworten = moeglicheAntworten;
        this.richtigeAntwort = richtigeAntwort;
    }

    public String[] getMoeglicheAntworten() {
        return moeglicheAntworten;
    }

    public int[] getRichtigeAntworten() {
        return richtigeAntwort;
    }

    @Override
   public void zeigeVorderseite() {
        super.zeigeVorderseite(); 
        for (int i = 0; i < moeglicheAntworten.length; i++) {
            System.out.println(i + ": " + moeglicheAntworten[i]);
        }
    }

    @Override
    public void zeigeRueckseite() {
        System.out.println("Die richtigen Antworten sind:");
        for (int i = 0; i < richtigeAntwort.length; i++) {
            int j = richtigeAntwort[i];
            System.out.println(j + ": " + moeglicheAntworten[j]);
        }
    }

    @Override
    public void druckeKarte() {
        zeigeVorderseite();
        zeigeRueckseite();
    }

    public int hashCode() {
        return Objects.hash(getKategorie(), getTitel(), getFrage(), moeglicheAntworten, richtigeAntwort);
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        MehrfachantwortKarte other = (MehrfachantwortKarte) obj;
        return super.equals(other);
    }

    public void validiere(Lernkarte karte) throws UngueltigeKarteException {
        super.validiere();
        if (moeglicheAntworten == null || moeglicheAntworten.length < 2) {
            throw new UngueltigeKarteException("Ungültige mögliche Antworten");
        }
        if (richtigeAntwort == null || richtigeAntwort.length == 0) {
            throw new UngueltigeKarteException("Ungültige richtige Antworten");
        }
        for (int index : richtigeAntwort) {
            if (index < 0 || index >= moeglicheAntworten.length) {
                throw new UngueltigeKarteException("Ungültiger Index in den richtigen Antworten");
            }
        }
    }

    public String exportiereAlsCsv() {
        String moeglicheant = Arrays.toString(moeglicheAntworten);
        String richtigeant = Arrays.toString(richtigeAntwort);
        StringBuilder sb = new StringBuilder();
        sb.append(getId()).append(",");
        sb.append(getKategorie()).append(",");
        sb.append(getTitel()).append(",");
        sb.append(getFrage()).append(",");
        sb.append(moeglicheant).append(",");
        sb.append(richtigeant);
        sb.append("\n");
        return sb.toString();
    }
}