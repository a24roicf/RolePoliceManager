package BD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Normativa;

public class NormativaBD {

    private Connection connection;

    public NormativaBD() {
        this.connection = ConexionBD.conectar();
    }

    //Crear
    public boolean insertarNormativa(Normativa normativa) {
        String sql = "INSERT INTO Normativa (titulo, contenido, id_autor, fecha_publicacion) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, normativa.getTitulo());
            ps.setString(2, normativa.getContenido());
            ps.setInt(3, normativa.getIdAutor());
            ps.setDate(4, new java.sql.Date(normativa.getFechaPublicacion().getTime()));

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar normativa: " + e.getMessage());
            return false;
        }
    }

    //Obtener todas las normativas
    public List<Normativa> obtenerNormativas() {
        List<Normativa> normativas = new ArrayList<>();

        String sql = "SELECT n.*, u.nombre_rol as nombre_autor "
                + "FROM Normativa n "
                + "LEFT JOIN Usuario u ON n.id_autor = u.id_usuario "
                + "ORDER BY n.fecha_publicacion DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Normativa normativa = new Normativa();
                normativa.setIdNormativa(rs.getInt("id_normativa"));
                normativa.setTitulo(rs.getString("titulo"));
                normativa.setContenido(rs.getString("contenido"));
                normativa.setIdAutor(rs.getInt("id_autor"));
                normativa.setFechaPublicacion(rs.getDate("fecha_publicacion"));
                normativa.setNombreAutor(rs.getString("nombre_autor"));

                normativas.add(normativa);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener normativas: " + e.getMessage());
        }

        return normativas;
    }

    //Obtener normativa por ID
    public Normativa obtenerNormativaPorId(int idNormativa) {
        String sql = "SELECT n.*, u.nombre_rol as nombre_autor "
                + "FROM Normativa n "
                + "LEFT JOIN Usuario u ON n.id_autor = u.id_usuario "
                + "WHERE n.id_normativa = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idNormativa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Normativa normativa = new Normativa();
                    normativa.setIdNormativa(rs.getInt("id_normativa"));
                    normativa.setTitulo(rs.getString("titulo"));
                    normativa.setContenido(rs.getString("contenido"));
                    normativa.setIdAutor(rs.getInt("id_autor"));
                    normativa.setFechaPublicacion(rs.getDate("fecha_publicacion"));
                    normativa.setNombreAutor(rs.getString("nombre_autor"));

                    return normativa;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener normativa: " + e.getMessage());
        }

        return null;
    }

    //Actualizar
    public boolean actualizarNormativa(Normativa normativa) {
        String sql = "UPDATE Normativa SET titulo = ?, contenido = ? WHERE id_normativa = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, normativa.getTitulo());
            ps.setString(2, normativa.getContenido());
            ps.setInt(3, normativa.getIdNormativa());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al actualizar normativa: " + e.getMessage());
            return false;
        }
    }

    //Eliminar
    public boolean eliminarNormativa(int idNormativa) {
        String sql = "DELETE FROM Normativa WHERE id_normativa = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idNormativa);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar normativa: " + e.getMessage());
            return false;
        }
    }
}
