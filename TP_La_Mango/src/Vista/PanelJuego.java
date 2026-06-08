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
import java.awt.Image;
import javax.swing.JPanel;

public class PanelJuego extends JPanel {
    private final Matriz matriz;
    private final CargadorRecursos cargador;
    private final int TAMANIO_TILE = 64; // El tamaño nativo de los sprites de Kenney

    public PanelJuego(Matriz matriz) {
        this.matriz = matriz;
        this.cargador = new CargadorRecursos(); // Inicializa y carga las imágenes
        
        this.setPreferredSize(new Dimension(5 * TAMANIO_TILE, 5 * TAMANIO_TILE));
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Renderizado por capas (Para evitar bugs visuales)
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Posicion posActual = new Posicion(x, y);
                GameObject obj = matriz.obtenerObjetoEn(posActual);
                int px = x * TAMANIO_TILE;
                int py = y * TAMANIO_TILE;

                // Capa 1: El Piso base (Siempre se dibuja)
                Image imgPiso = cargador.getImagen("piso");
                if (imgPiso != null) {
                    g.drawImage(imgPiso, px, py, TAMANIO_TILE, TAMANIO_TILE, null);
                }

                // Capa 2: Las Metas (Se dibujan arriba del piso)
                if (matriz.esMeta(posActual)) {
                    Image imgMeta = cargador.getImagen("meta");
                    if (imgMeta != null) {
                        g.drawImage(imgMeta, px, py, TAMANIO_TILE, TAMANIO_TILE, null);
                    }
                }

                // Capa 3: Las Entidades sólidas (Se dibujan arriba de todo)
                if (obj instanceof ParedSimple) {
                    g.drawImage(cargador.getImagen("pared"), px, py, TAMANIO_TILE, TAMANIO_TILE, null);
                } 
                else if (obj instanceof Jugador) {
                    g.drawImage(cargador.getImagen("jugador"), px, py, TAMANIO_TILE, TAMANIO_TILE, null);
                } 
                else if (obj instanceof CajaSimple) {
                    if (matriz.esMeta(posActual)) {
                        // Si la caja está en la meta, usamos el sprite especial de Kenney
                        g.drawImage(cargador.getImagen("caja_meta"), px, py, TAMANIO_TILE, TAMANIO_TILE, null);
                    } else {
                        g.drawImage(cargador.getImagen("caja"), px, py, TAMANIO_TILE, TAMANIO_TILE, null);
                    }
                }
            }
        }
    }
}