package BD;

import modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumno
 */
public class UsuarioBD {

    //INSERTAR USUARIO
    public boolean insertarUsuario(Usuario u) {
        String sql = "INSERT INTO Usuario (nombre_rol, email, password, id_rango, fecha_ingreso, estado, nivel_permiso)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombreRol());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setInt(4, u.getIdRango());
            ps.setDate(5, new java.sql.Date(u.getFechaIngreso().getTime()));
            ps.setString(6, u.getEstado());
            ps.setInt(7, u.getNivelPermiso());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //OBTENER TODOS LOS USUARIOS
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";

        try (Connection conexion = ConexionBD.conectar(); Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario();

                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombreRol(rs.getString("nombre_rol"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setIdRango(rs.getInt("id_rango"));
                u.setFechaIngreso(rs.getDate("fecha_ingreso"));
                u.setFechaUltimoAscenso(rs.getDate("fecha_ultimo_ascenso"));
                u.setEstado(rs.getString("estado"));
                u.setNivelPermiso(rs.getInt("nivel_permiso"));

                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    //OBTENER USUARIO POR ID
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";
        Usuario u = null;

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Usuario();

                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombreRol(rs.getString("nombre_rol"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setIdRango(rs.getInt("id_rango"));
                u.setFechaIngreso(rs.getDate("fecha_ingreso"));
                u.setFechaUltimoAscenso(rs.getDate("fecha_ultimo_ascenso"));
                u.setEstado(rs.getString("estado"));
                u.setNivelPermiso(rs.getInt("nivel_permiso"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return u;
    }

    //ACTUALIZAR USUARIO
    public boolean actualizarUsuario(Usuario u) {
        String sql = "UPDATE Usuario SET nombre_rol=?, email=?, password=?, id_rango=?, estado=?, nivel_permiso=? WHERE id_usuario=?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, u.getNombreRol());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setInt(4, u.getIdRango());
            ps.setString(5, u.getEstado());
            ps.setInt(6, u.getNivelPermiso());
            ps.setInt(7, u.getIdUsuario());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //ELIMINAR USUARIO
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM Usuario WHERE id_usuario=?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Usuario login(String email, String password) {
        String sql = "SELECT * FROM Usuario WHERE email = ? AND password = ?";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();

                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombreRol(rs.getString("nombre_rol"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setIdRango(rs.getInt("id_rango"));
                u.setFechaIngreso(rs.getDate("fecha_ingreso"));
                u.setEstado(rs.getString("estado"));
                u.setNivelPermiso(rs.getInt("nivel_permiso"));

                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
