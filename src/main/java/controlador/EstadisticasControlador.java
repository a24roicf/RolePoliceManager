package controlador;

import BD.EstadisticasBD;
import java.util.List;
import modelo.Estadistica;
import modelo.Usuario;
import vista.EstadisticasVista;

/**
 *
 * @author roi conles ferro
 */
public class EstadisticasControlador {
    private EstadisticasVista vista;
    private Usuario usuario;

    public EstadisticasControlador(EstadisticasVista vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;

        cargarDatos();
    }

    private void cargarDatos() {
        //Cargar resumen total, los 6 números
        int totalUsuarios = EstadisticasBD.getTotalUsuarios();
        int totalDivisiones = EstadisticasBD.getTotalDivisiones();
        int totalLicencias = EstadisticasBD.getTotalLicenciasAsignadas();
        int totalInformes = EstadisticasBD.getTotalInformes();
        int totalAnuncios = EstadisticasBD.getTotalAnuncios();
        int totalLogs = EstadisticasBD.getTotalLogs();

        vista.setResumen(totalUsuarios, totalDivisiones, totalLicencias, totalInformes, totalAnuncios, totalLogs);

        //Cargar gráfico de divisiones
        List<Estadistica> datosDivisiones = EstadisticasBD.getUsuariosPorDivision();
        vista.cargarGraficoDivisiones(datosDivisiones);

        //Cargar gráfico de licencias
        List<Estadistica> datosLicencias = EstadisticasBD.getLicenciasMasAsignadas();
        vista.cargarGraficoLicencias(datosLicencias);

        //Cargar gráfico de actividad
        List<Estadistica> datosActividad = EstadisticasBD.getActividadPorModulo();
        vista.cargarGraficoActividad(datosActividad);

        //Cargar gráfico de rangos
        List<Estadistica> datosRangos = EstadisticasBD.getUsuariosPorRango();
        vista.cargarGraficoRangos(datosRangos);
    }
}
