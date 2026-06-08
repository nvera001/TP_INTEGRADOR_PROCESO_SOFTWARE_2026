package Modelo.Nucleo;

import Modelo.Entidades.GameObject;

public class Matriz {

    private final GameObject[][] grilla;
    private final int filas;
    private final int columnas;

    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.grilla = new GameObject[filas][columnas];
    }

    public void colocarObjeto(GameObject obj) {
        Posicion pos = obj.getPosicion();
        grilla[pos.getY()][pos.getX()] = obj;
    }

    public GameObject obtenerObjetoEn(Posicion pos) {
        if (pos.getX() < 0 || pos.getX() >= columnas || pos.getY() < 0 || pos.getY() >= filas) {
            return null; // Fuera de los límites
        }
        return grilla[pos.getY()][pos.getX()];
    }

    public void mostrarPorConsola() {
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                if (grilla[y][x] == null) {
                    System.out.print(". "); // Espacio vacío
                } else {
                    System.out.print(grilla[y][x].getSimbolo() + " ");
                }
            }
            System.out.println();
        }
    }

    public void moverObjeto(GameObject obj, Posicion nuevaPos) {
        Posicion viejaPos = obj.getPosicion();

        // Borramos de la posición vieja
        grilla[viejaPos.getY()][viejaPos.getX()] = null;

        // Lo ponemos en la posición nueva
        grilla[nuevaPos.getY()][nuevaPos.getX()] = obj;

        // Le actualizamos la posición interna al objeto
        obj.setPosicion(nuevaPos);
    }
}
