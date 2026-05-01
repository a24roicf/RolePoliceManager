package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.PrincipalVista;
import vista.UsuariosVista;

/**
 *
 * @author roi conles ferro
 */
public class PrincipalControlador {

    private PrincipalVista vista;
    private Usuario usuario;

    public PrincipalControlador(PrincipalVista vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.vista.addAdministrarUsuariosListener(getbtnAdministrarUsuarios());
    }

    private ActionListener getbtnAdministrarUsuarios() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usuario.getNivelPermiso() >= 3) {
                    UsuariosVista vista = new UsuariosVista();
                    new UsuariosControlador(vista, usuario);
                    vista.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(vista, "Acceso denegado");
                }
            }
        };
        return al;
    }
}
