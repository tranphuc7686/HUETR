package entities;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteApp {
    public final String DB_URL = "jdbc:sqlite:"+getClass().getResource("/database/quanlisv.db").toString();
    public static final String DRIVER = "org.sqlite.JDBC";
    public Connection connectSQLiteApp() throws SQLException {

        Connection connection = null;
        try {
            Class.forName(DRIVER);
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(DB_URL,config.toProperties());
        } catch (SQLException ex) {}
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}