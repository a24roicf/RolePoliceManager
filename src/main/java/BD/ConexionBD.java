package BD;

import java.sql.*;
/**
 *
 * @author alumno
 */
public class ConexionBD {
    
    public static Connection conectar() {
        try {
            String url = "jdbc:mariadb://localhost:3306/rolepolicemanager";
            return DriverManager.getConnection(url, "root", "abc123.");
        } catch (SQLException e) {
            return null;
        }
    }
}
