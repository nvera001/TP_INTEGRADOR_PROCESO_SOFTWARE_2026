package Vista;

import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {
    public interface AccionMenu {
        void alIniciarJuego();
    }
    private final CargadorRecursos cargador = new CargadorRecursos();
    public PanelMenu(AccionMenu accion) {
        this.setBackground(new Color(30, 30, 30)); // Fondo gris oscuro elegante
        this.setLayout(new GridBagLayout()); // Centra todo en el medio de la pantalla

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 15, 15, 15); // Espaciado entre botones
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- FUENTES ---
        Font fuenteTitulo = new Font("Impact", Font.PLAIN, 72);
        Font fuenteSubtitulo = new Font("Segoe UI", Font.BOLD, 18);
        Font fuenteBotones = new Font("Segoe UI", Font.BOLD, 16);

        // 1. Título Principal
        JLabel txtTitulo = new JLabel("SOKOBAN", SwingConstants.CENTER);
        txtTitulo.setFont(fuenteTitulo);
        txtTitulo.setForeground(new Color(243, 156, 18)); // Naranja Gaming
        gbc.gridy = 0;
        add(txtTitulo, gbc);

        // Espacio estético antes de los botones
        gbc.insets = new Insets(40, 15, 15, 15);

        // 3. Botón Iniciar Juego (Verde)
        JButton btnJugar = crearBoton("INICIAR JUEGO", fuenteBotones, new Color(46, 204, 113));
        btnJugar.addActionListener(e -> accion.alIniciarJuego());
        gbc.gridy = 2;
        add(btnJugar, gbc);

        gbc.insets = new Insets(15, 15, 15, 15);

        // 4. Botón Reglas de Juego (Azul)
        JButton btnReglas = crearBoton("REGLAS DE JUEGO", fuenteBotones, new Color(52, 152, 219));
        btnReglas.addActionListener(e -> mostrarReglas());
        gbc.gridy = 3;
        add(btnReglas, gbc);

        // 5. Botón Salir (Rojo)
        JButton btnSalir = crearBoton("SALIR DEL JUEGO", fuenteBotones, new Color(231, 76, 60));
        btnSalir.addActionListener(e -> System.exit(0));
        gbc.gridy = 4;
        add(btnSalir, gbc);
    }

    // Método auxiliar para estilizar los botones y que no tengan el look viejo de Windows
    private JButton crearBoton(String texto, Font fuente, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40)); // Padding
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de manito
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        return boton;
    }

    private void mostrarReglas() {
        // 1. Creamos un diálogo flotante (modal) acoplado a la ventana principal
        Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
        JDialog dialogoReglas = new JDialog(ventanaPadre, "Reglas", Dialog.ModalityType.APPLICATION_MODAL);
        dialogoReglas.setUndecorated(true); // Le quitamos los bordes feos de Windows
        dialogoReglas.setSize(550, 450);
        dialogoReglas.setLocationRelativeTo(ventanaPadre); // Lo centramos perfecto

        // 2. Panel principal del cartel (Fondo negro traslúcido elegante)
        JPanel panelContenedor = new JPanel(new BorderLayout(20, 20));
        panelContenedor.setBackground(new Color(20, 20, 20, 240)); // Un gris casi negro con opacidad
        panelContenedor.setBorder(BorderFactory.createLineBorder(new Color(243, 156, 18), 3)); // Borde naranja gaming

        // 3. TÍTULO DEL CARTEL
        JLabel txtTitulo = new JLabel("CÓMO JUGAR SOKOBAN", SwingConstants.CENTER);
        txtTitulo.setFont(new Font("Impact", Font.PLAIN, 32));
        txtTitulo.setForeground(new Color(243, 156, 18));
        txtTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        panelContenedor.add(txtTitulo, BorderLayout.NORTH);

        // 4. CUERPO DE TEXTO (Usamos HTML para que quede espaciado e impecable)
        String htmlReglas = "<html><body style='text-align: center; color: white; font-family: \"Segoe UI\"; font-size: 13px;'>"
                + "<p style='margin-bottom: 15px;'>El objetivo es simple pero desafiante: tenés que empujar todas las <b>cajas</b> hasta los <b>destinos azules</b>.</p>"
                + "<div style='text-align: left; padding-left: 40px; line-height: 1.6;'>"
                + "🔹 <b>Empujar, no tirar:</b> Solo podés mover las cajas hacia adelante.<br>"
                + "🔹 <b>Fuerza limitada:</b> No podés empujar más de una caja a la vez.<br>"
                + "⚠️ <b>Zonas trampa:</b> Si pegás una caja en una esquina, quedará bloqueada.<br>"
                + "⌨️ <b>Controles:</b> Usá las flechas del teclado para moverte.<br>"
                + "🔄 <b>¿Te trabaste?:</b> Presioná la tecla <b>[R]</b> para reiniciar el nivel."
                + "</div>"
                + "</body></html>";

        JLabel txtCuerpo = new JLabel(htmlReglas, SwingConstants.CENTER);
        txtCuerpo.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        panelContenedor.add(txtCuerpo, BorderLayout.CENTER);

        // 5. BOTÓN DE CIERRE (Mismo estilo que tus botones del menú)
        JButton btnEntendido = new JButton("ENTENDIDO, ¡A JUGAR!");
        btnEntendido.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntendido.setForeground(Color.WHITE);
        btnEntendido.setBackground(new Color(46, 204, 113)); // Verde pro
        btnEntendido.setFocusPainted(false);
        btnEntendido.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        btnEntendido.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Al hacer clic, simplemente destruimos el cartel flotante
        btnEntendido.addActionListener(e -> dialogoReglas.dispose());

        // Panel contenedor inferior para darle aire al botón
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(20, 20, 20, 0)); // Transparente
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 40, 25, 40));
        panelInferior.add(btnEntendido, BorderLayout.CENTER);

        panelContenedor.add(panelInferior, BorderLayout.SOUTH);

        // 6. Seteamos el panel y mostramos
        dialogoReglas.setContentPane(panelContenedor);
        dialogoReglas.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image imgFondo = cargador.getImagen("fondo");

        if (imgFondo != null) {
            int anchoFondo = imgFondo.getWidth(null);
            int altoFondo = imgFondo.getHeight(null);

            // Bucles para rellenar absolutamente toda la pantalla gigante en mosaico
            for (int x = 0; x < getWidth(); x += anchoFondo) {
                for (int y = 0; y < getHeight(); y += altoFondo) {
                    g.drawImage(imgFondo, x, y, this);
                }
            }
        }
    }
}