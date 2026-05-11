package controlador;

import BD.InformeBD;
import BD.LogBD;
import BD.UsuarioBD;
import modelo.Informe;
import modelo.Usuario;
import vista.CrearInformeDialogVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author roi conles ferro
 */
public class CrearInformeControlador {

    private CrearInformeDialogVista vista;
    private InformeBD informeBD;
    private UsuarioBD usuarioBD;
    private Usuario usuarioLogueado;
    private LogBD logBD;
    private InformesControlador controladorPrincipal;

    public CrearInformeControlador(CrearInformeDialogVista vista, Usuario usuario, InformesControlador controladorPrincipal) {
        this.vista = vista;
        this.usuarioLogueado = usuario;
        this.informeBD = new InformeBD();
        this.usuarioBD = new UsuarioBD();
        this.logBD = new LogBD();
        this.controladorPrincipal = controladorPrincipal;

        cargarUsuarios();

        vista.addAceptarListener(guardar());
        vista.addCancelarListener(cancelar());
    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = usuarioBD.obtenerUsuarios();
        vista.cargarUsuarios(usuarios);
    }

    private ActionListener cancelar() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        };
        return al;
    }

    private ActionListener guardar() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario evaluado = vista.getEvaluadoSeleccionado();
                String contenido = vista.getContenido();

                if (evaluado == null) {
                    JOptionPane.showMessageDialog(vista, "Selecciona un usuario para evaluar");
                    return;
                }

                if (contenido.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "El contenido del informe es obligatorio");
                    return;
                }

                Informe informe = new Informe();
                informe.setIdEvaluador(usuarioLogueado.getIdUsuario());
                informe.setIdEvaluado(evaluado.getIdUsuario());
                informe.setContenido(contenido);
                informe.setFecha(new Date());

                boolean insertado = informeBD.insertarInforme(informe);
                //Log
                if (insertado) {
                    logBD.insertarLog(
                            usuarioLogueado.getIdUsuario(),
                            "ALTA",
                            "INFORMES",
                            "Creó informe sobre: " + evaluado.getNombreRol()
                    );

                    JOptionPane.showMessageDialog(vista, "Informe creado correctamente");

                    if (controladorPrincipal != null) {
                        controladorPrincipal.recargarInformes();
                    }

                    vista.dispose();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al crear el informe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        return al;
    }
}
