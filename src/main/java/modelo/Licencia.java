package modelo;

import java.util.Date;

/**
 *
 * @author roi conles ferro
 */
public class Licencia {
    private int idLicencia;
    private String nombre;
    private String descripcion;
    private Date fechaObtencion;

    public Licencia() {
    }

    public Licencia(int idLicencia, String nombre, String descripcion) {
        this.idLicencia = idLicencia;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Licencia(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdLicencia() {
        return idLicencia;
    }

    public void setIdLicencia(int idLicencia) {
        this.idLicencia = idLicencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaObtencion() {
        return fechaObtencion;
    }

    public void setFechaObtencion(Date fechaObtencion) {
        this.fechaObtencion = fechaObtencion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
