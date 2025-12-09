package pk.lkarten.db;

import pk.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LernkartenDao {

    private final Connection conn;

    public LernkartenDao() throws SQLException {
        this.conn = Database.getInstance().getConnection();
    }

    public void addEinzelantwortKarte(EinzelantwortKarte karte) throws SQLException, DoppelteKarteException {
        String sql = "INSERT INTO lernkarte (kategorie, titel, frage, typ, antwort) VALUES (?, ?, ?, 'E', ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, karte.getKategorie());
            ps.setString(2, karte.getTitel());
            ps.setString(3, karte.getFrage());
            ps.setString(4, karte.getAntwort());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    karte.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                throw new DoppelteKarteException();
            } else {
                throw e;
            }
        }
    }

    public void addMehrfachantwortKarte(MehrfachantwortKarte karte) throws SQLException, DoppelteKarteException {
        String sqlKarte = "INSERT INTO lernkarte (kategorie, titel, frage, typ) VALUES (?, ?, ?, 'M')";
        try (PreparedStatement ps = conn.prepareStatement(sqlKarte, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, karte.getKategorie());
            ps.setString(2, karte.getTitel());
            ps.setString(3, karte.getFrage());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    // Note: We cannot set the ID on MehrfachantwortKarte since there's no setter
                    // This is a limitation of the current class design
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                throw new DoppelteKarteException();
            } else {
                throw e;
            }
        }

        String sqlAntwort = "INSERT INTO mehrfachantwort (lernkarte_id, antwort_index, antwort_text, ist_richtig) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlAntwort)) {
            String[] antworten = karte.getMoeglicheAntworten();
            int[] richtigeAntworten = karte.getRichtigeAntworten();
            
            for (int i = 0; i < antworten.length; i++) {
                ps.setInt(1, karte.getId());
                ps.setInt(2, i);
                ps.setString(3, antworten[i]);
                
                boolean isRichtig = false;
                for (int richtig : richtigeAntworten) {
                    if (richtig == i) {
                        isRichtig = true;
                        break;
                    }
                }
                ps.setInt(4, isRichtig ? 1 : 0);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public List<Lernkarte> getAlleKarten() throws SQLException {
        List<Lernkarte> result = new ArrayList<>();
        String sql = "SELECT * FROM lernkarte";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String typ = rs.getString("typ");
                if ("E".equals(typ)) {
                    result.add(mapEinzelantwortKarte(rs));
                } else {
                    result.add(mapMehrfachantwortKarte(rs));
                }
            }
        }
        return result;
    }

    private EinzelantwortKarte mapEinzelantwortKarte(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String kategorie = rs.getString("kategorie");
        String titel = rs.getString("titel");
        String frage = rs.getString("frage");
        String antwort = rs.getString("antwort");
        return new EinzelantwortKarte(id, kategorie, titel, frage, antwort);
    }

    private MehrfachantwortKarte mapMehrfachantwortKarte(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String kategorie = rs.getString("kategorie");
        String titel = rs.getString("titel");
        String frage = rs.getString("frage");

        String sql = "SELECT antwort_index, antwort_text, ist_richtig FROM mehrfachantwort WHERE lernkarte_id=? ORDER BY antwort_index";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs2 = ps.executeQuery()) {
                java.util.List<String> antworten = new java.util.ArrayList<>();
                java.util.List<Integer> richtigIndexe = new java.util.ArrayList<>();
                
                while (rs2.next()) {
                    antworten.add(rs2.getString("antwort_text"));
                    if (rs2.getInt("ist_richtig") == 1) {
                        richtigIndexe.add(antworten.size() - 1);
                    }
                }
                
                String[] moeglicheAntworten = antworten.toArray(new String[0]);
                int[] richtigeAntworten = richtigIndexe.stream().mapToInt(i -> i).toArray();
                
                return new MehrfachantwortKarte(id, kategorie, titel, frage, moeglicheAntworten, richtigeAntworten);
            }
        }
    }
}
