package Modelo.Nucleo;

import Modelo.Entidades.Caja;
import Modelo.Entidades.GameObject;
import Modelo.Entidades.Jugador;
import Modelo.Entidades.Meta;
import Modelo.Entidades.Cerrojo;
import Modelo.Entidades.MuroCerrado;


import java.util.ArrayList;
import java.util.List;

public class Matriz {

    private final GameObject[][] grilla;
    private final int filas;
    private final int columnas;
    private final List<Meta> metas;
    private final List<Cerrojo> cerrojos;
    private boolean murosAbiertos = false;

    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.grilla = new GameObject[filas][columnas];
        this.metas = new ArrayList<>();
        this.cerrojos = new ArrayList<>();
    }

    public int getFilas() { return this.filas; }
    public int getColumnas() { return this.columnas; }

    public void colocarObjeto(GameObject obj) {
        if (obj instanceof Meta) {
            metas.add((Meta) obj);
        } else if (obj instanceof Cerrojo) {
            cerrojos.add((Cerrojo) obj);
        } else {
            Posicion pos = obj.getPosicion();
            grilla[pos.getY()][pos.getX()] = obj;
        }
    }

    public GameObject obtenerObjetoEn(Posicion pos) {
        if (pos.getX() < 0 || pos.getX() >= columnas || pos.getY() < 0 || pos.getY() >= filas) {
            return null;
        }

        GameObject obj = grilla[pos.getY()][pos.getX()];

        if (obj instanceof MuroCerrado && murosAbiertos) {
            return null;
        }

        return obj;
    }

    public void moverObjeto(GameObject obj, Posicion nuevaPos) {
        Posicion viejaPos = obj.getPosicion();
        grilla[viejaPos.getY()][viejaPos.getX()] = null;
        grilla[nuevaPos.getY()][nuevaPos.getX()] = obj;
        obj.setPosicion(nuevaPos);

        actualizarEstadoMuros();
    }

    public boolean esMeta(Posicion pos) {
        for (Meta meta : metas) {
            if (meta.getPosicion().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public boolean esCerrojo(Posicion pos) {
        for (Cerrojo cerrojo : cerrojos) {
            if (cerrojo.getPosicion().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public boolean areMurosAbiertos() {
        return this.murosAbiertos;
    }

    private void actualizarEstadoMuros() {
        for (Cerrojo cerrojo : cerrojos) {
            GameObject objEncima = grilla[cerrojo.getPosicion().getY()][cerrojo.getPosicion().getX()];

            if (objEncima != null && objEncima.getSimbolo() == 'K') {
                this.murosAbiertos = true;
                return;
            }
        }
        this.murosAbiertos = false;
    }

    public boolean estanTodasLasMetasCubiertas() {
        if (metas.isEmpty()) return false;

        for (Meta meta : metas) {
            // Buscamos qué objeto hay en la grilla exactamente en la posición de esta meta
            GameObject objEnFrente = obtenerObjetoEn(meta.getPosicion());

            // Si en esa posición NO hay una caja, significa que todavía no ganaste
            if (!(objEnFrente instanceof Caja)) {
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

    public void eliminarObjeto(Posicion pos) {
        if (pos.getX() >= 0 && pos.getX() < columnas && pos.getY() >= 0 && pos.getY() < filas) {
            grilla[pos.getY()][pos.getX()] = null;
        }
    }

    public boolean esCasilleroCerrojo(Posicion pos) {
        return false; // Se completará al implementar los terrenos especiales
    }

    public void abrirMurosCorrespondientes() {
        System.out.println("¡Muros abiertos por la Caja Llave!");
    }

}