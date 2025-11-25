package eglv.sistemagerenciamentoacervos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    public static final String URL = "jdbc:mysql://localhost:3306/bdmuseueglv?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASS = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}