package pk;

public class EinzelantwortKarte extends Lernkarte {

    private String antwort;


    public EinzelantwortKarte(String kategorie, String titel, String frage,String antwort) {
        super(kategorie, titel, frage);
        this.antwort = antwort;
    }

    @Override
    public void zeigeVorderseite() {
        System.out.println("[" + getId() + ", " + getKategorie() + "] " + getTitel() + ":");
        System.out.println(getFrage());
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










}
