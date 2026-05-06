package controlador;

import BD.LogBD;
import BD.NormativaBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Normativa;
import modelo.Usuario;
import vista.NormativasVista;
import vista.CrearModificarNormativaDialogVista;
//import vista.VerNormativaDialogVista;

/**
 *
 * @author Roi Conles Ferro
 */
public class NormativaControlador {

    private NormativasVista vista;
    private NormativaBD normativaBD;
    private Usuario usuario;
    private LogBD logBD;

    public NormativaControlador(NormativasVista vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.normativaBD = new NormativaBD();

        vista.addBtnAgregarListener(getBtnAgregarListener());
        vista.addBtnEditarListener(getBtnEditarListener());
        vista.addBtnEliminarListener(getBtnEliminarListener());
        //vista.addBtnVerListener();

        cargarTabla();
        aplicarPermisos();
    }

    private void cargarTabla() {

        List<Normativa> lista = normativaBD.obtenerNormativas();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Titulo");
        modelo.addColumn("Autor");
        modelo.addColumn("Fecha");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Normativa n : lista) {
            modelo.addRow(new Object[]{
                n.getIdNormativa(),
                n.getTitulo(),
                n.getNombreAutor(),
                n.getFechaPublicacion() != null ? sdf.format(n.getFechaPublicacion()) : ""
            });
        }

        vista.getNormativaTabla().setModel(modelo);

        // Ocultamos el ID para usarlo internamente
        vista.getNormativaTabla().getColumnModel().getColumn(0).setMinWidth(0);
        vista.getNormativaTabla().getColumnModel().getColumn(0).setMaxWidth(0);
    }

    private ActionListener getBtnAgregarListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearModificarNormativaDialogVista cmndv = new CrearModificarNormativaDialogVista(vista, true);
                new CrearModificarNormativaControlador(cmndv, usuario);

                cmndv.setLocationRelativeTo(null);
                cmndv.setVisible(true);
                cargarTabla();
            }
        };
        return al;
    }

    private ActionListener getBtnEditarListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = vista.getNormativaTabla().getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Selecciona una normativa");
                    return;
                }

                int id = (int) vista.getNormativaTabla().getValueAt(fila, 0);

                Normativa n = normativaBD.obtenerNormativaPorId(id);

                CrearModificarNormativaDialogVista cmndv = new CrearModificarNormativaDialogVista(vista, true);
                new CrearModificarNormativaControlador(cmndv, usuario, n);
                cmndv.setLocationRelativeTo(null);
                cmndv.setVisible(true);
                cargarTabla();
            }

        };
        return al;
    }

    private ActionListener getBtnEliminarListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = vista.getNormativaTabla().getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Selecciona una normativa");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(vista, "¿Eliminar normativa?", "Confirmar", JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                int id = (int) vista.getNormativaTabla().getValueAt(fila, 0);

                normativaBD.eliminarNormativa(id);

                //Log
                logBD.insertarLog(usuario.getIdUsuario(), "BORRADO", "anuncios", "Elimino una normativa");
                
                cargarTabla();
            }
        };
        return al;
    }

    private ActionListener getBtnVerNormativa() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = vista.getNormativaTabla().getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Selecciona una normativa");
                    return;
                }

                int id = (int) vista.getNormativaTabla().getValueAt(fila, 0);

                Normativa n = normativaBD.obtenerNormativaPorId(id);

                //VerNormativaDialogVista vndv = new VerNormativaDialogVista(vista, true);

                //vndv.setTitulo(n.getTitulo());
                //vndv.setContenido(n.getContenido());
                //vndv.setInfo("Por: " + n.getNombreAutor() + " | " + n.getFechaPublicacion());

                //vndv.setLocationRelativeTo(null);
                //vndv.setVisible(true);
            }
        };
        return al;
    }

    private void aplicarPermisos() {

        if (usuario.getNivelPermiso() < 2) {
            vista.btnAgregarEnable(false);
            vista.btnEditarEnable(false);
            vista.btnEliminarEnable(false);
        }
    }
}
