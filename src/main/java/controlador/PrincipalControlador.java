package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.AnunciosVista;
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
        this.vista.addBtnAdministrarUsuariosListener(getbtnAdministrarUsuarios());
        this.vista.addBtnRegistroLogsListener(getBtnRegistroLogs());
        this.vista.addBtnAnunciosListener(getBtnAnuncios());
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
                JFrame frame = new JFrame("Anuncios");
                frame.setSize(800, 600);

                AnunciosVista vistaAnuncios = new AnunciosVista();
                frame.add(vistaAnuncios);

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                // TEMP: prueba visual
                vistaAnuncios.agregarTarjetaAnuncio(
                        1,
                        "Aviso general",
                        "Esto es una prueba de anuncio",
                        "general",
                        "Admin",
                        "04/06/2026",
                        ev -> {
                        },
                        ev -> {
                        }
                );
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
}
