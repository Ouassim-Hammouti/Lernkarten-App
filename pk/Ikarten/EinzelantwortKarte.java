package pk.Ikarten;

import java.util.Objects;

public class EinzelantwortKarte extends Lernkarte {

    private String antwort;

    public EinzelantwortKarte(String kategorie, String titel, String frage, String antwort) {
        super(kategorie, titel, frage);
        this.antwort = antwort;
    }

    public EinzelantwortKarte(int id, String kategorie, String titel, String frage, String antwort) {
        super(id, kategorie, titel, frage);
        this.antwort = antwort;
    }

    public String getAntwort() {
        return antwort;
    }

    @Override
    public void zeigeVorderseite() {
        System.out.println("[" + getId() + ", " + getKategorie() + "] " + getTitel() + ":");
        System.out.println(getFrage());
    }

    
    
    public  String gibVorderseite() {
    	String Vorderseite = this.getFrage();
        return Vorderseite;
    }
    
    public String gibRueckseite() {
    	String Rueckseite = "Die richtige Antwort ist : "+this.getAntwort();
    	return Rueckseite;
    }
    
    
    @Override
    public void zeigeRueckseite() {
        System.out.println("    " + antwort);
    }

    @Override
    public void druckeKarte() {
        zeigeVorderseite();
        zeigeRueckseite();
    }

    public int hashCode() {
        return Objects.hash(getKategorie(), getTitel(), getFrage(), antwort);
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        EinzelantwortKarte other = (EinzelantwortKarte) obj;
        return Objects.equals(getKategorie(), other.getKategorie()) &&
               Objects.equals(getTitel(), other.getTitel()) &&
               Objects.equals(getFrage(), other.getFrage()) &&
               Objects.equals(antwort, other.antwort);
    }

    public void validiere() throws UngueltigeKarteException {
        super.validiere();
        if (antwort == null || antwort.trim().isEmpty()) {
            throw new UngueltigeKarteException("Ungültige Antwort");
        }
    }

    public String exportiereAlsCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append(getId()).append(",");
        sb.append(getKategorie()).append(",");
        sb.append(getTitel()).append(",");
        sb.append(getFrage()).append(",");
        sb.append(antwort).append(",");
        sb.append("\n");
        return sb.toString();

    }

	@Override
	public String toString() {
		return super.toString() + ",antwort=" + antwort + "]";
	}
    
    
    
    
}