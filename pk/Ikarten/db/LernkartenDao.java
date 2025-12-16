package pk.Ikarten.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pk.Ikarten.DoppelteKarteException;
import pk.Ikarten.EinzelantwortKarte;
import pk.Ikarten.Lernkarte;
import pk.Ikarten.MehrfachantwortKarte;







/**
 * Data Access Object (DAO) fuer Lernkarten. Diese Klasse enthaelt Methoden zum
 * Verwalten von Lernkarten in der Datenbank.
 */
public class LernkartenDao {

	private final Connection conn;

	public LernkartenDao(Connection conn) {
		this.conn = conn;
	}

	

public int createEinzelantwortKarte(EinzelantwortKarte karte) throws SQLException, DoppelteKarteException {
    String sql = "INSERT OR IGNORE INTO lernkarte (kategorie, titel, frage, antwort, typ) VALUES (?, ?, ?, ?, ?)";
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        pstmt.setString(1, karte.getKategorie());
        pstmt.setString(2, karte.getTitel());
        pstmt.setString(3, karte.getFrage());
        pstmt.setString(4, karte.getAntwort());
        pstmt.setString(5, "E");
        
        int affectedRows = pstmt.executeUpdate();
        
        
        if (affectedRows == 0) {
            throw new DoppelteKarteException("Die Lernkarte existiert bereits in der Datenbank.");
        }
        
        
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Speichern der Lernkarte fehlgeschlagen, keine ID generiert.");
            }
        }
    }
}

public int createMehrfachantwortKarte(MehrfachantwortKarte karte) throws SQLException, DoppelteKarteException {
    String sql = "INSERT OR IGNORE INTO lernkarte (kategorie, titel, frage, antwort, typ) VALUES (?, ?, ?, ?, ?)";
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        pstmt.setString(1, karte.getKategorie());
        pstmt.setString(2, karte.getTitel());
        pstmt.setString(3, karte.getFrage());
        pstmt.setString(4, null); 
        pstmt.setString(5, "M");
        
        int affectedRows = pstmt.executeUpdate();
        
       
        if (affectedRows == 0) {
            throw new DoppelteKarteException("Die Lernkarte existiert bereits in der Datenbank.");
        }
        
       
        int karteId;
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                karteId = generatedKeys.getInt(1);
                karte.setId(karteId); 
            } else {
                throw new SQLException("Speichern der Lernkarte fehlgeschlagen, keine ID generiert.");
            }
        }
        
        
        createAntwortoptionen(karte);
        
        return karteId;
    }
}

public List<Lernkarte> findAll() throws SQLException {
    String sql = "SELECT id, kategorie, titel, frage, antwort, typ FROM lernkarte";
    List<Lernkarte> lernkarten = new ArrayList<>();
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            String typ = rs.getString("typ");
            
            if ("E".equals(typ)) {
                lernkarten.add(mapEinzelantwortKarte(rs));
            } else if ("M".equals(typ)) {
                lernkarten.add(mapMehrfachantwortKarte(rs));
            }
        }
    }
    
    return lernkarten;
}




public List<Lernkarte> findByKategorie(String kategorie) throws SQLException {
    String sql = "SELECT id, kategorie, titel, frage, antwort, typ FROM lernkarte WHERE kategorie = ?";
    List<Lernkarte> lernkarten = new ArrayList<>();
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, kategorie);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String typ = rs.getString("typ");
                
                if ("E".equals(typ)) {
                    lernkarten.add(mapEinzelantwortKarte(rs));
                } else if ("M".equals(typ)) {
                    lernkarten.add(mapMehrfachantwortKarte(rs));
                }
            }
        }
    }
    
    return lernkarten;
}

	/**
	 * Zaehlt die Anzahl der Lernkarten in der Datenbank.
	 * 
	 * @return Anzahl der Lernkarten
	 * @throws SQLException bei SQL-Fehlern
	 */
	public int countLernkarten() throws SQLException {
		String sql = "SELECT COUNT(*) AS count FROM lernkarte";
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt("count");
			} else {
				throw new SQLException("Count query returned no result.");
			}
		}
	}

	/**
	 * Fuegt die Antwortoptionen der gegebenen Mehrfachantwortkarte in die Datenbank
	 * ein.
	 * 
	 * @param karte Mehrfachantwortkarte mit den einzufuegenden Antwortoptionen
	 * @throws SQLException bei SQL-Fehlern
	 */
	private void createAntwortoptionen(MehrfachantwortKarte karte) throws SQLException {
		String[] moeglicheAntworten = karte.getMoeglicheAntworten();
		int[] richtigeAntworten = karte.getRichtigeAntworten();

		// Insert-Statement fuer Antwortoptionen
		String sql = "INSERT OR IGNORE INTO mehrfachantwort (lernkarte_id, antwort_index, antwort_text, ist_richtig) VALUES (?, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			for (int i = 0; i < moeglicheAntworten.length; i++) {
				ps.setInt(1, karte.getId());
				ps.setInt(2, i);
				ps.setString(3, moeglicheAntworten[i]);
				ps.setBoolean(4, istAntwortRichtig(i, richtigeAntworten));
				// Statements fuer einzelne Antwortoptionen in einem Batch sammeln
				ps.addBatch();
			}
			// Batch ausfuehren
			ps.executeBatch();
		}
	}

	/**
	 * Prueft, ob die Antwort mit dem gegebenen Index zu den richtigen Antworten
	 * gehoert.
	 * 
	 * @param antwortIndex      Index der zu pruefenden Antwort (aus Array
	 *                          "moeglicheAntworten")
	 * @param richtigeAntworten Array mit den Indizes der richtigen Antworten
	 * @return true, wenn die Antwort richtig ist, sonst falseÏ
	 */
	private boolean istAntwortRichtig(int antwortIndex, int[] richtigeAntworten) {
		if (richtigeAntworten == null)
			return false;
		for (int r : richtigeAntworten)
			if (r == antwortIndex)
				return true;
		return false;
	}

	/**
	 * Konvertiert ein ResultSet in eine EinzelantwortKarte.
	 * 
	 * @param rs ResultSet mit den Daten der EinzelantwortKarte
	 * @return EinzelantwortKarte-Objekt
	 * @throws SQLException bei SQL-Fehlern
	 */
	private EinzelantwortKarte mapEinzelantwortKarte(ResultSet rs) throws SQLException {
		return new EinzelantwortKarte(rs.getInt("id"), rs.getString("kategorie"), rs.getString("titel"),
				rs.getString("frage"), rs.getString("antwort"));
	}

	/**
	 * Konvertiert ein ResultSet in eine MehrfachantwortKarte. Da sich die
	 * Antwortoptionen in einer separaten Tabelle befinden, wird eine zweite Abfrage
	 * ausgefuehrt, um diese abzurufen.
	 * 
	 * @param rs ResultSet mit den Grunddatendaten der MehrfachantwortKarte
	 * @return MehrfachantwortKarte-Objekt
	 * @throws SQLException bei SQL-Fehlern
	 */
	private MehrfachantwortKarte mapMehrfachantwortKarte(ResultSet rs) throws SQLException {
		MehrfachantwortKarte karte = null;
		int id = rs.getInt("id");

		// Zweite Abfrage fuer die Antwortoptionen
		String sql = "	SELECT antwort_index, antwort_text, ist_richtig FROM mehrfachantwort	WHERE lernkarte_id = ? ORDER BY antwort_index";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs2 = ps.executeQuery()) {

				List<String> moeglicheAntworten = new ArrayList<>();
				List<Integer> richtigeAntworten = new ArrayList<>();

				while (rs2.next()) {
					int antwortIndex = rs2.getInt("antwort_index");
					String text = rs2.getString("antwort_text");
					boolean istRichtig = rs2.getBoolean("ist_richtig");

					// Dank Sortierung nach antwort_index bleiben die Antworten in der richtigen
					// Reihenfolge
					moeglicheAntworten.add(text);
					if (istRichtig)
						richtigeAntworten.add(antwortIndex);
				}

				// Liste der richtigen Antworten manuell in ein Array umwandeln, da
				// toArray() nur fuer Objekttypen verfuegbar ist
				int[] richtigeArr = new int[richtigeAntworten.size()];
				for (int i = 0; i < richtigeAntworten.size(); i++)
					richtigeArr[i] = richtigeAntworten.get(i);

				karte = new MehrfachantwortKarte(id, rs.getString("kategorie"), rs.getString("titel"),
						rs.getString("frage"), moeglicheAntworten.toArray(new String[0]), richtigeArr);
			}
		}
		return karte;
	}
}
