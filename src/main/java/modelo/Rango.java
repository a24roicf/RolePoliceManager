package modelo;

/**
 *
 * @author alumno
 */
public class Rango {
    private int idRango;
    private String nombre;
    private int nivel;
    private double salario;

    public Rango(){}
    
    public Rango(int idRango, String nombre, int nivel, double salario) {
        this.idRango = idRango;
        this.nombre = nombre;
        this.nivel = nivel;
        this.salario = salario;
    }
    
    public int getIdRango() {
        return idRango;
    }

    public void setIdRango(int idRango) {
        this.idRango = idRango;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
