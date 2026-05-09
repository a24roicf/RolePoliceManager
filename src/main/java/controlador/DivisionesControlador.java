package controlador;

import BD.DivisionBD;
import BD.LogBD;
import modelo.Division;
import modelo.Usuario;
import vista.DivisionesVista;
import vista.CrearModificarDivisionDialogVista;
import vista.GestionarMiembrosDivisionDialogVista;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DivisionesControlador {

    private DivisionesVista vista;
    private Usuario usuarioActual;
    private LogBD logBD;

    public DivisionesControlador(DivisionesVista vista, Usuario usuarioActual) {
        this.vista = vista;
        this.usuarioActual = usuarioActual;
        this.logBD = new LogBD();

        inicializarEventos();
        cargarDivisiones();
        aplicarPermisos();
    }

    private void inicializarEventos() {
        vista.addBtnCrearListener(getBtnCrearListener());
        vista.addBtnEditarListener(getBtnEditarListener());
        vista.addBtnEliminarListener(getBtnEliminarListener());
        vista.addBtnGestionarMiembrosListener(getBtnGestionarMiembrosListener());
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
                eliminarDivision();
            }
        };
    }

    private ActionListener getBtnGestionarMiembrosListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestionMiembros();
            }
        };
    }

    private KeyListener getTxtBuscarListener() {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarDivisiones();
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

    private void cargarDivisiones() {
        List<Division> divisiones = DivisionBD.obtenerTodasLasDivisiones();
        vista.cargarDivisiones(divisiones);
    }

    private void filtrarDivisiones() {
        String textoBusqueda = vista.getTextoBusqueda().toLowerCase();
        List<Division> todasDivisiones = DivisionBD.obtenerTodasLasDivisiones();

        if (textoBusqueda.isEmpty()) {
            vista.cargarDivisiones(todasDivisiones);
            return;
        }

        List<Division> divisionesFiltradas = new ArrayList<>();
        for (Division division : todasDivisiones) {
            if (division.getNombre().toLowerCase().contains(textoBusqueda)
                    || (division.getDescripcion() != null && division.getDescripcion().toLowerCase().contains(textoBusqueda))
                    || (division.getNombreResponsable() != null && division.getNombreResponsable().toLowerCase().contains(textoBusqueda))) {
                divisionesFiltradas.add(division);
            }
        }

        vista.cargarDivisiones(divisionesFiltradas);
    }

    private void abrirDialogoCrear() {
        CrearModificarDivisionDialogVista cmddv = new CrearModificarDivisionDialogVista(vista, true);
        cmddv.setTituloVentana("Crear División");
        new CrearModificarDivisionControlador(cmddv, usuarioActual, this);
        cmddv.setVisible(true);
    }

    private void abrirDialogoEditar() {
        Division divisionSeleccionada = vista.getDivisionSeleccionada();

        if (divisionSeleccionada == null) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor, selecciona una división para editar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Obtener la división completa de la BD
        Division divisionCompleta = DivisionBD.obtenerDivisionPorId(divisionSeleccionada.getIdDivision());

        CrearModificarDivisionDialogVista cmddv = new CrearModificarDivisionDialogVista(vista, true);
        cmddv.setTituloVentana("Editar División");
        new CrearModificarDivisionControlador(cmddv, usuarioActual, divisionCompleta, this);
        cmddv.setVisible(true);
    }

    private void eliminarDivision() {
        int idDivision = vista.getIdDivisionSeleccionada();

        if (idDivision == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor, selecciona una división para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Division division = vista.getDivisionSeleccionada();

        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Estás seguro de que deseas eliminar la división '" + division.getNombre() + "'?\n"
                + "Esta acción también eliminará todas las asignaciones de usuarios a esta división.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = DivisionBD.eliminarDivision(idDivision);

            if (eliminado) {
                // Registrar en log
                logBD.insertarLog(
                        usuarioActual.getIdUsuario(),
                        "ELIMINAR",
                        "DIVISIONES",
                        "Eliminó la división: " + division.getNombre()
                );

                JOptionPane.showMessageDialog(vista,
                        "División eliminada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                cargarDivisiones();
                vista.limpiarSeleccion();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "Error al eliminar la división",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirGestionMiembros() {
        Division divisionSeleccionada = vista.getDivisionSeleccionada();

        if (divisionSeleccionada == null) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor, selecciona una división para gestionar sus miembros",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        GestionarMiembrosDivisionDialogVista gmddv = new GestionarMiembrosDivisionDialogVista(vista, true);
        gmddv.setNombreDivision(divisionSeleccionada.getNombre());
        new GestionarMiembrosDivisionesControlador(gmddv, divisionSeleccionada.getIdDivision(), usuarioActual, this);
        gmddv.setVisible(true);
    }

    private void aplicarPermisos() {
        // Solo nivel 3 (Administrador) puede crear, editar y eliminar divisiones
        // Nivel 2 (Encargado) puede ver y gestionar miembros de su división
        // Nivel 1 (Oficial) solo lectura

        if (usuarioActual.getNivelPermiso() < 3) {
            // Deshabilitar botones de creación, edición y eliminación para niveles 1 y 2
            vista.getBtnCrear().setEnabled(false);
            vista.getBtnEditar().setEnabled(false);
            vista.getBtnEliminar().setEnabled(false);
        }

        if (usuarioActual.getNivelPermiso() < 2) {
            // Nivel 1 no puede gestionar miembros
            vista.getBtnGestionarMiembros().setEnabled(false);
        }
    }

    // Método público para recargar divisiones (llamado desde otros controladores)
    public void recargarDivisiones() {
        cargarDivisiones();
    }
}
