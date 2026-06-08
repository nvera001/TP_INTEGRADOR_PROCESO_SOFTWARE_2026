package Modelo.Fabricas;

import Modelo.Archivos.TraductorTXT;

public class ConstructorNivel {
    private TraductorTXT traductor = new TraductorTXT();

    public void generarNivel(String mapaString) {
        int x = 0;
        int y = 0;

        for (int i = 0; i < mapaString.length(); i++) {
            char caracterActual = mapaString.charAt(i);

            if (caracterActual == '\n') {
                y++;
                x = 0;
                continue;
            }

            int TAMANO_TILE = 32;
            int posicionXReal = x * TAMANO_TILE;
            int posicionYReal = y * TAMANO_TILE;

            traductor.instanciarDesdeCaracter(caracterActual, posicionXReal, posicionYReal);

            x++;
        }
    }
}
