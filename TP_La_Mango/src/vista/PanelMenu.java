package vista;

import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {
    public interface AccionMenu {
        void alIniciarJuego();
    }
    private final CargadorRecursos cargador = CargadorRecursos.getInstancia();

    public PanelMenu(AccionMenu accion) {
        this.setBackground(new Color(30, 30, 30));
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuenteTitulo = new Font("Impact", Font.PLAIN, 72);
        Font fuenteSubtitulo = new Font("Segoe UI", Font.BOLD, 18);
        Font fuenteBotones = new Font("Segoe UI", Font.BOLD, 16);

        JLabel txtTitulo = new JLabel("SOKOBAN", SwingConstants.CENTER);
        txtTitulo.setFont(fuenteTitulo);
        txtTitulo.setForeground(new Color(243, 156, 18));
        gbc.gridy = 0;
        add(txtTitulo, gbc);

        gbc.insets = new Insets(40, 15, 15, 15);

        JButton btnJugar = crearBoton("INICIAR JUEGO", fuenteBotones, new Color(46, 204, 113));
        btnJugar.addActionListener(e -> accion.alIniciarJuego());
        gbc.gridy = 2;
        add(btnJugar, gbc);

        gbc.insets = new Insets(15, 15, 15, 15);

        JButton btnReglas = crearBoton("REGLAS DE JUEGO", fuenteBotones, new Color(52, 152, 219));
        btnReglas.addActionListener(e -> mostrarReglas());
        gbc.gridy = 3;
        add(btnReglas, gbc);

        JButton btnSalir = crearBoton("SALIR DEL JUEGO", fuenteBotones, new Color(231, 76, 60));
        btnSalir.addActionListener(e -> System.exit(0));
        gbc.gridy = 4;
        add(btnSalir, gbc);

        JButton btnVolumen = new JButton(GestorAudio.isSonidoActivo() ? "SONIDO: ON" : "SONIDO: OFF");
        btnVolumen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolumen.setForeground(Color.WHITE);
        btnVolumen.setBackground(GestorAudio.isSonidoActivo() ? new Color(52, 73, 94) : new Color(127, 140, 141));
        btnVolumen.setFocusPainted(false);
        btnVolumen.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        btnVolumen.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVolumen.addActionListener(e -> {
            GestorAudio.toggleSonido();
            if (GestorAudio.isSonidoActivo()) {
                btnVolumen.setText("SONIDO: ON");
                btnVolumen.setBackground(new Color(52, 73, 94));
            } else {
                btnVolumen.setText("SONIDO: OFF");
                btnVolumen.setBackground(new Color(127, 140, 141));
            }
        });
        gbc.gridy = 5;
        add(btnVolumen, gbc);
    }

    private JButton crearBoton(String texto, Font fuente, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        return boton;
    }

    private void mostrarReglas() {
        Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
        JDialog dialogoReglas = new JDialog(ventanaPadre, "Reglas", Dialog.ModalityType.APPLICATION_MODAL);
        dialogoReglas.setUndecorated(true);
        dialogoReglas.setSize(560, 460);
        dialogoReglas.setLocationRelativeTo(ventanaPadre);

        JPanel panelContenedor = new JPanel(new BorderLayout(15, 15));
        panelContenedor.setBackground(new Color(25, 25, 25));
        panelContenedor.setBorder(BorderFactory.createLineBorder(new Color(243, 156, 18), 3));

        JLabel txtTitulo = new JLabel("CÓMO JUGAR SOKOBAN", SwingConstants.CENTER);
        txtTitulo.setFont(new Font("Impact", Font.PLAIN, 32));
        txtTitulo.setForeground(new Color(243, 156, 18));
        txtTitulo.setBorder(BorderFactory.createEmptyBorder(25, 10, 10, 10));
        panelContenedor.add(txtTitulo, BorderLayout.NORTH);

        String htmlReglas = "<html>"
                + "<body style='color: white; font-family: \"Segoe UI\", sans-serif; font-size: 13px;'>"
                + "  <p style='text-align: center; color: #BDC3C7; margin-bottom: 20px; padding: 0 25px;'>"
                + "     El objetivo es simple pero desafiante: tenés que empujar todas las <b>cajas</b> hasta los <b>destinos azules</b>."
                + "  </p>"
                + "  <div style='padding-left: 45px; padding-right: 45px;'>"
                + "     <font color='#F1C40F'><b>• Múltiples cajas:</b></font> Varios tipos de caja, con funcionalidades distintas.<br><br>"
                + "     <font color='#F1C40F'><b>• Fuerza limitada:</b></font> No podés empujar más de una caja a la vez.<br><br>"
                + "     <font color='#F1C40F'><b>• Zonas trampa:</b></font> Si pegás una caja en una esquina, quedará bloqueada.<br><br>"
                + "     <font color='#F1C40F'><b>• Controles:</b></font> Usá las flechas del teclado para moverte.<br><br>"
                + "     <font color='#F1C40F'><b>• ¿Te trabaste?:</b></font> Presioná la tecla <b>[R]</b> para reiniciar el nivel."
                + "  </div>"
                + "</body>"
                + "</html>";

        JLabel txtCuerpo = new JLabel(htmlReglas);
        txtCuerpo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelContenedor.add(txtCuerpo, BorderLayout.CENTER);

        JButton btnEntendido = new JButton("ENTENDIDO, ¡A JUGAR!");
        btnEntendido.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntendido.setForeground(Color.WHITE);
        btnEntendido.setBackground(new Color(46, 204, 113));
        btnEntendido.setFocusPainted(false);
        btnEntendido.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        btnEntendido.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEntendido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btnEntendido.setBackground(new Color(39, 174, 96)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btnEntendido.setBackground(new Color(46, 204, 113)); }
        });

        btnEntendido.addActionListener(e -> dialogoReglas.dispose());

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 45, 30, 45));
        panelInferior.add(btnEntendido, BorderLayout.CENTER);

        panelContenedor.add(panelInferior, BorderLayout.SOUTH);

        dialogoReglas.setContentPane(panelContenedor);
        dialogoReglas.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image imgFondo = cargador.getImagen("fondo");

        if (imgFondo != null) {
            int anchoFondo = imgFondo.getWidth(null);
            int altoFondo = imgFondo.getHeight(null);

            for (int x = 0; x < getWidth(); x += anchoFondo) {
                for (int y = 0; y < getHeight(); y += altoFondo) {
                    g.drawImage(imgFondo, x, y, this);
                }
            }
        }
    }
}