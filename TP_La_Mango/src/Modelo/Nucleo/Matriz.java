package Modelo.Nucleo;

import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.GameObject;
import Modelo.Entidades.Jugador;
import Modelo.Entidades.Meta;

import java.util.ArrayList;
import java.util.List;

public class Matriz {

    private final GameObject[][] grilla;
    private final int filas;
    private final int columnas;
    private final List<Meta> metas;

    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.grilla = new GameObject[filas][columnas];
        this.metas = new ArrayList<>();
    }

    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }

    public void colocarObjeto(GameObject obj) {
        if (obj instanceof Meta) {
            // Si es una Meta, se guarda en la capa de fondo
            metas.add((Meta) obj);
        } else {
            // Si es un sólido (Pared, Caja, Jugador), va a la grilla normal
            Posicion pos = obj.getPosicion();
            grilla[pos.getY()][pos.getX()] = obj;
        }
    }

    public GameObject obtenerObjetoEn(Posicion pos) {
        if (pos.getX() < 0 || pos.getX() >= columnas || pos.getY() < 0 || pos.getY() >= filas) {
            return null; // Fuera de los límites
        }
        return grilla[pos.getY()][pos.getX()];
    }

    public void moverObjeto(GameObject obj, Posicion nuevaPos) {
        Posicion viejaPos = obj.getPosicion();
        grilla[viejaPos.getY()][viejaPos.getX()] = null;
        grilla[nuevaPos.getY()][nuevaPos.getX()] = obj;
        obj.setPosicion(nuevaPos);
    }

    public boolean esMeta(Posicion pos) {
        for (Meta meta : metas) {
            if (meta.getPosicion().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public boolean estanTodasLasMetasCubiertas() {
        if (metas.isEmpty()) return false;

        for (Meta meta : metas) {
            // Buscamos qué objeto hay en la grilla exactamente en la posición de esta meta
            GameObject objEnFrente = obtenerObjetoEn(meta.getPosicion());

            // Si en esa posición NO hay una caja, significa que todavía no ganaste
            if (!(objEnFrente instanceof CajaSimple)) {
                return false;
            }
        }
        return true; // Si recorrió todas las metas y en todas había una caja, ¡GANASTE!
    }

    public Jugador obtenerJugador() {
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                GameObject obj = grilla[y][x];
                if (obj instanceof Jugador) {
                    return (Jugador) obj;
                }
            }
        }
        System.out.println("ADVERTENCIA: No se encontró ningún jugador en el mapa.");
        return null;
    }
}