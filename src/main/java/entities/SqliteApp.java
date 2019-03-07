package entities;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SqliteApp {

    public Connection connectSQLiteApp() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enableFullSync(true);
            config.setReadOnly(false);
            SQLiteDataSource sQLiteDataSource = new SQLiteDataSource(config);
            String url = "jdbc:sqlite:" + getClass().getResource("/database/quanlisv.db").toString();
            sQLiteDataSource.setUrl(url);
            conn = sQLiteDataSource.getConnection();

        } catch (ClassNotFoundException se) {
            System.out.println("" + se.getMessage());
        }
        return conn;
    }
}
