package Vista;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Controlador.GestorJuego;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;

public class VentanaPrincipal extends JFrame {
    private final PanelJuego panelJuego;

    public VentanaPrincipal(GestorJuego gestor, Matriz matriz) {
        setTitle("Sokoban - TP La Mango");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        panelJuego = new PanelJuego(matriz);
        add(panelJuego);
        pack(); // Ajusta la ventana al tamaño del panel
        setLocationRelativeTo(null); // Centra la ventana

        // Capturador de teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> gestor.intentarMover(Direccion.ARRIBA);
                    case KeyEvent.VK_DOWN -> gestor.intentarMover(Direccion.ABAJO);
                    case KeyEvent.VK_LEFT -> gestor.intentarMover(Direccion.IZQUIERDA);
                    case KeyEvent.VK_RIGHT -> gestor.intentarMover(Direccion.DERECHA);
                }
                actualizarPantalla();
            }
        });
    }

    public void actualizarPantalla() {
        panelJuego.repaint(); // Fuerza a Swing a redibujar el panel
    }
}