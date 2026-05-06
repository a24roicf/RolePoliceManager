package modelo;

import java.util.Date;

public class Normativa {

    private int idNormativa;
    private String titulo;
    private String contenido;
    private int idAutor;
    private Date fechaPublicacion;

    private String nombreAutor;

    public Normativa() {
    }

    public Normativa(int idNormativa, String titulo, String contenido,
            int idAutor, Date fechaPublicacion) {
        this.idNormativa = idNormativa;
        this.titulo = titulo;
        this.contenido = contenido;
        this.idAutor = idAutor;
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getIdNormativa() {
        return idNormativa;
    }

    public void setIdNormativa(int idNormativa) {
        this.idNormativa = idNormativa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
}
