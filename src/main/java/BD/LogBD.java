package BD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import modelo.Log;

/**
 *
 * @author roi conles ferro
 */
public class LogBD {

    public void insertarLog(int idUsuario, String tipoAccion, String modulo, String descripcion) {
        String sql = "INSERT INTO Log_actividad (id_usuario, tipo_accion, modulo, fecha_hora, descripcion) VALUES (?,?,?, NOW(), ?)";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, tipoAccion);
            ps.setString(3, modulo);
            ps.setString(4, descripcion);

            ps.executeUpdate();

        } catch (SQLException ex) {
            System.getLogger(LogBD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    //Colocar los paquetes de Date literalmente en los parametros dado que si no colocas nada se pone el Date de sql
    public List<Log> obtenerLogsFiltrados(String texto, java.util.Date desde, java.util.Date hasta) {

        List<Log> lista = new ArrayList<>();

        String sql = "SELECT * FROM Log_actividad WHERE 1=1"; //Condicion para poder concatenar fechas si no pones texto

        if (texto != null && !texto.isEmpty()) {
            sql += " AND (descripcion LIKE ? OR modulo LIKE ?)";
        }

        if (desde != null) {
            sql += " AND fecha_hora >= ?";
        }

        if (hasta != null) {
            sql += " AND fecha_hora <= ?";
        }

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;

            if (texto != null && !texto.isEmpty()) {
                ps.setString(i++, "%" + texto + "%");
                ps.setString(i++, "%" + texto + "%");
            }
            //Convertir la fecha date a sql
            if (desde != null) {
                ps.setTimestamp(i++, new Timestamp(desde.getTime()));
            }

            if (hasta != null) {
                ps.setTimestamp(i++, new Timestamp(hasta.getTime()));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Log l = new Log();

                l.setIdUsuario(rs.getInt("id_usuario"));
                l.setTipoAccion(rs.getString("tipo_accion"));
                l.setModulo(rs.getString("modulo"));
                l.setFechaHora(rs.getTimestamp("fecha_hora"));
                l.setDescripcion(rs.getString("descripcion"));

                lista.add(l);
            }

        } catch (SQLException ex) {
            System.getLogger(LogBD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return lista;
    }

}
