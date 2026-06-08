package Modelo.Nucleo;

import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.GameObject;
import java.util.ArrayList;
import java.util.List;

public class Matriz {

    private final GameObject[][] grilla;
    private final int filas;
    private final int columnas;

    private final List<Posicion> metas;

    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.grilla = new GameObject[filas][columnas];
        this.metas = new ArrayList<>();
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

    public void moverObjeto(GameObject obj, Posicion nuevaPos) {
        Posicion viejaPos = obj.getPosicion();

        // Borramos de la posición vieja
        grilla[viejaPos.getY()][viejaPos.getX()] = null;

        // Lo ponemos en la posición nueva
        grilla[nuevaPos.getY()][nuevaPos.getX()] = obj;

        // Le actualizamos la posición interna al objeto
        obj.setPosicion(nuevaPos);
    }

    public void registrarMeta(Posicion pos) {
        if (!metas.contains(pos)) {
            metas.add(pos);
        }
    }

    public boolean esMeta(Posicion pos) {
        return metas.contains(pos);
    }

    public boolean estanTodasLasMetasCubiertas() {
        if (metas.isEmpty()) return false;
        
        for (Posicion posMeta : metas) {
            GameObject obj = obtenerObjetoEn(posMeta);
            // Si en la posición de la meta NO hay una caja, todavía no ganaste
            if (!(obj instanceof CajaSimple)) {
                return false; 
            }
        }
        return true; // Todas las metas tienen una caja encima
    }

    public void mostrarPorConsola() {
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                if (grilla[y][x] == null) {
                    System.out.print(esMeta(new Posicion(x, y)) ? "X " : ". ");
                } else {
                    System.out.print(grilla[y][x].getSimbolo() + " ");
                }
            }
            System.out.println();
        }
    }
}

