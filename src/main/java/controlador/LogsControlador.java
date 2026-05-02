package controlador;

import BD.LogBD;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import modelo.Log;
import modelo.Usuario;
import vista.LogsVista;

/**
 *
 * @author Roi Conles Ferro
 */
public class LogsControlador {

    private LogsVista vista;
    private LogBD logBD;
    private Usuario usuarioLogueado;

    public LogsControlador(LogsVista vista, Usuario usuarioLogueado) {
        this.vista = vista;
        this.logBD = new LogBD();
        this.usuarioLogueado = usuarioLogueado;

        if (usuarioLogueado.getNivelPermiso() != 3) {
            vista.dispose();
            return;
        }

        vista.addBuscarListener(e -> cargarLogs());

        cargarLogs();
    }

    private void cargarLogs() {

        String texto = vista.getTextoBusqueda();
        Date desde = vista.getFechaDesde();
        Date hasta = vista.getFechaHasta();

        List<Log> lista = logBD.obtenerLogsFiltrados(texto, desde, hasta);

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Usuario");
        modelo.addColumn("Acción");
        modelo.addColumn("Módulo");
        modelo.addColumn("Fecha");
        modelo.addColumn("Descripción");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Log l : lista) {

            String fecha = "";
            if (l.getFechaHora() != null) {
                fecha = sdf.format(l.getFechaHora());
            }

            modelo.addRow(new Object[]{
                l.getIdUsuario(),
                l.getTipoAccion(),
                l.getModulo(),
                fecha,
                l.getDescripcion()
            });
        }

        vista.getTabla().setModel(modelo);
    }
}
