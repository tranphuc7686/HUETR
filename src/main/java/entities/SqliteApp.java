package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteApp {
    String hostName = "127.0.0.1";
    String sqlInstanceName = "";
    String database = "QUANLISINHVIEN";
    String userName = "sa";
    String password = "sa";
    public final String DB_URL = "jdbc:sqlserver://" + hostName + ":1433"
            + ";databaseName=" + database;
    public static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public Connection connectSQLiteApp() throws SQLException {

        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL, userName,
                    password);
        } catch (SQLException ex) {}
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}