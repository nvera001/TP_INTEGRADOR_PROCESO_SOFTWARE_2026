package vista;

import modelo.entidades.GameObject;
import modelo.nucleo.Matriz;
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

                // 1. Siempre se dibuja el piso base
                Image imgPiso = cargador.getImagen("piso");
                if (imgPiso != null) {
                    g.drawImage(imgPiso, px, py, tileSize, tileSize, null);
                }

                // 2. Capa Estática de Suelo (Polimórfica)
                if (matriz.esMeta(posActual)) {
                    g.drawImage(cargador.getImagen("meta"), px, py, tileSize, tileSize, null);
                } else if (matriz.esCerrojo(posActual)) {
                    g.drawImage(cargador.getImagen("cerrojo"), px, py, tileSize, tileSize, null);
                } else if (matriz.esResbaladizo(posActual)) {
                    g.drawImage(cargador.getImagen("resbaladizo"), px, py, tileSize, tileSize, null);
                }

                // 3. Capa de Elementos Superiores (100% OCP sin instanceof)
                GameObject obj = matriz.obtenerObjetoGrillaPura(posActual);
                if (obj != null) {
                    char simbolo = obj.getSimbolo();
                    String claveImagen = null;

                    switch (simbolo) {
                        case '@' -> claveImagen = "jugador";
                        case '#' -> claveImagen = "pared";
                        case 'F' -> claveImagen = "caja_fragil";
                        case 'Z' -> claveImagen = "caja_fragil_llave";
                        case 'K' -> claveImagen = "caja_llave";
                        case 'M' -> {
                            if (!matriz.MurosAbiertos()) {
                                claveImagen = "muro";
                            }
                        }
                        case '$' -> {
                            if (matriz.esMeta(posActual)) {
                                claveImagen = "caja_meta";
                            } else {
                                claveImagen = "caja";
                            }
                        }
                    }

                    if (claveImagen != null) {
                        g.drawImage(cargador.getImagen(claveImagen), px, py, tileSize, tileSize, null);
                    }
                }
            }
        }
    }
}