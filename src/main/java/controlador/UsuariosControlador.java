package controlador;

import BD.UsuarioBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import vista.UsuariosVista;
import vista.CrearModificarUsuariosDialogVista;
import controlador.CrearModificarUsuariosControlador;
import BD.RangoBD;
import java.text.SimpleDateFormat;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.Rango;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author alumno
 */
public class UsuariosControlador {

    private UsuariosVista vista;
    private UsuarioBD bd;
    private RangoBD rangoBD;
    private List<Usuario> listaOriginal;

    public UsuariosControlador(UsuariosVista vista) {
        this.vista = vista;
        this.bd = new UsuarioBD();
        this.rangoBD = new RangoBD();

        vista.addBtnAgregarListener(agregarUsuario());
        vista.addBtnEliminarListener(eliminarUsuario());
        vista.addBtnEditarListener(editarUsuario());
        vista.addBuscarListener(filtrar());

        cargarTabla();
    }

    //EDITAR USUARIOS EN LA TABLA
    private ActionListener editarUsuario() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = vista.getUsuariosTable().getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Selecciona un usuario");
                    return;
                }

                int id = (int) vista.getUsuariosTable().getValueAt(fila, 0);

                Usuario u = bd.obtenerUsuarioPorId(id);

                CrearModificarUsuariosDialogVista dialog = new CrearModificarUsuariosDialogVista(vista, true);

                CrearModificarUsuariosControlador cmuc = new CrearModificarUsuariosControlador(dialog, u);

                dialog.setLocationRelativeTo(vista);
                dialog.setVisible(true);

                cargarTabla();
            }
        };
        return al;
    }

    //AGREGAR USUARIO
    private ActionListener agregarUsuario() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearModificarUsuariosDialogVista dialog
                        = new CrearModificarUsuariosDialogVista(vista, true);

                new CrearModificarUsuariosControlador(dialog);

                dialog.setLocationRelativeTo(vista);
                dialog.setVisible(true);

                cargarTabla();
            }
        };
        return al;
    }

    //ELIMINAR USUARIOS
    private ActionListener eliminarUsuario() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = vista.getUsuariosTable().getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Selecciona un usuario");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(vista, "¿Seguro que quieres eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
                int id = (int) vista.getUsuariosTable().getValueAt(fila, 0);

                bd.eliminarUsuario(id);

                JOptionPane.showMessageDialog(vista, "Usuario eliminado");

                cargarTabla();
            }
        };
        return al;
    }

    private void cargarTabla() {
        listaOriginal = bd.obtenerUsuarios();
        pintarTabla(listaOriginal);
    }

    private void pintarTabla(List<Usuario> lista) {
        List<Rango> rangos = rangoBD.obtenerRangos();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Email");
        modelo.addColumn("Nombre");
        modelo.addColumn("Rango");
        modelo.addColumn("Fecha Ingreso");
        modelo.addColumn("Fecha Ascenso");
        modelo.addColumn("Estado");
        modelo.addColumn("Permisos");

        for (Usuario u : lista) {
            // Buscar el nombre del rango
            String nombreRango = "";
            for (Rango r : rangos) {
                if (r.getIdRango() == u.getIdRango()) {
                    nombreRango = r.getNombre();
                    break;
                }
            }

            // Convertir nivel de permiso a texto
            String nivelPermisoTexto = "";
            switch (u.getNivelPermiso()) {
                case 1:
                    nivelPermisoTexto = "Oficial";
                    break;
                case 2:
                    nivelPermisoTexto = "Encargado";
                    break;
                case 3:
                    nivelPermisoTexto = "Administrador";
                    break;
                default:
                    nivelPermisoTexto = "Desconocido";
                    break;
            }

            // Formatear fechas: modificamos la fecha para que se vea simple, si es null el campo sale vacio
            /*String fechaIngreso;
            if (u.getFechaIngreso() != null) {
                fechaIngreso = new SimpleDateFormat("dd/MM/yyyy").format(u.getFechaIngreso());
            } else {
                fechaIngreso = "";
            }

            String fechaUltimoAscenso;
            if (u.getFechaUltimoAscenso() != null) {
                fechaUltimoAscenso = new SimpleDateFormat("dd/MM/yyyy").format(u.getFechaUltimoAscenso());
            } else {
                fechaUltimoAscenso = "";
            }*/

            modelo.addRow(new Object[]{
                u.getIdUsuario(),
                u.getEmail(),
                u.getNombreRol(),
                nombreRango,
                u.getFechaIngreso()/*fechaIngreso*/,
                u.getFechaUltimoAscenso()/*fechaUltimoAscenso*/,
                u.getEstado(),
                nivelPermisoTexto
            });
        }

        vista.getUsuariosTable().setModel(modelo);
    }

    private DocumentListener filtrar() {
        return new DocumentListener() {

            private void filtrarLista() {
                String texto = vista.getTextoBusqueda().toLowerCase();

                List<Usuario> filtrada = new java.util.ArrayList<>();

                for (Usuario u : listaOriginal) {
                    if (u.getEmail().toLowerCase().contains(texto)
                            || u.getNombreRol().toLowerCase().contains(texto)) {
                        filtrada.add(u);
                    }
                }
                pintarTabla(filtrada);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarLista();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarLista();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarLista();
            }
        };
    }
}
