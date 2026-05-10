package modelo;

import java.util.Date;

/**
 *
 * @author roi conles ferro
 */
public class Informe {

    private int idInforme;
    private int idEvaluador;
    private int idEvaluado;
    private String contenido;
    private Date fecha;
    private String nombreEvaluador;
    private String nombreEvaluado;

    public Informe() {
    }

    public Informe(int idInforme, int idEvaluador, int idEvaluado, String contenido, Date fecha) {
        this.idInforme = idInforme;
        this.idEvaluador = idEvaluador;
        this.idEvaluado = idEvaluado;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public Informe(int idEvaluador, int idEvaluado, String contenido, Date fecha) {
        this.idEvaluador = idEvaluador;
        this.idEvaluado = idEvaluado;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public int getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(int idInforme) {
        this.idInforme = idInforme;
    }

    public int getIdEvaluador() {
        return idEvaluador;
    }

    public void setIdEvaluador(int idEvaluador) {
        this.idEvaluador = idEvaluador;
    }

    public int getIdEvaluado() {
        return idEvaluado;
    }

    public void setIdEvaluado(int idEvaluado) {
        this.idEvaluado = idEvaluado;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreEvaluador() {
        return nombreEvaluador;
    }

    public void setNombreEvaluador(String nombreEvaluador) {
        this.nombreEvaluador = nombreEvaluador;
    }

    public String getNombreEvaluado() {
        return nombreEvaluado;
    }

    public void setNombreEvaluado(String nombreEvaluado) {
        this.nombreEvaluado = nombreEvaluado;
    }

    @Override
    public String toString() {
        return "Informe{"
                + "idInforme=" + idInforme
                + ", nombreEvaluador='" + nombreEvaluador + '\''
                + ", nombreEvaluado='" + nombreEvaluado + '\''
                + ", fecha=" + fecha
                + '}';
    }
}
