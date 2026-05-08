package BD;

import modelo.Division;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roi conles ferro
 */
public class DivisionBD {

    public static List<Division> obtenerTodasLasDivisiones() {
        List<Division> divisiones = new ArrayList<>();
        String sql = "SELECT d.id_division, d.nombre, d.descripcion, d.id_responsable, u.nombre_rol AS nombre_responsable "
                + "FROM Division d "
                + "LEFT JOIN Usuario u ON d.id_responsable = u.id_usuario "
                + "ORDER BY d.nombre";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Division division = new Division();
                division.setIdDivision(rs.getInt("id_division"));
                division.setNombre(rs.getString("nombre"));
                division.setDescripcion(rs.getString("descripcion"));
                division.setIdResponsable(rs.getInt("id_responsable"));
                division.setNombreResponsable(rs.getString("nombre_responsable"));
                divisiones.add(division);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener divisiones: " + e.getMessage());
        }

        return divisiones;
    }

    public static Division obtenerDivisionPorId(int idDivision) {
        String sql = "SELECT d.id_division, d.nombre, d.descripcion, d.id_responsable, u.nombre_rol AS nombre_responsable "
                + "FROM Division d "
                + "LEFT JOIN Usuario u ON d.id_responsable = u.id_usuario "
                + "WHERE d.id_division = ?";
        
        Division division = null;
        
        try (Connection conn = ConexionBD.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDivision);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    division.setIdDivision(rs.getInt("id_division"));
                    division.setNombre(rs.getString("nombre"));
                    division.setDescripcion(rs.getString("descripcion"));
                    division.setIdResponsable(rs.getInt("id_responsable"));
                    division.setNombreResponsable(rs.getString("nombre_responsable"));
                    return division;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener división por ID: " + e.getMessage());
        }

        return division;
    }
    
    public static boolean insertarDivision(Division division) {
        String sql = "INSERT INTO Division (nombre, descripcion, id_responsable) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, division.getNombre());
            ps.setString(2, division.getDescripcion());
            
            // Si no hay responsable asignado, insertar NULL
            if (division.getIdResponsable() > 0) {
                ps.setInt(3, division.getIdResponsable());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar división: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean actualizarDivision(Division division) {
        String sql = "UPDATE Division SET nombre = ?, descripcion = ?, id_responsable = ? " +
                     "WHERE id_division = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, division.getNombre());
            ps.setString(2, division.getDescripcion());
            
            // Si no hay responsable asignado, insertar NULL
            if (division.getIdResponsable() > 0) {
                ps.setInt(3, division.getIdResponsable());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            
            ps.setInt(4, division.getIdDivision());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al actualizar división: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean eliminarDivision(int idDivision) {
        Connection conn = null;
        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            // Primero eliminar las relaciones de usuarios con esta división
            String sqlRelaciones = "DELETE FROM Usuario_Division WHERE id_division = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlRelaciones)) {
                ps.setInt(1, idDivision);
                ps.executeUpdate();
            }

            // Luego eliminar la división
            String sqlDivision = "DELETE FROM Division WHERE id_division = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDivision)) {
                ps.setInt(1, idDivision);
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
            System.err.println("Error al eliminar división: " + e.getMessage());
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
    
    public static boolean asignarUsuarioADivision(int idUsuario, int idDivision) {
        String sql = "INSERT INTO Usuario_Division (id_usuario, id_division) VALUES (?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idDivision);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al asignar usuario a división: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean quitarUsuarioDeDivision(int idUsuario, int idDivision) {
        String sql = "DELETE FROM Usuario_Division WHERE id_usuario = ? AND id_division = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idDivision);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al quitar usuario de división: " + e.getMessage());
            return false;
        }
    }
    
    public static List<Integer> obtenerUsuariosDeDivision(int idDivision) {
        List<Integer> idsUsuarios = new ArrayList<>();
        String sql = "SELECT id_usuario FROM Usuario_Division WHERE id_division = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDivision);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    idsUsuarios.add(rs.getInt("id_usuario"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios de división: " + e.getMessage());
        }

        return idsUsuarios;
    }
    
    public static int contarMiembrosDivision(int idDivision) {
        String sql = "SELECT COUNT(*) as total FROM Usuario_Division WHERE id_division = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDivision);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al contar miembros de división: " + e.getMessage());
        }

        return 0;
    }
    
    /*public static boolean usuarioYaAsignado(int idUsuario, int idDivision) {
        String sql = "SELECT COUNT(*) as total FROM Usuario_Division " +
                     "WHERE id_usuario = ? AND id_division = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idDivision);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar asignación: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }*/
}
