package controlador;

import BD.AnuncioBD;
import BD.LogBD;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Anuncio;
import modelo.Usuario;
import vista.AnunciosVista;
import vista.CrearModificarAnuncioDialogVista;

public class AnuncioControlador {

    private AnunciosVista vista;
    private AnuncioBD anuncioBD;
    private LogBD logBD;
    private Usuario usuario;

    public AnuncioControlador(AnunciosVista vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.anuncioBD = new AnuncioBD();
        this.logBD = new LogBD();

        vista.addBtnNuevoAnuncioListener(e -> nuevoAnuncio());

        cargarAnuncios();
    }

    //Cargar anuncios
    private void cargarAnuncios() {

        vista.limpiarAnuncios();

        List<Anuncio> lista = anuncioBD.obtenerAnuncios();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Anuncio a : lista) {

            String fecha = "";
            if (a.getFechaPublicacion() != null) {
                fecha = sdf.format(a.getFechaPublicacion());
            }

            vista.agregarTarjetaAnuncio(
                    a.getIdAnuncio(),
                    a.getTitulo(),
                    a.getContenido(),
                    a.getTipo(),
                    a.getNombreAutor(),
                    fecha,
                    e -> editarAnuncio(a),
                    e -> eliminarAnuncio(a)
            );
        }
    }

    //Crear
    private void nuevoAnuncio() {

        if (usuario.getNivelPermiso() < 2) {
            JOptionPane.showMessageDialog(vista, "No tienes permisos");
            return;
        }

        CrearModificarAnuncioDialogVista dialog
                = new CrearModificarAnuncioDialogVista(null, true);

        new CrearModificarAnuncioControlador(dialog, usuario);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        cargarAnuncios();
    }

    //Editar
    private void editarAnuncio(Anuncio a) {

        if (usuario.getNivelPermiso() < 2) {
            JOptionPane.showMessageDialog(vista, "No tienes permisos");
            return;
        }

        CrearModificarAnuncioDialogVista dialog
                = new CrearModificarAnuncioDialogVista(null, true);

        new CrearModificarAnuncioControlador(dialog, usuario, a);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        cargarAnuncios();
    }

    //Eliminar
    private void eliminarAnuncio(Anuncio a) {

        if (usuario.getNivelPermiso() < 2) {
            JOptionPane.showMessageDialog(vista, "No tienes permisos");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                vista,
                "¿Eliminar anuncio?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        anuncioBD.eliminarAnuncio(a.getIdAnuncio());

        cargarAnuncios();
    }
}
