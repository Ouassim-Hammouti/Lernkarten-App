package pk;

public class DoppelteKarteException extends Exception {
    public DoppelteKarteException() {
        super("Diese Karte existiert bereits.");
    }

    public DoppelteKarteException(String message) {
        super(message);
    }
}
