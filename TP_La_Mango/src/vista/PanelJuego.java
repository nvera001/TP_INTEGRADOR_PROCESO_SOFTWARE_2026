package vista;

import modelo.entidades.GameObject;
import modelo.entidades.Jugador;
import modelo.entidades.ParedSimple;
import modelo.nucleo.Matriz;
import modelo.entidades.Caja;
import modelo.nucleo.Posicion;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class PanelJuego extends JPanel {
    private final Matriz matriz;
    private final CargadorRecursos cargador;
    private final int TAMANIO_TILE = 64;

    public PanelJuego(Matriz matriz) {
        this.matriz = matriz;
        this.cargador = CargadorRecursos.getInstancia();

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
                int px = offsetX + (x * tileSize);
                int py = offsetY + (y * tileSize);

                Image imgPiso = cargador.getImagen("piso");
                if (imgPiso != null) {
                    g.drawImage(imgPiso, px, py, tileSize, tileSize, null);
                }

                if (matriz.esMeta(posActual)) {
                    Image imgMeta = cargador.getImagen("meta");
                    if (imgMeta != null) {
                        g.drawImage(imgMeta, px, py, tileSize, tileSize, null);
                    }
                } else if (matriz.esCerrojo(posActual)) {
                    Image imgCerrojo = cargador.getImagen("cerrojo");
                    if (imgCerrojo != null) {
                        g.drawImage(imgCerrojo, px, py, tileSize, tileSize, null);
                    }
                } else if (matriz.esResbaladizo(posActual)) {
                    Image imgResbaladizo = cargador.getImagen("resbaladizo");
                    if (imgResbaladizo != null) {
                        g.drawImage(imgResbaladizo, px, py, tileSize, tileSize, null);
                    }
                }

                if (matriz.esMuro(posActual) && !matriz.MurosAbiertos()) {
                    g.drawImage(cargador.getImagen("muro"), px, py, tileSize, tileSize, null);
                }

                GameObject obj = matriz.obtenerObjetoGrillaPura(posActual);

                if (obj instanceof ParedSimple) {
                    g.drawImage(cargador.getImagen("pared"), px, py, tileSize, tileSize, null);
                } else if (obj instanceof Jugador) {
                    g.drawImage(cargador.getImagen("jugador"), px, py, tileSize, tileSize, null);
                } else if (obj instanceof Caja) {
                    char simbolo = obj.getSimbolo();

                    switch (simbolo) {
                        case 'F' -> g.drawImage(cargador.getImagen("caja_fragil"), px, py, tileSize, tileSize, null);
                        case 'Z' -> g.drawImage(cargador.getImagen("caja_fragil_llave"), px, py, tileSize, tileSize, null);
                        case 'K' -> g.drawImage(cargador.getImagen("caja_llave"), px, py, tileSize, tileSize, null);
                        default -> {
                            if (matriz.esMeta(posActual)) {
                                g.drawImage(cargador.getImagen("caja_meta"), px, py, tileSize, tileSize, null);
                            } else {
                                g.drawImage(cargador.getImagen("caja"), px, py, tileSize, tileSize, null);
                            }
                        }
                    }
                }
            }
        }
    }
}