package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Controlador.GestorJuego;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;

public class VentanaPrincipal extends JFrame {
    private final GestorJuego gestor;
    private boolean juegoIniciado = false;

    public VentanaPrincipal(GestorJuego gestor) {
        this.gestor = gestor;

        setTitle("Sokoban - TP La Mango");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));

        mostrarMenuPrincipal();

        // Capturador de teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (!juegoIniciado) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> gestor.intentarMover(Direccion.ARRIBA);
                    case KeyEvent.VK_DOWN -> gestor.intentarMover(Direccion.ABAJO);
                    case KeyEvent.VK_LEFT -> gestor.intentarMover(Direccion.IZQUIERDA);
                    case KeyEvent.VK_RIGHT -> gestor.intentarMover(Direccion.DERECHA);
                    case KeyEvent.VK_R -> gestor.reiniciarNivel();
                    case KeyEvent.VK_M -> gestor.volverAlMenu();
                }
            }
        });
        setVisible(true);
    }

    public void mostrarMenuPrincipal() {
        juegoIniciado = false;
        PanelMenu menu = new PanelMenu(() -> gestor.iniciarJuego());
        setContentPane(menu);
        revalidate();
        repaint();
    }

    public void mostrarPantallaJuego(Matriz matriz) {
        // 1. Contenedor principal del juego (BorderLayout)
        JPanel panelContenedorJuego = new JPanel(new BorderLayout());
        panelContenedorJuego.setBackground(Color.BLACK);

        // 2. Creamos el HUD superior (Barra de herramientas)
        JPanel hudSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        hudSuperior.setBackground(new Color(40, 40, 40));

        JButton btnReiniciar = new JButton("REINICIAR [R]");
        btnReiniciar.setBackground(new Color(230, 126, 34)); // Naranja
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusable(false); // CRÍTICO: evita que el botón le robe el foco al teclado
        btnReiniciar.addActionListener(e -> gestor.reiniciarNivel());

        JButton btnMenu = new JButton("MENÚ PRINCIPAL [M]");
        btnMenu.setBackground(new Color(149, 165, 166)); // Gris
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setFocusable(false);
        btnMenu.addActionListener(e -> gestor.volverAlMenu());

        hudSuperior.add(btnReiniciar);
        hudSuperior.add(btnMenu);
        panelContenedorJuego.add(hudSuperior, BorderLayout.NORTH);

        // 3. Centralizador del mapa de Kenney
        PanelJuego panelJuego = new PanelJuego(matriz);
        panelContenedorJuego.add(panelJuego, BorderLayout.CENTER);

        // 4. Cambiamos la interfaz de la ventana
        setContentPane(panelContenedorJuego);
        juegoIniciado = true;

        revalidate();
        repaint();
        requestFocusInWindow();
    }

    public void actualizarPantalla() {
        repaint();
    }
}
