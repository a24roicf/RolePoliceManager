package BD;

import modelo.Informe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roi conles ferro
 */
public class InformeBD {

    public static List<Informe> obtenerTodosLosInformes() {
        List<Informe> informes = new ArrayList<>();
        String sql = "SELECT i.id_informe, i.id_evaluador, i.id_evaluado, i.contenido, i.fecha, "
                + "u1.nombre_rol AS nombre_evaluador, u2.nombre_rol AS nombre_evaluado "
                + "FROM Informe i "
                + "JOIN Usuario u1 ON i.id_evaluador = u1.id_usuario "
                + "JOIN Usuario u2 ON i.id_evaluado = u2.id_usuario "
                + "ORDER BY i.fecha DESC";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Informe informe = new Informe();
                informe.setIdInforme(rs.getInt("id_informe"));
                informe.setIdEvaluador(rs.getInt("id_evaluador"));
                informe.setIdEvaluado(rs.getInt("id_evaluado"));
                informe.setContenido(rs.getString("contenido"));
                informe.setFecha(rs.getDate("fecha"));
                informe.setNombreEvaluador(rs.getString("nombre_evaluador"));
                informe.setNombreEvaluado(rs.getString("nombre_evaluado"));
                informes.add(informe);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener informes: " + e.getMessage());
        }

        return informes;
    }

    public static List<Informe> obtenerInformesPorEvaluador(int idEvaluador) {
        List<Informe> informes = new ArrayList<>();
        String sql = "SELECT i.id_informe, i.id_evaluador, i.id_evaluado, i.contenido, i.fecha, "
                + "u1.nombre_rol AS nombre_evaluador, u2.nombre_rol AS nombre_evaluado "
                + "FROM Informe i "
                + "JOIN Usuario u1 ON i.id_evaluador = u1.id_usuario "
                + "JOIN Usuario u2 ON i.id_evaluado = u2.id_usuario "
                + "WHERE i.id_evaluador = ? "
                + "ORDER BY i.fecha DESC";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvaluador);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Informe informe = new Informe();
                    informe.setIdInforme(rs.getInt("id_informe"));
                    informe.setIdEvaluador(rs.getInt("id_evaluador"));
                    informe.setIdEvaluado(rs.getInt("id_evaluado"));
                    informe.setContenido(rs.getString("contenido"));
                    informe.setFecha(rs.getDate("fecha"));
                    informe.setNombreEvaluador(rs.getString("nombre_evaluador"));
                    informe.setNombreEvaluado(rs.getString("nombre_evaluado"));
                    informes.add(informe);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener informes por evaluador: " + e.getMessage());
        }

        return informes;
    }

    public static List<Informe> obtenerInformesPorEvaluado(int idEvaluado) {
        List<Informe> informes = new ArrayList<>();
        String sql = "SELECT i.id_informe, i.id_evaluador, i.id_evaluado, i.contenido, i.fecha, "
                + "u1.nombre_rol AS nombre_evaluador, u2.nombre_rol AS nombre_evaluado "
                + "FROM Informe i "
                + "JOIN Usuario u1 ON i.id_evaluador = u1.id_usuario "
                + "JOIN Usuario u2 ON i.id_evaluado = u2.id_usuario "
                + "WHERE i.id_evaluado = ? "
                + "ORDER BY i.fecha DESC";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvaluado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Informe informe = new Informe();
                    informe.setIdInforme(rs.getInt("id_informe"));
                    informe.setIdEvaluador(rs.getInt("id_evaluador"));
                    informe.setIdEvaluado(rs.getInt("id_evaluado"));
                    informe.setContenido(rs.getString("contenido"));
                    informe.setFecha(rs.getDate("fecha"));
                    informe.setNombreEvaluador(rs.getString("nombre_evaluador"));
                    informe.setNombreEvaluado(rs.getString("nombre_evaluado"));
                    informes.add(informe);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener informes por evaluado: " + e.getMessage());
        }

        return informes;
    }

    public static Informe obtenerInformePorId(int idInforme) {
        String sql = "SELECT i.id_informe, i.id_evaluador, i.id_evaluado, i.contenido, i.fecha, "
                + "u1.nombre_rol AS nombre_evaluador, u2.nombre_rol AS nombre_evaluado "
                + "FROM Informe i "
                + "JOIN Usuario u1 ON i.id_evaluador = u1.id_usuario "
                + "JOIN Usuario u2 ON i.id_evaluado = u2.id_usuario "
                + "WHERE i.id_informe = ?";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idInforme);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Informe informe = new Informe();
                    informe.setIdInforme(rs.getInt("id_informe"));
                    informe.setIdEvaluador(rs.getInt("id_evaluador"));
                    informe.setIdEvaluado(rs.getInt("id_evaluado"));
                    informe.setContenido(rs.getString("contenido"));
                    informe.setFecha(rs.getDate("fecha"));
                    informe.setNombreEvaluador(rs.getString("nombre_evaluador"));
                    informe.setNombreEvaluado(rs.getString("nombre_evaluado"));
                    return informe;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener informe por ID: " + e.getMessage());
        }

        return null;
    }

    public static boolean insertarInforme(Informe informe) {
        String sql = "INSERT INTO Informe (id_evaluador, id_evaluado, contenido, fecha) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, informe.getIdEvaluador());
            ps.setInt(2, informe.getIdEvaluado());
            ps.setString(3, informe.getContenido());
            if (informe.getFecha() != null) {
                ps.setDate(4, new Date(informe.getFecha().getTime()));
            } else {
                ps.setDate(4, new Date(System.currentTimeMillis()));
            }

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar informe: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarInforme(int idInforme) {
        String sql = "DELETE FROM Informe WHERE id_informe = ?";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idInforme);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar informe: " + e.getMessage());
            return false;
        }
    }

    public static int contarInformesDeUsuario(int idEvaluado) {
        String sql = "SELECT COUNT(*) as total FROM Informe WHERE id_evaluado = ?";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvaluado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al contar informes de usuario: " + e.getMessage());
        }

        return 0;
    }
}
