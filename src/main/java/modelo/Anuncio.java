package modelo;

import java.util.Date;

/**
 *
 * @author Roi Conles Ferro
 */
public class Anuncio {
    private int idAnuncio;
    private String titulo;
    private String contenido;
    private int idAutor;
    private Integer idDivision;
    private Date fechaPublicacion;
    private String tipo;
    private String nombreAutor;     //Para mostrar el nombre del autor y no el ID
    
    public Anuncio(){
        
    }

    public Anuncio(int idAnuncio, String titulo, String contenido, int idAutor, Integer idDivision, Date fechaPublicacion, String tipo) {
        this.idAnuncio = idAnuncio;
        this.titulo = titulo;
        this.contenido = contenido;
        this.idAutor = idAutor;
        this.idDivision = idDivision;
        this.fechaPublicacion = fechaPublicacion;
        this.tipo = tipo;
    }

    public int getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(int idAnuncio) {
        this.idAnuncio = idAnuncio;
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

    public Integer getIdDivision() {
        return idDivision;
    }

    public void setIdDivision(Integer idDivision) {
        this.idDivision = idDivision;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
}
