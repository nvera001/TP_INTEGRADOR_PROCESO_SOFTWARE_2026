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
        // 1. Dividir el string por saltos de línea para tener un array de filas
        // Funciona tanto con saltos de línea de Windows (\r\n) como de Linux (\n)
        String[] lineas = mapaString.split("\\r?\\n");

        int filas = lineas.length;
        // Asumimos que todas las líneas tienen el mismo ancho (nivel rectangular)
        int columnas = lineas[0].length();

        // 2. Instanciar tu nueva Matriz con las dimensiones correctas
        Matriz tablero = new Matriz(filas, columnas);

        // 3. Recorrer usando coordenadas X (columnas) e Y (filas)
        for (int y = 0; y < filas; y++) {
            String filaActual = lineas[y];

            for (int x = 0; x < filaActual.length(); x++) {
                char caracterActual = filaActual.charAt(x);
                Posicion pos = new Posicion(x, y);

                // Le pedimos al traductor que nos dé el objeto para esa posición
                GameObject nuevoObjeto = traductor.instanciarDesdeCaracter(caracterActual, pos);

                // Si no es null (es decir, no era un espacio vacío '.'), lo colocamos
                if (nuevoObjeto != null) {
                    tablero.colocarObjeto(nuevoObjeto);
                }
            }
        }

        return tablero;
    }
}
