package Vista;

import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {

    // Interfaz para avisarle a la ventana cuándo arrancar el juego
    public interface AccionMenu {
        void alIniciarJuego();
    }

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

        // 2. Subtítulo / Bienvenida
        JLabel txtBienvenida = new JLabel("¡Bienvenido a TP La Mango!", SwingConstants.CENTER);
        txtBienvenida.setFont(fuenteSubtitulo);
        txtBienvenida.setForeground(Color.WHITE);
        gbc.gridy = 1;
        add(txtBienvenida, gbc);

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
        String textoReglas = "--- REGLAS DEL SOKOBAN ---\n\n"
                + "1. El objetivo es empujar todas las cajas hacia los destinos azules.\n"
                + "2. Solo podés empujar las cajas, no podés tirar de ellas.\n"
                + "3. No podés empujar más de una caja a la vez.\n"
                + "4. ¡Cuidado con las esquinas! Si trabás una caja ahí, no sale más.\n\n"
                + "Controles: Usá las flechas del teclado para moverte.";

        JOptionPane.showMessageDialog(this, textoReglas, "Reglas de Juego", JOptionPane.INFORMATION_MESSAGE);
    }
}