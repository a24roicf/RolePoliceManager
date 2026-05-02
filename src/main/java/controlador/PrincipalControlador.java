package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.LogsVista;
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
        this.vista.addRegistroLogsListener(getBtnRegistroLogs());
    }

    private ActionListener getbtnAdministrarUsuarios() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usuario.getNivelPermiso() >= 3) {
                    UsuariosVista vistaUsuarios = new UsuariosVista();
                    new UsuariosControlador(vistaUsuarios, usuario);
                    vistaUsuarios.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(vista, "Acceso denegado");
                }
            }
        };
        return al;
    }

    private ActionListener getBtnRegistroLogs() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (usuario.getNivelPermiso() >= 3) {
                    LogsVista vistaLogs = new LogsVista();
                    new LogsControlador(vistaLogs, usuario);
                    vistaLogs.setLocationRelativeTo(null);
                    vistaLogs.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(vista, "Acceso denegado");
                }
            }
        };
        return al;
    }
}
