package pk.lkarten.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private Connection connection;

    private Database() throws SQLException {
        // SQLite JDBC URL - ändere den Pfad entsprechend
        String url = "jdbc:sqlite:lernkarten.db";
        this.connection = DriverManager.getConnection(url);
    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
