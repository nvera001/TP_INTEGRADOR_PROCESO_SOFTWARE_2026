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
    private final List<MuroCerrado> muros;
    private boolean murosAbiertos = false;

    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.grilla = new GameObject[filas][columnas];
        this.metas = new ArrayList<>();
        this.cerrojos = new ArrayList<>();
        this.muros = new ArrayList<>();
    }

    public int getFilas() { return this.filas; }
    public int getColumnas() { return this.columnas; }

    public void colocarObjeto(GameObject obj) {
        if (obj instanceof Meta) {
            metas.add((Meta) obj);
        } else if (obj instanceof Cerrojo) {
            cerrojos.add((Cerrojo) obj);
        } else if (obj instanceof MuroCerrado) {
            muros.add((MuroCerrado) obj);
        } else {
            Posicion pos = obj.getPosicion();
            grilla[pos.getY()][pos.getX()] = obj;
        }
    }

    public GameObject obtenerObjetoEn(Posicion pos) {
        if (pos.getX() < 0 || pos.getX() >= columnas || pos.getY() < 0 || pos.getY() >= filas) {
            return null;
        }

        if (!murosAbiertos && esMuro(pos)) {
            GameObject objEnGrilla = grilla[pos.getY()][pos.getX()];
            if (objEnGrilla != null) {
                return objEnGrilla;
            }
            return obtenerMuroEn(pos);
        }

        return grilla[pos.getY()][pos.getX()];
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

    public boolean esMuro(Posicion pos) {
        for (MuroCerrado muro : muros) {
            if (muro.getPosicion().equals(pos)) return true;
        }
        return false;
    }

    private MuroCerrado obtenerMuroEn(Posicion pos) {
        for (MuroCerrado muro : muros) {
            if (muro.getPosicion().equals(pos)) return muro;
        }
        return null;
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
            GameObject objEnFrente = obtenerObjetoEn(meta.getPosicion());

            if (!(objEnFrente instanceof Caja)) {
                return false;
            }
        }
        return true;
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

    public void abrirMurosCorrespondientes() {
        System.out.println("¡Muros abiertos por la Caja Llave!");
    }

    public GameObject obtenerObjetoGrillaPura(Posicion pos) {
        return grilla[pos.getY()][pos.getX()];
    }
}