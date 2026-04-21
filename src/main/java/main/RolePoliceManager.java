package main;

import com.formdev.flatlaf.FlatDarkLaf;
import controlador.LoginControlador;
import vista.LoginVista;

public class RolePoliceManager {

    public static void main(String[] args) {

        // Tema visual
        FlatDarkLaf.setup();

        // Crear vista login
        LoginVista vistaLogin = new LoginVista();

        // Conectar controlador
        LoginControlador lc = new LoginControlador(vistaLogin);

        // Mostrar ventana
        vistaLogin.setLocationRelativeTo(null);
        vistaLogin.setVisible(true);
    }
}