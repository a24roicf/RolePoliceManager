package controlador;

import BD.DivisionBD;
import BD.LogBD;
import BD.UsuarioBD;
import modelo.Division;
import modelo.Usuario;
import vista.CrearModificarDivisionDialogVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author roi conles ferro
 */
public class CrearModificarDivisionControlador {
    
    private CrearModificarDivisionDialogVista vista;
    private DivisionBD divisionBD;
    private UsuarioBD usuarioBD;
    private Division divisionEditar;
    private Usuario usuarioLogueado;
    private LogBD logBD;
    private DivisionesControlador controladorPrincipal;
    
    //Constructor Crear división
    public CrearModificarDivisionControlador(CrearModificarDivisionDialogVista vista, Usuario usuario, DivisionesControlador controladorPrincipal) {
        this(vista, usuario, null, controladorPrincipal);
    }
    
    //Constructor Editar división
    public CrearModificarDivisionControlador(CrearModificarDivisionDialogVista vista, Usuario usuario, Division division, DivisionesControlador controladorPrincipal) {
        this.vista = vista;
        this.usuarioLogueado = usuario;
        this.divisionEditar = division;
        this.divisionBD = new DivisionBD();
        this.usuarioBD = new UsuarioBD();
        this.logBD = new LogBD();
        this.controladorPrincipal = controladorPrincipal;
        
        cargarCombos();
        
        if (divisionEditar != null) {
            cargarDatos();
        }
        
        vista.addAceptarListener(guardar());
        vista.addCancelarListener(cancelar());
    }
    
    private void cargarCombos() {
        List<Usuario> usuarios = usuarioBD.obtenerUsuarios();
        vista.cargarResponsables(usuarios);
    }
    
    private void cargarDatos() {
        vista.setNombre(divisionEditar.getNombre());
        vista.setDescripcion(divisionEditar.getDescripcion());
        
        //Seleccionar responsable
        if (divisionEditar.getIdResponsable() > 0) {
            Usuario responsable = usuarioBD.obtenerUsuarioPorId(divisionEditar.getIdResponsable());
            vista.setResponsable(responsable);
        } else {
            vista.setResponsable(null);
        }
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
                Usuario responsable = vista.getResponsableSeleccionado();
                int idResponsable = (responsable != null) ? responsable.getIdUsuario() : 0;
                
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "El nombre es obligatorio");
                    return;
                }
                
                //Crear
                if (divisionEditar == null) {
                    Division d = new Division();
                    d.setNombre(nombre);
                    d.setDescripcion(descripcion);
                    d.setIdResponsable(idResponsable);
                    
                    divisionBD.insertarDivision(d);
                    
                    //Log
                    logBD.insertarLog(usuarioLogueado.getIdUsuario(), "ALTA", "DIVISIONES", "Creó división: " + nombre);
                    
                } else {
                    //Editar
                    divisionEditar.setNombre(nombre);
                    divisionEditar.setDescripcion(descripcion);
                    divisionEditar.setIdResponsable(idResponsable);
                    
                    divisionBD.actualizarDivision(divisionEditar);
                    
                    //Log
                    logBD.insertarLog(usuarioLogueado.getIdUsuario(), "MODIFICACION", "DIVISIONES", "Editó división ID: " + divisionEditar.getIdDivision());
                }
                
                JOptionPane.showMessageDialog(vista, "Guardado correctamente");
                
                //Recargar tabla en el controlador principal
                if (controladorPrincipal != null) {
                    controladorPrincipal.recargarDivisiones();
                }
                
                vista.dispose();
            }
        };
        return al;
    }
}
