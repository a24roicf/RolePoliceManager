package controlador;

import BD.LicenciaBD;
import BD.LogBD;
import modelo.Licencia;
import modelo.Usuario;
import vista.LicenciasVista;
import vista.CrearModificarLicenciaDialogVista;
import vista.GestionarUsuariosLicenciaDialogVista;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roi conles ferro
 */
public class LicenciasControlador {

    private LicenciasVista vista;
    private Usuario usuarioActual;
    private LogBD logBD;

    public LicenciasControlador(LicenciasVista vista, Usuario usuarioActual) {
        this.vista = vista;
        this.usuarioActual = usuarioActual;
        this.logBD = new LogBD();

        inicializarEventos();
        cargarLicencias();
        aplicarPermisos();
    }

    private void inicializarEventos() {
        vista.addBtnCrearListener(getBtnCrearListener());
        vista.addBtnEditarListener(getBtnEditarListener());
        vista.addBtnEliminarListener(getBtnEliminarListener());
        vista.addBtnGestionarUsuariosListener(getBtnGestionarUsuariosListener());
        vista.addTxtBuscarListener(getTxtBuscarListener());
        vista.addTablaMouseListener(getTablaMouseListener());
    }

    private ActionListener getBtnCrearListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDialogoCrear();
            }
        };
    }

    private ActionListener getBtnEditarListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDialogoEditar();
            }
        };
    }

    private ActionListener getBtnEliminarListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarLicencia();
            }
        };
    }

    private ActionListener getBtnGestionarUsuariosListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestionUsuarios();
            }
        };
    }

    private KeyListener getTxtBuscarListener() {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarLicencias();
            }
        };
    }

    private MouseListener getTablaMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    abrirDialogoEditar();
                }
            }
        };
    }

    private void cargarLicencias() {
        List<Licencia> licencias = LicenciaBD.obtenerTodasLasLicencias();
        vista.cargarLicencias(licencias);
    }

    private void filtrarLicencias() {
        String textoBusqueda = vista.getTextoBusqueda().toLowerCase();
        List<Licencia> todasLicencias = LicenciaBD.obtenerTodasLasLicencias();

        if (textoBusqueda.isEmpty()) {
            vista.cargarLicencias(todasLicencias);
            return;
        }

        List<Licencia> licenciasFiltradas = new ArrayList<>();
        for (Licencia licencia : todasLicencias) {
            if (licencia.getNombre().toLowerCase().contains(textoBusqueda)
                    || (licencia.getDescripcion() != null && licencia.getDescripcion().toLowerCase().contains(textoBusqueda))) {
                licenciasFiltradas.add(licencia);
            }
        }

        vista.cargarLicencias(licenciasFiltradas);
    }

    private void abrirDialogoCrear() {
        CrearModificarLicenciaDialogVista cmldv = new CrearModificarLicenciaDialogVista(vista, true);
        cmldv.setTituloVentana("Crear Licencia");
        new CrearModificarLicenciaControlador(cmldv, usuarioActual, this);
        cmldv.setVisible(true);
    }

    private void abrirDialogoEditar() {
        Licencia licenciaSeleccionada = vista.getLicenciaSeleccionada();

        if (licenciaSeleccionada == null) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor, selecciona una licencia para editar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Obtener la licencia completa de la BD
        Licencia licenciaCompleta = LicenciaBD.obtenerLicenciaPorId(licenciaSeleccionada.getIdLicencia());

        if (licenciaCompleta == null) {
            JOptionPane.showMessageDialog(vista,
                    "Error al obtener los datos de la licencia",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        CrearModificarLicenciaDialogVista cmldv = new CrearModificarLicenciaDialogVista(vista, true);
        cmldv.setTituloVentana("Editar Licencia");
        new CrearModificarLicenciaControlador(cmldv, usuarioActual, licenciaCompleta, this);
        cmldv.setVisible(true);
    }

    private void eliminarLicencia() {
        int idLicencia = vista.getIdLicenciaSeleccionada();

        if (idLicencia == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor, selecciona una licencia para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Licencia licencia = vista.getLicenciaSeleccionada();

        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Estás seguro de que deseas eliminar la licencia '" + licencia.getNombre() + "'?\n"
                + "Esta acción también eliminará todas las asignaciones de esta licencia a usuarios.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = LicenciaBD.eliminarLicencia(idLicencia);

            if (eliminado) {
                //Registrar en log
                logBD.insertarLog(
                        usuarioActual.getIdUsuario(),
                        "ELIMINAR",
                        "LICENCIAS",
                        "Eliminó la licencia: " + licencia.getNombre()
                );

                JOptionPane.showMessageDialog(vista,
                        "Licencia eliminada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                cargarLicencias();
                vista.limpiarSeleccion();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "Error al eliminar la licencia",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirGestionUsuarios() {
        Licencia licenciaSeleccionada = vista.getLicenciaSeleccionada();

        if (licenciaSeleccionada == null) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor, selecciona una licencia para gestionar sus usuarios",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        GestionarUsuariosLicenciaDialogVista guldv = new GestionarUsuariosLicenciaDialogVista(vista, true);
        guldv.setNombreLicencia(licenciaSeleccionada.getNombre());
        new GestionarUsuariosLicenciaControlador(guldv, usuarioActual, licenciaSeleccionada.getIdLicencia(), this);
        guldv.setVisible(true);
    }

    private void aplicarPermisos() {
        //Solo nivel 3 (Administrador) puede crear, editar y eliminar licencias
        //Nivel 2 (Encargado) puede ver y gestionar usuarios con licencias
        //Nivel 1 (Oficial) solo lectura

        if (usuarioActual.getNivelPermiso() < 3) {
            //Deshabilitar botones de creación, edición y eliminación para niveles 1 y 2
            vista.getBtnCrear().setEnabled(false);
            vista.getBtnEditar().setEnabled(false);
            vista.getBtnEliminar().setEnabled(false);
        }

        if (usuarioActual.getNivelPermiso() < 2) {
            //Nivel 1 no puede gestionar usuarios
            vista.getBtnGestionarUsuarios().setEnabled(false);
        }
    }

    //Método público para recargar licencias (llamado desde otros controladores)
    public void recargarLicencias() {
        cargarLicencias();
    }
}
