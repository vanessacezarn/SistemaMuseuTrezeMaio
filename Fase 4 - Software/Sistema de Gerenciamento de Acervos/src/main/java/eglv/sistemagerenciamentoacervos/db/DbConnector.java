/* conexão pelo mysql
package eglv.sistemagerenciamentoacervos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    public static final String URL = "jdbc:mysql://localhost:3306/bdmuseueglv?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASS = "0113MA1404A";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

*/
// CONEXÃO PELO SQLSERVER
package eglv.sistemagerenciamentoacervos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

    /*
    public static final String URL ="jdbc:sqlserver://localhost:1433;databaseName=ACERVO_TREZE_MAIO;encrypt=false";
    public static final String USER = "sa";  // ou outro usuário
    public static final String PASS = "0113MA1404A";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
     */
    public static final String URL ="jdbc:sqlserver://localhost:1433;databaseName=ACERVO_TREZE_MAIO;encrypt=false";
    public static final String USER = "luizaa";  // ou outro usuário
    public static final String PASS = "koda";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
