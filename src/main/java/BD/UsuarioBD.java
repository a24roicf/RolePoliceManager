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
    public Usuario obtenerUsuarioPorId(int id) {
        Usuario u = null;

        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombreRol(rs.getString("nombre_rol"));
                u.setEmail(rs.getString("email"));
                u.setEstado(rs.getString("estado"));
                u.setNivelPermiso(rs.getInt("nivel_permiso"));
                u.setIdRango(rs.getInt("id_rango"));
            }

        } catch (Exception e) {
            e.printStackTrace();
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
    public void eliminarUsuario(int id) {

        try (Connection con = ConexionBD.conectar()) {

            //transacción
            con.setAutoCommit(false);

            //usuario_division
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM usuario_division WHERE id_usuario = ?");
            ps1.setInt(1, id);
            ps1.executeUpdate();

            //usuario_licencia
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM usuario_licencia WHERE id_usuario = ?");
            ps2.setInt(1, id);
            ps2.executeUpdate();

            //log_actividad
            PreparedStatement ps3 = con.prepareStatement("DELETE FROM log_actividad WHERE id_usuario = ?");
            ps3.setInt(1, id);
            ps3.executeUpdate();

            //informe (evaluador o evaluado)
            PreparedStatement ps4 = con.prepareStatement("DELETE FROM informe WHERE id_evaluador = ? OR id_evaluado = ?");
            ps4.setInt(1, id);
            ps4.setInt(2, id);
            ps4.executeUpdate();

            //anuncio
            PreparedStatement ps5 = con.prepareStatement("DELETE FROM anuncio WHERE id_autor = ?");
            ps5.setInt(1, id);
            ps5.executeUpdate();

            //division (si es responsable → poner NULL)
            PreparedStatement ps6 = con.prepareStatement("UPDATE division SET id_responsable = NULL WHERE id_responsable = ?");
            ps6.setInt(1, id);
            ps6.executeUpdate();

            //borrar usuario
            PreparedStatement psFinal = con.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");
            psFinal.setInt(1, id);
            psFinal.executeUpdate();

            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
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
