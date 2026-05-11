package controlador;

import modelo.Informe;
import vista.VerInformeDialogVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author roi conles ferro
 */
public class VerInformeControlador {

    private VerInformeDialogVista vista;
    private Informe informe;

    public VerInformeControlador(VerInformeDialogVista vista, Informe informe) {
        this.vista = vista;
        this.informe = informe;

        vista.cargarInforme(informe);
        vista.addCerrarListener(cerrar());
    }

    private ActionListener cerrar() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        };
        return al;
    }
}
