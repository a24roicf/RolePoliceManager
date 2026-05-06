package controlador;

import BD.LogBD;
import BD.NormativaBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Normativa;
import modelo.Usuario;
import vista.CrearModificarNormativaDialogVista;

/**
 *
 * @author roi conles ferro
 */
public class CrearModificarNormativaControlador {

    private CrearModificarNormativaDialogVista vista;
    private NormativaBD normativaBD;
    private Usuario usuario;
    private Normativa normativaEditar;
    private LogBD logBD;

    //Constructor para crear
    public CrearModificarNormativaControlador(CrearModificarNormativaDialogVista vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.normativaBD = new NormativaBD();
        this.normativaEditar = null;
        this.logBD = new LogBD();

        vista.setTituloVentana("Role Police Manager - Nueva Normativa");

        vista.addBtnGuardarListener(getBtnGuardarListener());
        vista.addBtnCancelarListener(getBtnCancelarListener());
    }

    //Constructor para editar
    public CrearModificarNormativaControlador(CrearModificarNormativaDialogVista vista, Usuario usuario, Normativa normativa) {
        this.vista = vista;
        this.usuario = usuario;
        this.normativaBD = new NormativaBD();
        this.normativaEditar = normativa;
        this.logBD = new LogBD();

        vista.setTituloVentana("Role Police Manager - Editar Normativa");

        vista.addBtnGuardarListener(getBtnGuardarListener());
        vista.addBtnCancelarListener(getBtnCancelarListener());

        cargarDatosNormativa();
    }

    private void cargarDatosNormativa() {
        vista.setTituloNormativa(normativaEditar.getTitulo());
        vista.setContenido(normativaEditar.getContenido());
    }

    private ActionListener getBtnGuardarListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarNormativa();
            }
        };
        return al;
    }
    
    private ActionListener getBtnCancelarListener(){
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        };
        return al;
    }

    private void guardarNormativa() {
        String titulo = vista.getTituloNormativa().trim();
        String contenido = vista.getContenido().trim();

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El título no puede estar vacío",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (contenido.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El contenido no puede estar vacío",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (titulo.length() > 200) {
            JOptionPane.showMessageDialog(vista, "El título no puede superar los 200 caracteres",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear
        if (normativaEditar == null) {
            Normativa nuevaNormativa = new Normativa();
            nuevaNormativa.setTitulo(titulo);
            nuevaNormativa.setContenido(contenido);
            nuevaNormativa.setIdAutor(usuario.getIdUsuario());
            nuevaNormativa.setFechaPublicacion(new Date());

            boolean exito = normativaBD.insertarNormativa(nuevaNormativa);

            if (exito) {
                logBD.insertarLog(usuario.getIdUsuario(), "CREACION", "normativas",
                        "Creó la normativa: " + titulo);
                JOptionPane.showMessageDialog(vista, "Normativa creada correctamente");
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al crear la normativa",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Editar
        } else {
            normativaEditar.setTitulo(titulo);
            normativaEditar.setContenido(contenido);

            boolean exito = normativaBD.actualizarNormativa(normativaEditar);

            if (exito) {
                logBD.insertarLog(usuario.getIdUsuario(), "MODIFICACION", "normativas",
                        "Editó la normativa: " + titulo);
                JOptionPane.showMessageDialog(vista, "Normativa actualizada correctamente");
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar la normativa",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
