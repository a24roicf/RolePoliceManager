package controlador;

import BD.LogBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.AnunciosVista;
import vista.DivisionesVista;
import vista.InformesVista;
import vista.LicenciasVista;
import vista.LoginVista;
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
    private LogBD logBD;

    public PrincipalControlador(PrincipalVista vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.vista.addBtnAdministrarUsuariosListener(getbtnAdministrarUsuarios());
        this.vista.addBtnRegistroLogsListener(getBtnRegistroLogs());
        this.vista.addBtnAnunciosListener(getBtnAnuncios());
        this.vista.addBtnNormativasListener(getbtnNormativa());
        this.vista.addBtnDivisionesListener(getbtnDivisiones());
        this.vista.addBtnLicenciasListener(getBtnLicencias());
        this.vista.addBtnInformesListener(getbtnInformes());
        this.vista.addBtnCerrarSesionListener(getBtnCerrarSesion());
        this.logBD = new LogBD();
        Permisos();
    }

    private ActionListener getbtnAdministrarUsuarios() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuariosVista vistaUsuarios = new UsuariosVista();
                new UsuariosControlador(vistaUsuarios, usuario);
                vistaUsuarios.setVisible(true);
            }
        };
        return al;
    }

    private ActionListener getBtnRegistroLogs() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogsVista vistaLogs = new LogsVista();
                new LogsControlador(vistaLogs, usuario);
                vistaLogs.setLocationRelativeTo(null);
                vistaLogs.setVisible(true);
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
        if (usuario.getNivelPermiso() < 2) {
            vista.btnInformesEnable(false);
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

    private ActionListener getbtnDivisiones() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DivisionesVista vistaDivisiones = new DivisionesVista();
                new DivisionesControlador(vistaDivisiones, usuario);
                vistaDivisiones.setVisible(true);
            }
        };
        return al;
    }

    private ActionListener getBtnLicencias() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LicenciasVista vistaLicencias = new LicenciasVista();
                new LicenciasControlador(vistaLicencias, usuario);
                vistaLicencias.setVisible(true);
            }
        };
        return al;
    }

    private ActionListener getbtnInformes() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformesVista informesVista = new InformesVista();
                new InformesControlador(informesVista, usuario);
                informesVista.setVisible(true);
            }
        };
        return al;
    }

    private ActionListener getBtnCerrarSesion() {
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de querer cerrar sesión?",
                        "Confirmar cierre de sesión",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (confirmacion == JOptionPane.YES_OPTION){
                    //Log
                    logBD.insertarLog(usuario.getIdUsuario(), "LOGOUT", "SESION", "Cerro sesion");
                    
                    vista.dispose();
                    
                    LoginVista vistaLogin = new LoginVista();
                    new LoginControlador(vistaLogin);
                    vistaLogin.setLocationRelativeTo(null);
                    vistaLogin.setVisible(true);
                }
            }
        };
        return al;
    }
}
