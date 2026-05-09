package controlador;

import BD.DivisionBD;
import BD.UsuarioBD;
import modelo.Usuario;
import vista.GestionarMiembrosDivisionDialogVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author roi conles ferro
 */
public class GestionarMiembrosDivisionesControlador {
    
    private GestionarMiembrosDivisionDialogVista vista;
    private DivisionBD divisionBD;
    private UsuarioBD usuarioBD;
    private int idDivision;
    private Usuario usuarioLogueado;
    private DivisionesControlador controladorPrincipal;
    
    public GestionarMiembrosDivisionesControlador(GestionarMiembrosDivisionDialogVista vista, int idDivision, Usuario usuario, DivisionesControlador controladorPrincipal) {
        this.vista = vista;
        this.idDivision = idDivision;
        this.usuarioLogueado = usuario;
        this.divisionBD = new DivisionBD();
        this.usuarioBD = new UsuarioBD();
        this.controladorPrincipal = controladorPrincipal;
        
        cargarListas();
        
        vista.addAgregarListener(agregar());
        vista.addQuitarListener(quitar());
        vista.addVolverListener(volver());
    }
    
    private void cargarListas() {
        //Obtener todos los usuarios
        List<Usuario> todosUsuarios = usuarioBD.obtenerUsuarios();
        
        //Obtener IDs de usuarios ya asignados a esta división
        List<Integer> idsAsignados = divisionBD.obtenerUsuariosDeDivision(idDivision);
        
        //Separar en dos listas: disponibles y miembros
        List<Usuario> disponibles = new ArrayList<>();
        List<Usuario> miembros = new ArrayList<>();
        
        for (Usuario u : todosUsuarios) {
            if (idsAsignados.contains(u.getIdUsuario())) {
                miembros.add(u);
            } else {
                disponibles.add(u);
            }
        }
        
        vista.cargarUsuariosDisponibles(disponibles);
        vista.cargarMiembros(miembros);
    }
    
    private ActionListener agregar() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuarioSeleccionado = vista.getUsuarioLibreSeleccionado();
                
                if (usuarioSeleccionado == null) {
                    JOptionPane.showMessageDialog(vista, "Selecciona un usuario para agregar");
                    return;
                }
                
                boolean asignado = divisionBD.asignarUsuarioADivision(usuarioSeleccionado.getIdUsuario(), idDivision);
                
                if (asignado) {
                    JOptionPane.showMessageDialog(vista, "Usuario agregado correctamente");
                    cargarListas();
                    vista.limpiarSelecciones();
                    
                    //Recargar tabla principal para actualizar número de miembros
                    if (controladorPrincipal != null) {
                        controladorPrincipal.recargarDivisiones();
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al agregar usuario");
                }
            }
        };
        return al;
    }
    
    private ActionListener quitar() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuarioSeleccionado = vista.getMiembroSeleccionado();
                
                if (usuarioSeleccionado == null) {
                    JOptionPane.showMessageDialog(vista, "Selecciona un usuario para quitar");
                    return;
                }
                
                int confirmacion = JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro de quitar a " + usuarioSeleccionado.getNombreRol() + " de esta división?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    boolean quitado = divisionBD.quitarUsuarioDeDivision(usuarioSeleccionado.getIdUsuario(), idDivision);
                    
                    if (quitado) {
                        JOptionPane.showMessageDialog(vista, "Usuario quitado correctamente");
                        cargarListas();
                        vista.limpiarSelecciones();
                        
                        //Recargar tabla principal para actualizar número de miembros
                        if (controladorPrincipal != null) {
                            controladorPrincipal.recargarDivisiones();
                        }
                    } else {
                        JOptionPane.showMessageDialog(vista, "Error al quitar usuario");
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