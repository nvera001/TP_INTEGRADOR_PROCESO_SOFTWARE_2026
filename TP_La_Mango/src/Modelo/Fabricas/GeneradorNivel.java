package Modelo.Fabricas;

import Modelo.Archivos.TraductorTXT;
import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Matriz;
import Modelo.Nucleo.Posicion;

public class GeneradorNivel {

    private TraductorTXT traductor;

    public GeneradorNivel() {
        this.traductor = new TraductorTXT();
    }

    public Matriz generarMatrizDesdeString(String mapaString) {
        String[] lineas = mapaString.split("\\r?\\n");

        int filas = lineas.length;
        int columnas = lineas[0].length();

        Matriz tablero = new Matriz(filas, columnas);

        for (int y = 0; y < filas; y++) {
            String filaActual = lineas[y];

            for (int x = 0; x < filaActual.length(); x++) {
                char caracterActual = filaActual.charAt(x);
                Posicion pos = new Posicion(x, y);

                GameObject nuevoObjeto = traductor.instanciarDesdeCaracter(caracterActual, pos);

                if (nuevoObjeto != null) {
                    tablero.colocarObjeto(nuevoObjeto);
                }
            }
        }
        return tablero;
    }
}
