package controlador;

import BD.UsuarioBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.PrincipalVista;
import vista.UsuariosVista;

/**
 *
 * @author alumno
 */
public class PrincipalControlador {

    private PrincipalVista vista;
    private UsuarioBD usuario;

    public PrincipalControlador(PrincipalVista vista) {
        this.vista = vista;
        this.vista.addAdministrarUsuariosListener(getbtnAdministrarUsuarios());
    }

    private ActionListener getbtnAdministrarUsuarios() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuariosVista vista = new UsuariosVista();
                new UsuariosControlador(vista);
                vista.setVisible(true);
            }
        };
        return al;
    }
}
