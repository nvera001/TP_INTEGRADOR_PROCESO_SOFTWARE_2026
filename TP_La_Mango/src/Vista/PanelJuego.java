package Vista;

import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.GameObject;
import Modelo.Entidades.Jugador;
import Modelo.Entidades.ParedSimple;
import Modelo.Nucleo.Matriz;
import Modelo.Nucleo.Posicion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelJuego extends JPanel {
    private final Matriz matriz;
    private final int TAMANIO_TILE = 64; // Cada casilla mide 64x64 píxeles

    public PanelJuego(Matriz matriz) {
        this.matriz = matriz;
        // Definimos un tamaño fijo para nuestro mapa de 5x5 por ahora
        this.setPreferredSize(new Dimension(5 * TAMANIO_TILE, 5 * TAMANIO_TILE));
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Recorremos la matriz y dibujamos cada entidad
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                GameObject obj = matriz.obtenerObjetoEn(new Posicion(x, y));
                int px = x * TAMANIO_TILE;
                int py = y * TAMANIO_TILE;

                if (obj instanceof ParedSimple) {
                    g.setColor(Color.GRAY);
                    g.fillRect(px, py, TAMANIO_TILE, TAMANIO_TILE);
                } else if (obj instanceof Jugador) {
                    g.setColor(Color.RED);
                    g.fillOval(px + 4, py + 4, TAMANIO_TILE - 8, TAMANIO_TILE - 8);
                } else if (obj instanceof CajaSimple) {
                    g.setColor(Color.ORANGE);
                    g.fillRect(px + 8, py + 8, TAMANIO_TILE - 16, TAMANIO_TILE - 16);
                }
            }
        }
    }
}