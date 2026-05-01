package BD;

import java.sql.*;

/**
 *
 * @author roi conles ferro
 */
public class LogBD {
    public void insertarLog(int idUsuario, String tipoAccion, String modulo, String descripcion){
        String sql = "INSERT INTO Log_actividad (id_usuario, tipo_accion, modulo, fecha_hora, descripcion) VALUES (?,?,?, NOW(), ?)";
        
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, idUsuario);
            ps.setString(2, tipoAccion);
            ps.setString(3, modulo);
            ps.setString(4, descripcion);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.getLogger(LogBD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
