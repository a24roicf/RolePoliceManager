package BD;

import modelo.Licencia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roi conles ferro
 */
public class LicenciaBD {
    public static List<Licencia> obtenerTodasLasLicencias() {
        List<Licencia> licencias = new ArrayList<>();
        String sql = "SELECT id_licencia, nombre, descripcion FROM Licencia ORDER BY nombre";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Licencia licencia = new Licencia();
                licencia.setIdLicencia(rs.getInt("id_licencia"));
                licencia.setNombre(rs.getString("nombre"));
                licencia.setDescripcion(rs.getString("descripcion"));
                licencias.add(licencia);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener licencias: " + e.getMessage());
        }

        return licencias;
    }

    public static Licencia obtenerLicenciaPorId(int idLicencia) {
        String sql = "SELECT id_licencia, nombre, descripcion FROM Licencia WHERE id_licencia = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLicencia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Licencia licencia = new Licencia();
                    licencia.setIdLicencia(rs.getInt("id_licencia"));
                    licencia.setNombre(rs.getString("nombre"));
                    licencia.setDescripcion(rs.getString("descripcion"));
                    return licencia;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener licencia por ID: " + e.getMessage());
        }

        return null;
    }

    public static boolean insertarLicencia(Licencia licencia) {
        String sql = "INSERT INTO Licencia (nombre, descripcion) VALUES (?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, licencia.getNombre());
            ps.setString(2, licencia.getDescripcion());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar licencia: " + e.getMessage());
            return false;
        }
    }

    public static boolean actualizarLicencia(Licencia licencia) {
        String sql = "UPDATE Licencia SET nombre = ?, descripcion = ? WHERE id_licencia = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, licencia.getNombre());
            ps.setString(2, licencia.getDescripcion());
            ps.setInt(3, licencia.getIdLicencia());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al actualizar licencia: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarLicencia(int idLicencia) {
        Connection conn = null;
        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            // Primero eliminar las relaciones de usuarios con esta licencia
            String sqlRelaciones = "DELETE FROM Usuario_Licencia WHERE id_licencia = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlRelaciones)) {
                ps.setInt(1, idLicencia);
                ps.executeUpdate();
            }

            // Luego eliminar la licencia
            String sqlLicencia = "DELETE FROM Licencia WHERE id_licencia = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlLicencia)) {
                ps.setInt(1, idLicencia);
                int filasAfectadas = ps.executeUpdate();
                
                if (filasAfectadas > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar licencia: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }

    public static boolean asignarLicenciaAUsuario(int idUsuario, int idLicencia, Date fechaObtencion) {
        String sql = "INSERT INTO Usuario_Licencia (id_usuario, id_licencia, fecha_obtencion) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idLicencia);
            ps.setDate(3, fechaObtencion != null ? new java.sql.Date(fechaObtencion.getTime()) : new java.sql.Date(System.currentTimeMillis()));

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al asignar licencia a usuario: " + e.getMessage());
            return false;
        }
    }

    public static boolean quitarLicenciaDeUsuario(int idUsuario, int idLicencia) {
        String sql = "DELETE FROM Usuario_Licencia WHERE id_usuario = ? AND id_licencia = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idLicencia);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al quitar licencia de usuario: " + e.getMessage());
            return false;
        }
    }

    public static List<Integer> obtenerUsuariosConLicencia(int idLicencia) {
        List<Integer> idsUsuarios = new ArrayList<>();
        String sql = "SELECT id_usuario FROM Usuario_Licencia WHERE id_licencia = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLicencia);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    idsUsuarios.add(rs.getInt("id_usuario"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios con licencia: " + e.getMessage());
        }

        return idsUsuarios;
    }

    public static int contarUsuariosConLicencia(int idLicencia) {
        String sql = "SELECT COUNT(*) as total FROM Usuario_Licencia WHERE id_licencia = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLicencia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al contar usuarios con licencia: " + e.getMessage());
        }

        return 0;
    }

    public static boolean usuarioTieneLicencia(int idUsuario, int idLicencia) {
        String sql = "SELECT COUNT(*) as total FROM Usuario_Licencia WHERE id_usuario = ? AND id_licencia = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idLicencia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar licencia de usuario: " + e.getMessage());
        }

        return false;
    }
}
