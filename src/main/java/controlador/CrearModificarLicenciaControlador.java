package controlador;

import BD.LicenciaBD;
import BD.LogBD;
import modelo.Licencia;
import modelo.Usuario;
import vista.CrearModificarLicenciaDialogVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author roi conles ferro
 */
public class CrearModificarLicenciaControlador {

    private CrearModificarLicenciaDialogVista vista;
    private LicenciaBD licenciaBD;
    private Licencia licenciaEditar;
    private Usuario usuarioLogueado;
    private LogBD logBD;
    private LicenciasControlador controladorPrincipal;

    //Constructor Crear licencia
    public CrearModificarLicenciaControlador(CrearModificarLicenciaDialogVista vista, Usuario usuario, LicenciasControlador controladorPrincipal) {
        this(vista, usuario, null, controladorPrincipal);
    }

    //Constructor Editar licencia
    public CrearModificarLicenciaControlador(CrearModificarLicenciaDialogVista vista, Usuario usuario, Licencia licencia, LicenciasControlador controladorPrincipal) {
        this.vista = vista;
        this.usuarioLogueado = usuario;
        this.licenciaEditar = licencia;
        this.licenciaBD = new LicenciaBD();
        this.logBD = new LogBD();
        this.controladorPrincipal = controladorPrincipal;

        if (licenciaEditar != null) {
            cargarDatos();
        }

        vista.addAceptarListener(guardar());
        vista.addCancelarListener(cancelar());
    }

    private void cargarDatos() {
        vista.setNombre(licenciaEditar.getNombre());
        vista.setDescripcion(licenciaEditar.getDescripcion());
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
                String nombre = vista.getNombre();
                String descripcion = vista.getDescripcion();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "El nombre es obligatorio");
                    return;
                }

                //Crear
                if (licenciaEditar == null) {
                    Licencia l = new Licencia();
                    l.setNombre(nombre);
                    l.setDescripcion(descripcion);

                    licenciaBD.insertarLicencia(l);

                    //Log
                    logBD.insertarLog(usuarioLogueado.getIdUsuario(), "ALTA", "LICENCIAS", "Creó licencia: " + nombre);

                } else {
                    //Editar
                    licenciaEditar.setNombre(nombre);
                    licenciaEditar.setDescripcion(descripcion);

                    licenciaBD.actualizarLicencia(licenciaEditar);

                    //Log
                    logBD.insertarLog(usuarioLogueado.getIdUsuario(), "MODIFICACION", "LICENCIAS", "Editó licencia ID: " + licenciaEditar.getIdLicencia());
                }

                JOptionPane.showMessageDialog(vista, "Guardado correctamente");

                //Recargar tabla en el controlador principal
                if (controladorPrincipal != null) {
                    controladorPrincipal.recargarLicencias();
                }

                vista.dispose();
            }
        };
        return al;
    }
}
