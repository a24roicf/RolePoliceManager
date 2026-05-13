package controlador;

import BD.LicenciaBD;
import BD.UsuarioBD;
import modelo.Usuario;
import vista.GestionarUsuariosLicenciaDialogVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author roi conles ferro
 */
public class GestionarUsuariosLicenciaControlador {

    private GestionarUsuariosLicenciaDialogVista vista;
    private LicenciaBD licenciaBD;
    private UsuarioBD usuarioBD;
    private int idLicencia;
    private Usuario usuarioLogueado;
    private LicenciasControlador controladorPrincipal;

    public GestionarUsuariosLicenciaControlador(GestionarUsuariosLicenciaDialogVista vista, Usuario usuario, int idLicencia, LicenciasControlador controladorPrincipal) {
        this.vista = vista;
        this.usuarioLogueado = usuario;
        this.idLicencia = idLicencia;
        this.licenciaBD = new LicenciaBD();
        this.usuarioBD = new UsuarioBD();
        this.controladorPrincipal = controladorPrincipal;

        cargarListas();

        vista.addAgregarListener(agregar());
        vista.addQuitarListener(quitar());
        vista.addVolverListener(volver());
    }

    private void cargarListas() {
        // Obtener todos los usuarios
        List<Usuario> todosUsuarios = usuarioBD.obtenerUsuarios();

        // Obtener IDs de usuarios que ya tienen esta licencia
        List<Integer> idsConLicencia = licenciaBD.obtenerUsuariosConLicencia(idLicencia);

        // Separar en dos listas: disponibles y con licencia
        List<Usuario> disponibles = new ArrayList<>();
        List<Usuario> conLicencia = new ArrayList<>();

        for (Usuario u : todosUsuarios) {
            if (idsConLicencia.contains(u.getIdUsuario())) {
                conLicencia.add(u);
            } else {
                disponibles.add(u);
            }
        }

        vista.cargarUsuariosDisponibles(disponibles);
        vista.cargarUsuariosConLicencia(conLicencia);
    }

    private ActionListener agregar() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuarioSeleccionado = vista.getUsuarioDisponibleSeleccionado();

                if (usuarioSeleccionado == null) {
                    JOptionPane.showMessageDialog(vista, "Selecciona un usuario para agregar");
                    return;
                }

                // Asignar con fecha actual
                Date fechaActual = new Date();
                boolean asignado = licenciaBD.asignarLicenciaAUsuario(usuarioSeleccionado.getIdUsuario(), idLicencia, new java.sql.Date(fechaActual.getTime()));

                if (asignado) {
                    JOptionPane.showMessageDialog(vista, "Licencia asignada correctamente");
                    cargarListas();
                    vista.limpiarSelecciones();

                    // Recargar tabla principal para actualizar número de usuarios
                    if (controladorPrincipal != null) {
                        controladorPrincipal.recargarLicencias();
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al asignar licencia");
                }
            }
        };
        return al;
    }

    private ActionListener quitar() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuarioSeleccionado = vista.getUsuarioConLicenciaSeleccionado();

                if (usuarioSeleccionado == null) {
                    JOptionPane.showMessageDialog(vista, "Selecciona un usuario para quitar");
                    return;
                }

                int confirmacion = JOptionPane.showConfirmDialog(vista,
                        "¿Estás seguro de quitar esta licencia a " + usuarioSeleccionado.getNombreRol() + "?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    boolean quitado = licenciaBD.quitarLicenciaDeUsuario(usuarioSeleccionado.getIdUsuario(), idLicencia);

                    if (quitado) {
                        JOptionPane.showMessageDialog(vista, "Licencia quitada correctamente");
                        cargarListas();
                        vista.limpiarSelecciones();

                        // Recargar tabla principal para actualizar número de usuarios
                        if (controladorPrincipal != null) {
                            controladorPrincipal.recargarLicencias();
                        }
                    } else {
                        JOptionPane.showMessageDialog(vista, "Error al quitar licencia");
                    }
                }
            }
        };
        return al;
    }

    private ActionListener volver() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        };
        return al;
    }
}
