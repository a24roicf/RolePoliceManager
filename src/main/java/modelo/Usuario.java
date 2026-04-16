package modelo;

import java.util.Date;

/**
 *
 * @author alumno
 */
public class Usuario {
    private int idUsuario;
    private String nombreRol;
    private String email;
    private String password;
    private int idRango;
    private Date fechaIngreso;
    private Date fechaUltimoAscenso;
    private String estado;
    private int nivelPermiso;

    public Usuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdRango() {
        return idRango;
    }

    public void setIdRango(int idRango) {
        this.idRango = idRango;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaUltimoAscenso() {
        return fechaUltimoAscenso;
    }

    public void setFechaUltimoAscenso(Date fechaUltimoAscenso) {
        this.fechaUltimoAscenso = fechaUltimoAscenso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNivelPermiso() {
        return nivelPermiso;
    }

    public void setNivelPermiso(int nivelPermiso) {
        this.nivelPermiso = nivelPermiso;
    }
    
}
