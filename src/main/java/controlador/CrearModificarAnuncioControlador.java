package controlador;

import BD.AnuncioBD;
import BD.LogBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Anuncio;
import modelo.Usuario;
import vista.CrearModificarAnuncioDialogVista;

/**
 *
 * @author Roi Conles Ferro
 */
public class CrearModificarAnuncioControlador {

    private CrearModificarAnuncioDialogVista vista;
    private AnuncioBD anuncioBD;
    private Anuncio anuncioEditar;
    private Usuario usuarioLogueado;
    private LogBD logBD;

    //Constructor Crear anuncio
    public CrearModificarAnuncioControlador(CrearModificarAnuncioDialogVista vista, Usuario usuario) {
        this(vista, usuario, null);
        this.logBD = new LogBD();
    }

    //Constructor Editar anuncio
    public CrearModificarAnuncioControlador(CrearModificarAnuncioDialogVista vista, Usuario usuario, Anuncio anuncio) {
        this.vista = vista;
        this.usuarioLogueado = usuario;
        this.anuncioEditar = anuncio;
        this.anuncioBD = new AnuncioBD();
        this.logBD = new LogBD();

        cargarCombos();

        if (anuncioEditar != null) {
            cargarDatos();
        }
        vista.addAceptarListener(guardar());
        vista.addCancelarListener(cancelar());
    }

    private void cargarCombos() {
        vista.setTipos(new String[]{
            "general",
            "urgente",
            "informativo",
            "division"
        });
    }

    private void cargarDatos() {
        vista.setTitulo(anuncioEditar.getTitulo());
        vista.setContenido(anuncioEditar.getContenido());
        vista.setTipo(anuncioEditar.getTipo());
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
                String titulo = vista.getTitulo();
                String contenido = vista.getContenido();
                String tipo = vista.getTipo();

                if (titulo.isEmpty() || contenido.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Rellena todos los campos");
                    return;
                }

                //Crear
                if (anuncioEditar == null) {
                    Anuncio a = new Anuncio();
                    a.setTitulo(titulo);
                    a.setContenido(contenido);
                    a.setTipo(tipo);
                    a.setIdAutor(usuarioLogueado.getIdUsuario());
                    anuncioBD.insertarAnuncio(a);
                    
                    //Log
                    logBD.insertarLog(usuarioLogueado.getIdUsuario(), "ALTA", "anuncios", "Creó anuncio: " + titulo);
                    
                } else {
                    //Editar
                    anuncioEditar.setTitulo(titulo);
                    anuncioEditar.setContenido(contenido);
                    anuncioEditar.setTipo(tipo);
                    anuncioBD.actualizarAnuncio(anuncioEditar);
                    
                    //Log
                    logBD.insertarLog(usuarioLogueado.getIdUsuario(), "MODIFICACION", "anuncios", "Editó anuncio ID: "+ anuncioEditar.getIdAnuncio());
                }

                JOptionPane.showMessageDialog(vista, "Guardado correctamente");
                vista.dispose();
            }
        };
        return al;
    }
}
