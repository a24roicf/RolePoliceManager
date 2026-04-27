package modelo;

public class Permiso {

    private int nivel;
    private String nombre;

    public Permiso(int nivel, String nombre) {
        this.nivel = nivel;
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
