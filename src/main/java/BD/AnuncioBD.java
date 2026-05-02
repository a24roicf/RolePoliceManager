package BD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Anuncio;

/**
 *
 * @author Roi Conles Ferro
 */
public class AnuncioBD {

    private Connection connection;

    public AnuncioBD() {
        this.connection = ConexionBD.conectar();
    }

    public boolean insertarAnuncio(Anuncio anuncio) {
        String sql = "INSERT INTO Anuncio (titulo, contenido, id_autor, id_division, fecha_publicacion, tipo) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, anuncio.getTitulo());
            ps.setString(2, anuncio.getContenido());
            ps.setInt(3, anuncio.getIdAutor());
            //Por si no pertenece a ninguna division
            if (anuncio.getIdDivision() != null) {
                ps.setInt(4, anuncio.getIdAnuncio());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setDate(5, new Date(anuncio.getFechaPublicacion().getTime()));
            ps.setString(6, anuncio.getTipo());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar anuncio: " + e.getMessage());
            return false;
        }
    }

    public List<Anuncio> obtenerAnuncios() {
        List<Anuncio> anuncios = new ArrayList<>();

        String sql = "SELECT a.*, u.nombre_rol AS nombre_autor "
                + "FROM Anuncio a "
                + "LEFT JOIN Usuario u ON a.id_autor = u.id_usuario "
                + "ORDER BY a.fecha_publicacion DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Anuncio anuncio = new Anuncio();
                anuncio.setIdAnuncio(rs.getInt("id_anuncio"));
                anuncio.setTitulo(rs.getString("titulo"));
                anuncio.setContenido(rs.getString("contenido"));
                anuncio.setIdAutor(rs.getInt("id_autor"));
                //idDivision controlamos por si es null
                int idDiv = rs.getInt("id_division");
                if (!rs.wasNull()) {
                    anuncio.setIdDivision(idDiv);
                }
                anuncio.setFechaPublicacion(rs.getDate("fecha_publicacion"));
                anuncio.setTipo(rs.getString("tipo"));
                anuncio.setNombreAutor(rs.getString("nombre_autor"));

                anuncios.add(anuncio);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener anuncios: " + e.getMessage());
        }
        return anuncios;
    }

    public Anuncio obtenerAnuncioPorId(int idAnuncio) {
        String sql = "SELECT a.*, u.nombre_rol as nombre_autor " + "FROM Anuncio a " + "LEFT JOIN Usuario u ON a.id_autor = u.id_usuario " + "WHERE a.id_anuncio = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnuncio);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Anuncio anuncio = new Anuncio();
                    anuncio.setIdAnuncio(rs.getInt("id_anuncio"));
                    anuncio.setTitulo(rs.getString("titulo"));
                    anuncio.setContenido(rs.getString("contenido"));
                    anuncio.setIdAutor(rs.getInt("id_autor"));
                    int idDiv = rs.getInt("id_division");
                    if (!rs.wasNull()) {
                        anuncio.setIdDivision(idDiv);
                    }
                    anuncio.setFechaPublicacion(rs.getDate("fecha_publicacion"));
                    anuncio.setTipo(rs.getString("tipo"));
                    anuncio.setNombreAutor(rs.getString("nombre_autor"));
                    return anuncio;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener anuncio: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizarAnuncio(Anuncio anuncio) {
        String sql = "UPDATE Anuncio SET titulo = ?, contenido = ?, id_division = ?, tipo = ? WHERE id_anuncio = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, anuncio.getTitulo());
            ps.setString(2, anuncio.getContenido());
            if (anuncio.getIdDivision() != null) {
                ps.setInt(3, anuncio.getIdDivision());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, anuncio.getTipo());
            ps.setInt(5, anuncio.getIdAnuncio());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar anuncio: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarAnuncio(int idAnuncio) {
        String sql = "DELETE FROM Anuncio WHERE id_anuncio = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAnuncio);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar anuncio: " + e.getMessage());
            return false;
        }
    }
}
