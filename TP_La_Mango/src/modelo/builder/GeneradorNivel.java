package modelo.builder;

import modelo.nucleo.Matriz;
import modelo.nucleo.Posicion;

public class GeneradorNivel {
    private MatrizBuilder builder;

    public GeneradorNivel() {
        this.builder = new SokobanBuilderMatriz();
    }

    public void setBuilder(MatrizBuilder builder) {
        this.builder = builder;
    }

    public Matriz generarMatrizDesdeString(String mapaString) {
        String[] lineas = mapaString.split("\\r?\\n");

        int filas = lineas.length;
        int columnas = lineas[0].length();

        builder.reset(filas, columnas);

        for (int y = 0; y < filas; y++) {
            String filaActual = lineas[y];

            for (int x = 0; x < filaActual.length(); x++) {
                char caracterActual = filaActual.charAt(x);
                Posicion pos = new Posicion(x, y);

                switch (caracterActual) {
                    case '#' -> builder.construirPared(pos);
                    case 'P' -> builder.construirJugador(pos);
                    case 'X' -> builder.construirMeta(pos);
                    case 'S' -> builder.construirTerrenoResbaladizo(pos);
                    case 'L' -> builder.construirCerrojo(pos);
                    case 'M' -> builder.construirMuroCerrado(pos);
                    case 'C', 'F', 'K', 'Z' -> builder.construirCaja(caracterActual, pos);
                    default -> {
                    }
                }
            }
        }

        return builder.getResultado();
    }
}