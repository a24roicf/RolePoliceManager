package controlador;

import BD.UsuarioBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import vista.UsuariosVista;

/**
 *
 * @author alumno
 */
public class UsuariosControlador {

    private UsuariosVista vista;
    private UsuarioBD bd;

    public UsuariosControlador(UsuariosVista vista) {
        this.vista = vista;
        this.bd = new UsuarioBD();

        vista.addBtnCargarListener(cargarUsuarios());
        vista.addBtnAgregarListener(agregarUsuario());
        vista.addBtnEliminarListener(eliminarUsuario());
    }

    //CARGAR USUARIOS EN LA TABLA
    private ActionListener cargarUsuarios() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Usuario> lista = bd.obtenerUsuarios();

                DefaultTableModel modelo = new DefaultTableModel();
                modelo.addColumn("ID");
                modelo.addColumn("Email");
                modelo.addColumn("Nombre");

                for (Usuario usuario : lista) {
                    modelo.addRow(new Object[]{
                        usuario.getIdUsuario(),
                        usuario.getEmail(),
                        usuario.getNombreRol()
                    });
                }
                vista.getUsuariosTable().setModel(modelo);
            }
        };
        return al;
    }

    //AGREGAR USUARIO SIMPLE TODO
    private ActionListener agregarUsuario() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = JOptionPane.showInputDialog("Email:");
                String pass = JOptionPane.showInputDialog("Password:");

                Usuario u = new Usuario();
                u.setEmail(email);
                u.setPassword(pass);
                u.setNombreRol("Agente");
                u.setIdRango(1);
                u.setFechaIngreso(new java.util.Date());
                u.setEstado("activo");
                u.setNivelPermiso(1);

                bd.insertarUsuario(u);

                JOptionPane.showMessageDialog(vista, "Usuario creado");
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

                int id = (int) vista.getUsuariosTable().getValueAt(fila, 0);

                bd.eliminarUsuario(id);

                JOptionPane.showMessageDialog(vista, "Usuario eliminado");
            }
        };
        return al;
    }
}
