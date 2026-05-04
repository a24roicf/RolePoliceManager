package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author usuario
 */
public class AnunciosVista extends javax.swing.JPanel {

    /**
     * Creates new form AnunciosVista
     */
    public AnunciosVista() {
        initComponents();
        configurarPanel();
    }
    
    private void configurarPanel(){
        panelAnuncios.setLayout(new BoxLayout(panelAnuncios, BoxLayout.Y_AXIS));
        panelAnuncios.setBackground(Color.DARK_GRAY);
    }

    public void limpiarAnuncios() {
        panelAnuncios.removeAll();
        panelAnuncios.revalidate();
        panelAnuncios.repaint();
    }

    public void addBtnNuevoAnuncioListener(ActionListener listener) {
        btnNuevoAnuncio.addActionListener(listener);
    }

    public void agregarTarjetaAnuncio(int idAnuncio, String titulo, String contenido,
            String tipo, String autor, String fecha,
            ActionListener editarListener,
            ActionListener eliminarListener) {
        
        //Panel principal de la tarjeta
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout(10, 10));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        tarjeta.setBackground(Color.WHITE);
        
        //Panel superior -> Tipo de anuncio + titulo
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        
        //Etiqueta de tipo (con colorines)
        JLabel lblTipo = new JLabel(obtenerIconoTipo(tipo) + " " + tipo.toUpperCase());
        lblTipo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTipo.setForeground(obtenerColorTipo(tipo));
        
        panelSuperior.add(lblTipo, BorderLayout.WEST);
        
        //Titulo del anuncio
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        
        //Panel central (contenido del anuncio)
        JTextArea txtContenido = new JTextArea(titulo);
        txtContenido.setEditable(false);
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        txtContenido.setFont(new Font("Arial", Font.PLAIN, 13));
        txtContenido.setRows(3);
        txtContenido.setOpaque(false);
        txtContenido.setBorder(null);
        
        //Panel inferior (autor + fecha + botones de editar o eliminar)
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);
        
        //Info del autor y la fecha
        JLabel lblInfo = new JLabel("Por: " + autor + " | " + fecha);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(Color.LIGHT_GRAY);
        
        //Panel de los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5,0));
        panelBotones.setOpaque(false);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setFocusPainted(false);
        btnEditar.addActionListener(editarListener);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFocusPainted(false);
        btnEliminar.setForeground(new Color(200,0,0));
        btnEliminar.addActionListener(eliminarListener);
        
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        
        panelInferior.add(lblInfo, BorderLayout.WEST);
        panelInferior.add(panelBotones, BorderLayout.EAST);
        
        //Colocar la tarjeta
        JPanel panelContenido = new JPanel(new BorderLayout(5,5));
        panelContenido.setOpaque(false);
        panelContenido.add(panelSuperior, BorderLayout.NORTH);
        panelContenido.add(lblTitulo, BorderLayout.CENTER);
        
        tarjeta.add(panelContenido, BorderLayout.NORTH);
        tarjeta.add(txtContenido, BorderLayout.CENTER);
        tarjeta.add(panelInferior, BorderLayout.SOUTH);
        
        //Añadimos al panel principal
        panelAnuncios.add(tarjeta);
        panelAnuncios.add(Box.createRigidArea(new Dimension(0,10)));    //Espacio entre cada tarjeta
    }
    
    private String obtenerIconoTipo(String tipo){
        switch (tipo.toLowerCase()){
            case "urgente":
                return "⚠️";
            case "general":
                return "";
            case "informativo":
                return "";
            default:
                return "";
        }
    }
    
    private Color obtenerColorTipo(String tipo){
        switch (tipo.toLowerCase()){
            case "urgente":
                return new Color(220,53,69);    //Rojo
            case "general":
                return new Color(0,123,255);    //Azul
            case "informativo":
                return new Color(40,167,69);    //Verde
            default:
                return Color.DARK_GRAY;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNuevoAnuncio = new javax.swing.JButton();
        scrollAnuncios = new javax.swing.JScrollPane();
        panelAnuncios = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        btnNuevoAnuncio.setText("Nuevo Anuncio");
        jPanel1.add(btnNuevoAnuncio);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        panelAnuncios.setLayout(new javax.swing.BoxLayout(panelAnuncios, javax.swing.BoxLayout.Y_AXIS));
        scrollAnuncios.setViewportView(panelAnuncios);

        add(scrollAnuncios, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevoAnuncio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelAnuncios;
    private javax.swing.JScrollPane scrollAnuncios;
    // End of variables declaration//GEN-END:variables
}
