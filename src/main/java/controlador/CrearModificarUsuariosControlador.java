package controlador;

import BD.RangoBD;
import BD.UsuarioBD;
import modelo.Rango;
import modelo.Usuario;
import vista.CrearModificarUsuariosDialogVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Permiso;

public class CrearModificarUsuariosControlador {

    private CrearModificarUsuariosDialogVista vista;
    private UsuarioBD usuarioBD;
    private RangoBD rangoBD;
    private Usuario usuarioEditar;

    public CrearModificarUsuariosControlador(CrearModificarUsuariosDialogVista vista) {
        this(vista, null);
    }

    public CrearModificarUsuariosControlador(CrearModificarUsuariosDialogVista vista, Usuario usuario) {
        this.vista = vista;
        this.usuarioBD = new UsuarioBD();
        this.rangoBD = new RangoBD();
        this.usuarioEditar = usuario;

        cargarCombos();

        if (usuarioEditar != null) {
            cargarDatosUsuario();
        }

        vista.addAceptarListener(guardarUsuario());
        vista.addCancelarListener(e -> vista.dispose());
    }

    private void cargarCombos() {

        //RANGOS
        List<Rango> rangos = rangoBD.obtenerRangos();
        vista.setRangos(rangos);

        //ESTADOS
        vista.setEstados(new String[]{"activo", "vacaciones"});

        //PERMISOS
        List<Permiso> permisos = new ArrayList<>();
        permisos.add(new modelo.Permiso(1, "Oficial"));
        permisos.add(new modelo.Permiso(2, "Encargado"));
        permisos.add(new modelo.Permiso(3, "Administrador"));

        vista.setPermisos(permisos);
    }

    //GUARDAR
    private ActionListener guardarUsuario() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = vista.getTxtNombre();
                String email = vista.getTxtEmail();
                String estado = vista.getEstado();

                modelo.Permiso permiso = vista.getPermisoSeleccionado();
                Rango rango = vista.getRangoSeleccionado();

                Date fechaIngreso = vista.getFechaIngreso();
                Date fechaUltimoAscenso = vista.getFechaUltimoAscenso();

                if (nombre.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Rellene todos los campos");
                    return;
                }
                //CREAR
                if (usuarioEditar == null) {
                    Usuario u = new Usuario();
                    u.setNombreRol(nombre);
                    u.setEmail(email);
                    u.setPassword("1234");
                    u.setEstado(estado);
                    u.setNivelPermiso(permiso.getNivel());
                    u.setIdRango(rango.getIdRango());
                    u.setFechaIngreso(fechaIngreso != null ? fechaIngreso : new java.util.Date());  //Si no colocas fecha inicial pone la actual
                    u.setFechaUltimoAscenso(fechaUltimoAscenso);

                    usuarioBD.insertarUsuario(u);

                    //EDITAR
                } else {
                    usuarioEditar.setNombreRol(nombre);
                    usuarioEditar.setEmail(email);
                    usuarioEditar.setEstado(estado);
                    usuarioEditar.setNivelPermiso(permiso.getNivel());
                    usuarioEditar.setIdRango(rango.getIdRango());
                    usuarioEditar.setFechaIngreso(fechaIngreso);
                    usuarioEditar.setFechaUltimoAscenso(fechaUltimoAscenso);

                    usuarioBD.actualizarUsuario(usuarioEditar);
                }

                JOptionPane.showMessageDialog(vista, "Guardado correctamente");
                vista.dispose();
            }
        };
    }

    private void cargarDatosUsuario() {

        vista.setTxtNombre(usuarioEditar.getNombreRol());
        vista.setTxtEmail(usuarioEditar.getEmail());
        vista.setEstado(usuarioEditar.getEstado());
        vista.setFechaIngreso(usuarioEditar.getFechaIngreso());
        vista.setFechaUltimoAscenso(usuarioEditar.getFechaUltimoAscenso());

        //Seleccionar rango
        for (int i = 0; i < vista.getRangoCombo().getItemCount(); i++) {
            if (vista.getRangoCombo().getItemAt(i).getIdRango() == usuarioEditar.getIdRango()) {
                vista.getRangoCombo().setSelectedIndex(i);
                break;
            }
        }

        //Seleccionar permiso
        for (int i = 0; i < vista.getPermisoCombo().getItemCount(); i++) {
            if (vista.getPermisoCombo().getItemAt(i).getNivel() == usuarioEditar.getNivelPermiso()) {
                vista.getPermisoCombo().setSelectedIndex(i);
                break;
            }
        }
    }
}
