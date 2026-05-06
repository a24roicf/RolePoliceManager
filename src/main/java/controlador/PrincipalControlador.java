package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.AnunciosVista;
import vista.LogsVista;
import vista.NormativasVista;
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
        this.vista.addBtnAdministrarUsuariosListener(getbtnAdministrarUsuarios());
        this.vista.addBtnRegistroLogsListener(getBtnRegistroLogs());
        this.vista.addBtnAnunciosListener(getBtnAnuncios());
        this.vista.addBtnNormativasListener(getbtnNormativa());
        Permisos();
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

    private ActionListener getBtnAnuncios() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                AnunciosVista vistaAnuncios = new AnunciosVista();
                new AnuncioControlador(vistaAnuncios, usuario);
                frame.setContentPane(vistaAnuncios);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }

        };
        return al;
    }

    private void Permisos() {
        if (usuario.getNivelPermiso() <= 2) {
            vista.btnAdministrarUsuariosEnable(false);
            vista.btnRegistroLogsEnable(false);
        }
    }

    private ActionListener getbtnNormativa() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NormativasVista vistaNormativas = new NormativasVista();
                new NormativaControlador(vistaNormativas, usuario);
                vistaNormativas.setVisible(true);
            }
        };
        return al;
    }
}
