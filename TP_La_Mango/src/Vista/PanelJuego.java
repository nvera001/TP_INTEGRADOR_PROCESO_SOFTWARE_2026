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
    private final int TAMANIO_TILE = 64;

    public PanelJuego(Matriz matriz) {
        this.matriz = matriz;
        this.cargador = new CargadorRecursos(); // Inicializa y carga las imágenes

        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Image imgFondo = cargador.getImagen("fondo");
        if (imgFondo != null) {
            int tileFondoSize = imgFondo.getWidth(null);
            for (int fx = 0; fx < getWidth(); fx += tileFondoSize) {
                for (int fy = 0; fy < getHeight(); fy += tileFondoSize) {
                    g.drawImage(imgFondo, fx, fy, this);
                }
            }
        } else {
            // Por las dudas, si no carga la imagen, que quede negro de respaldo
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        int columnas = matriz.getColumnas();
        int filas = matriz.getFilas();
        if (columnas == 0 || filas == 0) return;

        int tileAnchoMax = getWidth() / columnas;
        int tileAltoMax = getHeight() / filas;

        int tileSize = Math.min(tileAnchoMax, tileAltoMax);

        tileSize = Math.min(tileSize, TAMANIO_TILE);

        int mapaAnchoPixeles = columnas * tileSize;
        int mapaAltoPixeles = filas * tileSize;

        int offsetX = (getWidth() - mapaAnchoPixeles) / 2;
        int offsetY = (getHeight() - mapaAltoPixeles) / 2;

        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                Posicion posActual = new Posicion(x, y);
                GameObject obj = matriz.obtenerObjetoEn(posActual);
                int px = offsetX + (x * tileSize);
                int py = offsetY + (y * tileSize);

                // Capa 1: El Piso base (Siempre se dibuja)
                Image imgPiso = cargador.getImagen("piso");
                if (imgPiso != null) {
                    g.drawImage(imgPiso, px, py, tileSize, tileSize, null);
                }

                // Capa 2: Las Metas (Se dibujan arriba del piso)
                if (matriz.esMeta(posActual)) {
                    Image imgMeta = cargador.getImagen("meta");
                    if (imgMeta != null) {
                        g.drawImage(imgMeta, px, py, tileSize, tileSize, null);
                    }
                }

                // Capa 3: Las Entidades sólidas (Se dibujan arriba de todo)
                if (obj instanceof ParedSimple) {
                    g.drawImage(cargador.getImagen("pared"), px, py, tileSize, tileSize, null);
                } 
                else if (obj instanceof Jugador) {
                    g.drawImage(cargador.getImagen("jugador"), px, py, tileSize, tileSize, null);
                } 
                else if (obj instanceof CajaSimple) {
                    if (matriz.esMeta(posActual)) {
                        // Si la caja está en la meta, usamos el sprite especial de Kenney
                        g.drawImage(cargador.getImagen("caja_meta"), px, py, tileSize, tileSize, null);
                    } else {
                        g.drawImage(cargador.getImagen("caja"), px, py, tileSize, tileSize, null);
                    }
                }
            }
        }
    }
}