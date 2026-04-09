package conlesferroroi.rolepolicemanager;

import com.formdev.flatlaf.FlatDarkLaf;
import modelo.ConexionBD;
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
        
        //Conexion BD
        ConexionBD.abrirConexion();
    }
}
