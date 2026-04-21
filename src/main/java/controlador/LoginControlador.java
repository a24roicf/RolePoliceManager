package controlador;

import BD.UsuarioBD;
import modelo.Usuario;
import vista.LoginVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vista.PrincipalVista;

public class LoginControlador {

    private LoginVista vista;
    private UsuarioBD usuarioBD;

    public LoginControlador(LoginVista vista) {
        this.vista = vista;
        this.usuarioBD = new UsuarioBD();
        this.vista.addIniciarSesionButtonListener(getLoginAction());
    }

    private ActionListener getLoginAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email = vista.getEmailTextField();
                String password = vista.getPassword();

                Usuario u = usuarioBD.login(email, password);

                if (u != null) {
                    JOptionPane.showMessageDialog(vista, "Login correcto");

                    vista.dispose();

                    PrincipalVista pv = new PrincipalVista(u);
                    pv.setLocationRelativeTo(null);
                    pv.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(vista, "Credenciales incorrectas");
                }
            }
        };
    }
}