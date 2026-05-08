package modelo;

/**
 *
 * @author roi conles ferro
 */
public class Division {
    private int idDivision;
    private String nombre;
    private String descripcion;
    private int idResponsable;
    private String nombreResponsable;

    public Division() {
    }

    // Constructor completo
    public Division(int idDivision, String nombre, String descripcion, int idResponsable, String nombreResponsable) {
        this.idDivision = idDivision;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idResponsable = idResponsable;
        this.nombreResponsable = nombreResponsable;
    }

    // Constructor sin ID (para inserciones)
    public Division(String nombre, String descripcion, int idResponsable) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idResponsable = idResponsable;
    }

    public int getIdDivision() {
        return idDivision;
    }

    public void setIdDivision(int idDivision) {
        this.idDivision = idDivision;
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

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    @Override
    public String toString() {
        return "Division{" +
                "idDivision=" + idDivision +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", idResponsable=" + idResponsable +
                ", nombreResponsable='" + nombreResponsable + '\'' +
                '}';
    }
}
