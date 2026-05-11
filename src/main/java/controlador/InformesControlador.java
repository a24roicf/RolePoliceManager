package controlador;

import BD.InformeBD;
import BD.LogBD;
import BD.UsuarioBD;
import modelo.Informe;
import modelo.Usuario;
import vista.InformesVista;
import vista.CrearInformeDialogVista;
import vista.VerInformeDialogVista;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roi conles ferro
 */
public class InformesControlador {

    private InformesVista vista;
    private Usuario usuarioActual;
    private LogBD logBD;
    private UsuarioBD usuarioBD;

    public InformesControlador(InformesVista vista, Usuario usuarioActual) {
        this.vista = vista;
        this.usuarioActual = usuarioActual;
        this.logBD = new LogBD();
        this.usuarioBD = new UsuarioBD();

        inicializarEventos();
        cargarUsuariosEnFiltro();
        cargarInformes();
        aplicarPermisos();
    }

    private void inicializarEventos() {
        vista.addBtnCrearListener(getBtnCrearListener());
        vista.addBtnVerListener(getBtnVerListener());
        vista.addBtnEliminarListener(getBtnEliminarListener());
        vista.addCmbFiltroListener(getCmbFiltroListener());
        vista.addCmbUsuarioListener(getCmbUsuarioListener());
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

    private ActionListener getBtnVerListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDialogoVer();
            }
        };
    }

    private ActionListener getBtnEliminarListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarInforme();
            }
        };
    }

    private ActionListener getCmbFiltroListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtro = vista.getFiltroSeleccionado();
                if ("Sobre usuario específico".equals(filtro)) {
                    vista.habilitarComboUsuario(true);
                } else {
                    vista.habilitarComboUsuario(false);
                }
                aplicarFiltro();
            }
        };
    }

    private ActionListener getCmbUsuarioListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltro();
            }
        };
    }

    private KeyListener getTxtBuscarListener() {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                aplicarFiltro();
            }
        };
    }

    private MouseListener getTablaMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    abrirDialogoVer();
                }
            }
        };
    }

    private void cargarUsuariosEnFiltro() {
        List<Usuario> usuarios = usuarioBD.obtenerUsuarios();
        vista.cargarUsuariosEnFiltro(usuarios);
    }

    private void cargarInformes() {
        List<Informe> informes = InformeBD.obtenerTodosLosInformes();
        vista.cargarInformes(informes);
    }

    private void aplicarFiltro() {
        String filtro = vista.getFiltroSeleccionado();
        String textoBusqueda = vista.getTextoBusqueda().toLowerCase();
        List<Informe> informes = new ArrayList<>();

        //Aplicar filtro por tipo
        if ("Todos".equals(filtro)) {
            informes = InformeBD.obtenerTodosLosInformes();
        } else if ("Realizados por mí".equals(filtro)) {
            informes = InformeBD.obtenerInformesPorEvaluador(usuarioActual.getIdUsuario());
        } else if ("Sobre usuario específico".equals(filtro)) {
            Usuario usuarioSeleccionado = vista.getUsuarioFiltroSeleccionado();
            if (usuarioSeleccionado != null) {
                informes = InformeBD.obtenerInformesPorEvaluado(usuarioSeleccionado.getIdUsuario());
            }
        }

        //Aplicar búsqueda por texto
        if (!textoBusqueda.isEmpty()) {
            List<Informe> informesFiltrados = new ArrayList<>();
            for (Informe informe : informes) {
                if (informe.getNombreEvaluador().toLowerCase().contains(textoBusqueda)
                        || informe.getNombreEvaluado().toLowerCase().contains(textoBusqueda)) {
                    informesFiltrados.add(informe);
                }
            }
            informes = informesFiltrados;
        }

        vista.cargarInformes(informes);
    }

    private void abrirDialogoCrear() {
        CrearInformeDialogVista cidv = new CrearInformeDialogVista(vista, true);
        new CrearInformeControlador(cidv, usuarioActual, this);
        cidv.setVisible(true);
    }

    private void abrirDialogoVer() {
        Informe informeSeleccionado = vista.getInformeSeleccionado();

        if (informeSeleccionado == null) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor, selecciona un informe para ver",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        VerInformeDialogVista vidv = new VerInformeDialogVista(vista, true);
        new VerInformeControlador(vidv, informeSeleccionado);
        vidv.setVisible(true);
    }

    private void eliminarInforme() {
        int idInforme = vista.getIdInformeSeleccionado();

        if (idInforme == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor, selecciona un informe para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Informe informe = vista.getInformeSeleccionado();

        //Solo puede eliminar si es el creador del informe o es nivel 3
        if (informe.getIdEvaluador() != usuarioActual.getIdUsuario() && usuarioActual.getNivelPermiso() < 3) {
            JOptionPane.showMessageDialog(vista,
                    "Solo puedes eliminar informes creados por ti",
                    "Acceso denegado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de que deseas eliminar este informe?\n"
                + "Evaluador: " + informe.getNombreEvaluador() + "\n"
                + "Evaluado: " + informe.getNombreEvaluado(),
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = InformeBD.eliminarInforme(idInforme);

            //Log
            if (eliminado) {
                logBD.insertarLog(
                        usuarioActual.getIdUsuario(),
                        "ELIMINAR",
                        "INFORMES",
                        "Eliminó informe ID: " + idInforme
                );

                JOptionPane.showMessageDialog(vista,
                        "Informe eliminado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                aplicarFiltro();
                vista.limpiarSeleccion();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "Error al eliminar el informe",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void aplicarPermisos() {
        //Solo niveles 2 y 3 pueden acceder a este módulo
        //Esta verificación debería hacerse antes de abrir la vista
        if (usuarioActual.getNivelPermiso() < 2) {
            vista.getBtnCrear().setEnabled(false);
            vista.getBtnVer().setEnabled(false);
            vista.getBtnEliminar().setEnabled(false);
        }
    }

    public void recargarInformes() {
        aplicarFiltro();
    }
}
