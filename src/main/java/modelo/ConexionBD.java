package modelo;

import java.sql.*;
/**
 *
 * @author alumno
 */
public class ConexionBD {
    public static Connection conexion = null;
    
    public static void abrirConexion(){
        try{
            String url = "jdbc:mariadb://localhost:3306/rolepolicemanager";
            conexion = DriverManager.getConnection(url,"root","abc123.");
            System.out.println("Conexion establecida");
        } catch (SQLException e){
            e.getMessage();
        }
    }
}
