package conlesferroroi.rolepolicemanager;

import com.formdev.flatlaf.FlatDarkLaf;
import BD.ConexionBD;
import BD.UsuarioBD;
import modelo.Usuario;
import vista.vistaPrincipal;

/**
 *
 * @author alumno
 */
public class RolePoliceManager {

    public static void main(String[] args) {
        //Modificacion visual de flat
        FlatDarkLaf.setup();

        //Frame principal
        vistaPrincipal vistaPrincipal = new vistaPrincipal();
        vistaPrincipal.setLocationRelativeTo(null);
        vistaPrincipal.setTitle("RolePolice Manager");
        vistaPrincipal.setVisible(true);

        UsuarioBD dao = new UsuarioBD();

        /*Usuario u = new Usuario();
        u.setNombreRol("Agente");
        u.setEmail("test@test.com");
        u.setPassword("1234");
        u.setIdRango(1);
        u.setFechaIngreso(new java.util.Date());
        u.setEstado("activo");
        u.setNivelPermiso(1);

        dao.insertarUsuario(u);*/

        Usuario usuario = dao.login("test@test.com", "1234");

        if (usuario != null) {
            System.out.println("Login correcto: " + usuario.getNombreRol());
        } else {
            System.out.println("Login incorrecto");
        }
    }
}
