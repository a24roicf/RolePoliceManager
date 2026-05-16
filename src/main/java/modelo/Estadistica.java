package modelo;

/**
 *
 * @author roi conles ferro
 */
public class Estadistica {
    private String nombre;
    private int valor;
    private String categoria;

    public Estadistica() {
    }

    public Estadistica(String nombre, int valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public Estadistica(String nombre, int valor, String categoria) {
        this.nombre = nombre;
        this.valor = valor;
        this.categoria = categoria;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return nombre + ": " + valor;
    }
}